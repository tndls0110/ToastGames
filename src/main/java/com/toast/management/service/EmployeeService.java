package com.toast.management.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.toast.dataconfig.DataConfig;
import com.toast.management.dao.DepartmentDAO;
import com.toast.management.dao.EmployeeDAO;
import com.toast.management.dto.AppointmentDTO;
import com.toast.management.dto.DepartmentDTO;
import com.toast.management.dto.DutyDTO;
import com.toast.management.dto.EmployeeDTO;
import com.toast.management.dto.EmployeeDetailDTO;
import com.toast.management.dto.MainFileDTO;
import com.toast.management.dto.PositionDTO;
import com.toast.member.dao.MemberDAO;
import com.toast.member.dto.FileDTO;

@Service
public class EmployeeService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired PasswordEncoder encoder;
	@Autowired DepartmentDAO deptDAO;
	@Autowired MemberDAO memberDAO;
	// spring.servlet.multipart.location=C:/files 이 경로로 주입. !!! 파일 저장위치 !!!
    @Value("${spring.servlet.multipart.location}")
    private String uploadAddr;
    
	private final EmployeeDAO employeeDAO;
	
	private final DataConfig dataconfig;
	
	public EmployeeService(EmployeeDAO employeeDAO,DataConfig dataconfig) {
		this.employeeDAO = employeeDAO;
		this.dataconfig = dataconfig;
		
	}
	
	@Transactional
	public void employeeAdd(MultipartFile[] files,MultipartFile singleFile,Map<String, String> param) {
		
		String file_key = UUID.randomUUID().toString();
		// empl_stamp , empl_profile >> uuid 파일 키 사용안하고 newfilename 넣어서 저장된 이름 경로 사용하기
		String empl_stamp = UUID.randomUUID().toString();
		String empl_profile = UUID.randomUUID().toString();
		param.put("file_key", file_key);
		param.put("empl_profile", empl_profile);
		param.put("empl_stamp", empl_stamp);
		String pw = param.get("empl_pw");
		String encodepw = encoder.encode(pw); // 스프링 시큐리티 사용한 암호화 비밀번호
		param.put("empl_pw", encodepw);
		
		
		try {
			String ssn2 = param.get("empl_ssn2");
			// 주민번호 뒷자리 암호화 시키기
			String encodessn2 = dataconfig.aesCBCEncode(ssn2);
			param.put("empl_ssn2", encodessn2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("param : "+param);
		logger.info("암호화 주민번호 : "+param.get("empl_ssn2"));
		logger.info("암호화 비밀번호 : "+param.get("empl_pw"));
		logger.info("파일키 : "+param.get("file_key"));
		try {
			logger.info("복호화 주민번호 : "+dataconfig.aesCBCDecode(param.get("empl_ssn2")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//  throw new RuntimeException("주민등록번호 암호화 실패", e);
		}
		// 직인 업로드		
		String emplstamp = emplStampUploadAdd(singleFile);
		param.put("empl_stamp", emplstamp);
		employeeDAO.employeeAdd(param);	
		// 사원등록시 파일 등록하기 추가 ??
			
				try {
					emplfileUploadAdd(files,file_key);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	} // employeeAdd(MultipartFile[] files, Map<String, String> param)
	
	// 직원 상세보기 
	public void employeeDetail(String empl_idx,Model model) { // 직원 상세보기, 직급 직책 부서, 인사변경내역, 첨부파일, 직인 가져오기
		AppointmentDTO appolast = new AppointmentDTO();
		List<AppointmentDTO> appoList = new ArrayList<>();
		EmployeeDTO employee = new EmployeeDTO();
		List<MainFileDTO> file = new ArrayList<>();
		// 직원 정보 
		employee = employeeDAO.employeeDetail(empl_idx);
		try {
			String ssn2 = dataconfig.aesCBCDecode(employee.getEmpl_ssn2());
			employee.setEmpl_ssn2(ssn2);
			logger.info("복호화 주민번호 : "+ssn2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//  throw new RuntimeException("주민등록번호 암호화 실패", e);
		}
		// 직원 직급 직책 부서 가져오기 - 발령정보중 가장 최근꺼 가져오기
		appolast = employeeDAO.employeeAppolast(empl_idx);
		// 직원 발령정보
		appoList = employeeDAO.employeeAppoList(empl_idx);
		// 직원 첨부파일 가져오기 >> employeeDAO.employeeDetail(empl_idx); 에서 가져온 파일키 넣기
		String file_key =employee.getFile_key();
		file = employeeDAO.employeeFile(file_key);
		Double weekWork = getweekWorkRecord(empl_idx);
		
		model.addAttribute("weekWork",weekWork);
		  model.addAttribute("employee", employee);
	        model.addAttribute("appoLast", appolast);
	        model.addAttribute("appoList", appoList);
	        model.addAttribute("file", file);
		
	} // public void employeeDetail(String empl_idx,Model model)



	// 인사발령 서비스
	@Transactional
	public void employeeAppoDo(String empl_idx, String dept_idx, String position_idx, String duty_idx,
			String movein_date,String empl_duty) {
		AppointmentDTO appolast = new AppointmentDTO();
		appolast = employeeDAO.employeeAppolast(empl_idx);
		EmployeeDTO employee = employeeDAO.employeeDetail(empl_idx);
		int dutyidx = Integer.parseInt(duty_idx);
	//	String empl_job = employee.getEmpl_job();
		String empl_job = empl_duty;
		// employeeappodo 에 직무 넣기 >> 직원의 직무 가져와서 넣기?
		employeeDAO.employeeDutyUpdate(empl_idx,empl_duty);
		if(appolast==null) { // 이전 인사발령 이력이 없으면
			employeeDAO.employeeAppoDo(empl_idx,dept_idx,position_idx,duty_idx,movein_date,empl_job);
		}
		else { // 인사발령이력이 있으면
		int appo_idx =appolast.getAppo_idx();
			employeeDAO.employeeAppoDo(empl_idx,dept_idx,position_idx,duty_idx,movein_date,empl_job);
			// 전출날짜 넣기
			employeeDAO.employeeTransfer(appo_idx,movein_date);
		}
		
		// 내가 부서장인데 인사발령이 나면?
		if(employeeDAO.deptheadcheck(empl_idx)>0) {
				// 이전 부서의 head_idx를 null로 바꿈
				employeeDAO.deptheadmoveout(empl_idx);
			}
		
		if(dutyidx>1 && dutyidx <5) { // 부서장 직책이라면? 사장 ~ 팀장이라면 사장idx = 2 부장idx=3 팀장idx=4
			// department의 head_idx가 비어있지 않으면 - 부서장이 있는 경우라면 
			DepartmentDTO dept =deptDAO.getdeptinfo(dept_idx);			
			int head_idx = dept.getDept_head_idx();
			String headidx = String.valueOf(head_idx);
			if(head_idx != 0 && headidx != empl_idx) { // 이전 부서장이 존재한다면
				AppointmentDTO appolasthead = new AppointmentDTO();
				
				appolasthead = employeeDAO.employeeAppolast(headidx);
				int head_appo_idx = appolasthead.getAppo_idx();
				// 직위변경 넣기 
				String headdudy = String.valueOf(10);
				// 가발령 상태로 변환 ?
			//	dept_idx = "1";
				// 
				employeeDAO.employeeAppoDo(headidx,dept_idx,position_idx,headdudy,movein_date,empl_job);
				// 전출날짜 넣기
				employeeDAO.employeeTransfer(head_appo_idx,movein_date);
				// 부서장 부서테이블에서 퇴출
				employeeDAO.deptheadmoveout(headidx);
			}
			// department 테이블에 부서장 업데이트
			employeeDAO.deptHeadAdd(dept_idx,empl_idx);					
		} 
		
		// 인사발령나면 empl 상태 근무중으로 바꾸기
		String statement_idx = "1";
		employeeDAO.employeeChangeDo(empl_idx,statement_idx);
	}// public void employeeAppoDo(String empl_idx, String dept_idx, String position_idx, String duty_idx,String movein_date)
	
	// 직원 퇴사, 근무, 휴직 처리
	public void employeeChangeDo(String empl_idx, String statement_idx) {
	    // 현재 시간 가져오기
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String formattedDate = formatter.format(new Date()); // 현재 시간을 String으로 포맷팅
		if(statement_idx.equals("3")) { // 퇴직처리이면
		// 상태변화 + 퇴사일 입력
		employeeDAO.employeeResigDo(empl_idx,statement_idx);
		// 최근 발령상태 가져와서 전출날짜에 지금시간 넣기
		int emplidx = Integer.parseInt(empl_idx);
		employeeDAO.employeeTransfer(emplidx,formattedDate);
		}else {
		employeeDAO.employeeChangeDo(empl_idx,statement_idx);
		}
	}
	
	
	// 업로드 처리 
		public void emplfileUploadAdd(MultipartFile[] files, String file_key) throws Exception {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					String originalFileName = file.getOriginalFilename();
					String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
					String newFileName = UUID.randomUUID().toString() + "." + fileType;
					String fileAddr = uploadAddr + "files/" + newFileName;
					
					// 경로 설정 부분. 파일을 서버에 저장함. 필요한가? 이거 어떻게 해야할지 정해야 함..
					File dest = new File(fileAddr);
					file.transferTo(dest);
					long file_size = dest.length();
			
					// 첨부 파일 정보를 DTO에 저장.
					FileDTO fileDTO = new FileDTO();
					fileDTO.setFile_key(file_key);
					fileDTO.setOri_filename(originalFileName);
					fileDTO.setNew_filename(newFileName);
					fileDTO.setFile_type(fileType);
					fileDTO.setFile_addr(fileAddr);
				//	fileDTO.setUploader_idx(int_empl_idx);
					fileDTO.setFile_size(file_size);
					// file 테이블에 파일정보 저장.
					employeeDAO.emplfileUpload(fileDTO);
				}

			}
		} // public void emplfileUpload(String empl_idx, MultipartFile[] files)
	
	// 업로드 처리 
	public void emplfileUpload(MultipartFile[] files,String empl_idx) throws IOException {
		EmployeeDTO empl_info = employeeDAO.employeeDetail(empl_idx);
		String file_key =	empl_info.getFile_key();
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				String originalFileName = file.getOriginalFilename();
				String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
				String newFileName = UUID.randomUUID().toString() + "." + fileType;
				String fileAddr = uploadAddr + "files/" + newFileName;
				
				// 경로 설정 부분. 파일을 서버에 저장함. 필요한가? 이거 어떻게 해야할지 정해야 함..
				File dest = new File(fileAddr);
				file.transferTo(dest);
				long file_size = dest.length();
				
				int int_empl_idx = Integer.parseInt(empl_idx);
				// 첨부 파일 정보를 DTO에 저장.
				FileDTO fileDTO = new FileDTO();
				fileDTO.setFile_key(file_key);
				fileDTO.setOri_filename(originalFileName);
				fileDTO.setNew_filename(newFileName);
				fileDTO.setFile_type(fileType);
				fileDTO.setFile_addr(fileAddr);
				fileDTO.setUploader_idx(int_empl_idx);
				fileDTO.setFile_size(file_size);
				// file 테이블에 파일정보 저장.
				employeeDAO.emplfileUpload(fileDTO);
			}

		}
	} // public void emplfileUpload(String empl_idx, MultipartFile[] files)

	public List<FileDTO> getemplUploadedFiles(String empl_idx) {
		
		EmployeeDTO empl_info = employeeDAO.employeeDetail(empl_idx);
		String file_key =	empl_info.getFile_key();
		List<FileDTO> filelist = employeeDAO.getemplUploadedFiles(file_key);
		return filelist;
	}
	// 직인 등록
	public String emplStampUploadAdd(MultipartFile singleFile) {
	    try {
	        // 1. 파일 검증
	        if (singleFile == null || singleFile.isEmpty()) {
	           
	            return null; 
	        }

	        String originalFileName = singleFile.getOriginalFilename();
	        if (originalFileName == null || !originalFileName.contains(".")) {
	        	return null; 
	        }

	        // 2. 확장자 추출 및 파일명 생성
	        String fileType = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
	        String newFileName = UUID.randomUUID().toString() + "." + fileType;
	        String fileAddr = uploadAddr + "files/" + newFileName;

	        // 3. 디렉토리 확인 및 생성
	        File dest = new File(fileAddr);
	        if (!dest.getParentFile().exists()) {
	            dest.getParentFile().mkdirs();
	        }

	        // 4. 파일 저장
	        singleFile.transferTo(dest);
	        logger.info("파일 업로드 성공, 저장된 파일 이름: " + newFileName);

	        return newFileName; // 성공 시 새 파일 이름 반환
	    } catch (IllegalArgumentException e) {
	        logger.error("유효성 검증 실패: " + e.getMessage());
	        return "유효하지 않은 요청: " + e.getMessage(); // 사용자 친화적 메시지 반환
	    } catch (IOException e) {
	        logger.error("파일 업로드 중 오류 발생: " + e.getMessage());
	       
	        return null; 
	    }
	}
	
	// 사원 직인 등록
	public void emplStampUpload(MultipartFile singleFile,String empl_idx) {
		
		String originalFileName = singleFile.getOriginalFilename();
		String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
		String newFileName = UUID.randomUUID().toString() + "." + fileType;
		String fileAddr = uploadAddr + "files/" + newFileName;
		
		File dest = new File(fileAddr);
		if (!dest.exists()) {
	        dest.mkdirs();  // 디렉토리가 없으면 생성
	    }
		  try {
		        // 파일을 지정한 경로에 저장
		        singleFile.transferTo(dest);
		        logger.info("파일 이름은 : "+newFileName);
		        // 업로드된 파일 이름을 DB에 저장
		        
		        employeeDAO.emplStampUpload(newFileName,empl_idx);

		    } catch (IOException e) {
		        e.printStackTrace();
		        throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
		    }
		
	}

	public boolean emplFileDel(String new_filename) {
		// new_filename으로 file db 삭제 + 저장경로의 파일 삭제
		boolean success = false;
		employeeDAO.emplFileDel(new_filename);
		File file = new File(uploadAddr + "files/" + new_filename);
		if(file.exists()) {
			 success = file.delete();
			
		}
		return success;
	} // public boolean emplFileDel(String new_filename)

	public List<EmployeeDetailDTO> getEmployeeList() {
		
		return employeeDAO.getEmployeeList();
	}

	public List<EmployeeDetailDTO> getStaffList(String dept_idx) {
		
		return employeeDAO.getStaffList(dept_idx);
	}

	public List<EmployeeDetailDTO> getFilteredStaffList(String dept_idx, String searchKey, String searchValue) {
		
		return employeeDAO.getFilteredStaffList(dept_idx, searchKey, searchValue);
	}

	public EmployeeDetailDTO getStaffDetail(String empl_idx) {
		
		return employeeDAO.getStaffDetail(empl_idx);
	}

	public List<EmployeeDetailDTO> emplAllList() {
		// TODO Auto-generated method stub
		return employeeDAO.emplAllList();
	}

	public List<EmployeeDetailDTO> emplPreAllList() {
		// TODO Auto-generated method stub
		return employeeDAO.emplPreAllList();
	}

	public List<EmployeeDetailDTO> emplresignAllList() {
		// TODO Auto-generated method stub
		return employeeDAO.emplresignAllList();
	}

	public EmployeeDetailDTO staffListGo(String empl_idx) {
		
		if(	employeeDAO.deptheadcheck(empl_idx) >0) {
			return employeeDAO.getStaffDetail(empl_idx);
		}
		else {
			return null;
		}
		
		
	}
	// 이번주 근무 시간 총합
	public Double getweekWorkRecord(String empl_idx) {
		return employeeDAO.getweekWorkRecord(empl_idx);
		
	}

	public boolean isIdDuplicate(String emplId) {
		// TODO Auto-generated method stub
		return employeeDAO.isIdDuplicate(emplId);
	}
	
}
