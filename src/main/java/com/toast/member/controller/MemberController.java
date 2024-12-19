package com.toast.member.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.toast.member.dto.FileDTO;
import com.toast.member.dto.MemberDTO;
import com.toast.member.service.MailService;
import com.toast.member.service.MemberService;

@Controller
public class MemberController {
	Logger logger = LoggerFactory.getLogger(getClass());

	private final MemberService memberService;
	private final MailService mailService;

	public MemberController(MemberService memberService, MailService mailService) {
		this.memberService = memberService;
		this.mailService = mailService;
	}

	// 로그인 페이지 이동
	@GetMapping(value = "/login.go")
	public String loginView() {
		return "login";
	}

	@PostMapping(value = "/login.do")
	public String login(HttpSession session, Model model, String id, String pw) {
		String page = "login";
		boolean isValidId = memberService.isValidId(id); // 아이디 존재 여부 확인
		if (!isValidId) { // 아이디가 존재하지 않는 경우
			model.addAttribute("idError", "아이디를 확인하세요."); // 아이디 오류 메시지 전달
		} else {
			// 아이디가 존재하면 비밀번호 확인
			if (memberService.login(id, pw)) { // 로그인 성공
				int changePwCheck = memberService.changePwCheck(id); // 비밀번호 변경했는지 여부를 가져옴.
				session.setAttribute("loginId", id);
				if (changePwCheck != 0) { // 비밀번호 변경을 했다면.
					page = "index"; // 나중에 다른 jsp로 수정필요!!!
				} else { // 비밀번호 변경이 필요하면
					model.addAttribute("msg", "보안을 위해 비밀번호를 변경해주세요.");
					page = "myPageUpdate.go";
				}
			} else { // 아이디는 맞으나, 비밀번호가 틀린 경우.
				model.addAttribute("pwError", "비밀번호를 확인하세요."); // 비밀번호 오류 메시지 전달
			}
		}
		model.addAttribute("inputId", id); // 아이디 및 비밀번호가 틀려도 사용자가 입력한 아이디는 남김.
		return page;
	}

	// 로그아웃
	@GetMapping(value = "/logOut.do")
	public String logout(HttpSession session, Model model) {
		String id = (String) session.getAttribute("loginId");
		if (id == null) {
			model.addAttribute("msg", "로그아웃은 로그인 상태에서만 가능합니다.");
			return "login";
		} else {
			session.invalidate(); // 세션 전체를 무효화 (세션에 저장된 모든 정보가 삭제됨).
			model.addAttribute("msg", "로그아웃 되었습니다.");
			return "login"; // 로그인 페이지로.
		}
	}

	// 아이디 찾기 페이지 이동
	@GetMapping(value = "/findId.go")
	public String findIdView() {
		return "login_find_id";
	}

	// 아이디 찾기(이름과 이메일)
	@PostMapping(value = "/findId.do")
		public String findMemberId(Model model, String name, String email) {
			// 이름과 이메일을 db에서 확인 로직이 필요..
			String page = "login_find_id";
			boolean isValidName = memberService.isValidName(name);
			if (!isValidName) {
				model.addAttribute("nameError", "이름이 존재하지 않습니다.");
				model.addAttribute("inputName", name);
				model.addAttribute("inputEmail", email);
			} else {
				// 이름이 유효하면 이에밀 확인
				String findId = memberService.findId(name, email);
				if (findId == null) { // 이메일이 일치하지 않다면.
					model.addAttribute("emailError", "이메일이 존재하지 않습니다.");
					model.addAttribute("inputName", name);
					model.addAttribute("inputEmail", email);
			} else { // 이름과 이메일이 모두 맞는 경우.
				model.addAttribute("findName", name);
				model.addAttribute("findId", findId);
				page = "login_find_id_result";
			}
			  }
			return page;
		}

	// 비밀번호 찾기 페이지 이동
	@GetMapping(value = "/findPw.go")
	public String findPwView() {
		return "login_find_pw";
	}

	// 비밀번호 찾기 (아이디, 이메일) /(암호화된 비밀번호 DB저장 및 변경된 비밀번호 이메일로 전송)
	@Transactional
	@PostMapping(value = "/findPw.do")
	public String tempPw(Model model, String id, String email) {
		String page = "login_find_pw";
		boolean isValidId = memberService.isValidId(id); // 입력한 아이디가 일치하는지 확인.
		if (!isValidId) { // 존재 하지 않는다면?
			model.addAttribute("idError", "ID가 존재하지 않습니다."); // 아이디 오류 메시지 전달
			model.addAttribute("inputId", id);
			model.addAttribute("inputEmail", email);
			return page;
		}
		Map<String, String> userInfo = memberService.findPw(id, email); // 아이디와 이메일을 입력해 DB에 저장된 정보와 일치하는지 확인. map형식으로 가져오는 건 empl_name을 가져오기 위함.
		if (userInfo != null) { // 저장된 정보가 일치한다면.
			String tempPw = UUID.randomUUID().toString().substring(0, 8); // 비밀번호는 UUID로 새로 설정.
			memberService.UpdatePw(id, tempPw); // 해당 id에 변경된 비밀번호 저장.
			mailService.sendPwMail(email, tempPw); // 저장된 email에 변경된 비밀번호 전송.
			model.addAttribute("findName", userInfo.get("empl_name"));
			model.addAttribute("findEmail", email);
			page = "login_find_pw_result";
		} else {
			model.addAttribute("emailError", "이메일이 존재하지 않습니다.");
			model.addAttribute("inputId", id);
			model.addAttribute("inputEmail", email);
		}
		return page;
	}

	// 마이페이지(상세보기)
	@GetMapping(value = "/myPage.go")
	public String myPageForm(Model model, HttpSession session) {
		String id = (String) session.getAttribute("loginId");
		List<MemberDTO> memberInfo = memberService.memberInfo(id); // 리스트 형태로 가져옴.
		model.addAttribute("memberInfo", memberInfo); // 인포에 직인 정보도 포함!!
		List<FileDTO> fileList = memberService.fileList(id); // 사용자가 첨부한 파일 리스트를 불러온다.
		logger.info("fileList : ? " + fileList);
		model.addAttribute("fileList", fileList);

		return "mypage";
	}

	@ResponseBody
	@GetMapping(value = "/employmentHistory.ajax")
	public Map<String, Object> employmentHistory(String page, String cnt, HttpSession session, Model model) {
		logger.info("list called with page: {}, cnt: {}", page, cnt);
		String id = (String) session.getAttribute("loginId");
		// 페이지와 항목 수 변환
		int page_ = Integer.parseInt(page);
		int cnt_ = Integer.parseInt(cnt);

		// 서비스 호출 시 ID 전달
		Map<String, Object> result = memberService.employmentHistory(page_, cnt_, id);
		logger.info("Result from service: {}", result);
		return result;
	}
	
	// spring.servlet.multipart.location=C:/files 이 경로로 주입. !!! 파일 저장위치 !!!
    @Value("${spring.servlet.multipart.location}")
    private String uploadAddr;
	
	@GetMapping(value = "/download/{filename}") // 다운로드 요청이 들어오면 작동되는 메서드.
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
	    Resource resource = new FileSystemResource(uploadAddr + "/" + filename);
	    logger.info("isFileExist ? : " + resource);
	    if (resource.exists()) { // 파일이 존재 한다면.
	    	String originalFileName = memberService.originalFileName(filename); 
	        return ResponseEntity.ok()
	            .header("Content-Disposition", "attachment; filename=\"" + originalFileName + "\"")
	            .body(resource);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping(value = "/files/{filename}") // 직인 이미지 파일 요청이 들어오면 작동되는 메서드.
	public ResponseEntity<Resource> Image(@PathVariable String filename) {
		Path filePath = Paths.get(uploadAddr + "/" + filename);
	    // 파일이 존재하는지 확인
	    if (Files.exists(filePath)) {
	        // 파일을 Resource로 반환
	        Resource resource = new FileSystemResource(filePath);
	        // Content-Type을 자동으로 설정하고 파일을 반환
	        return ResponseEntity.ok()
	            //.contentType(MediaType.IMAGE_JPEG)  // 이미지의 경우 예시로 JPEG 사용, 실제 이미지 타입에 맞게 설정
	            .body(resource);
	    } else {
	        // 파일이 존재하지 않으면 404 반환
	        return ResponseEntity.notFound().build();
	    }
	}
	
	// 마이페이지(수정) 이동
	@GetMapping(value = "/myPageUpdate.go")
	public String myPageUpdateForm(Model model, HttpSession session) {
		// 직인 제출하기. 이 항목들이 go로 가야할지 do로 처리해야 할지 고민해봐야 할 듯!
		String id = (String) session.getAttribute("loginId");
		List<MemberDTO> memberInfo = memberService.memberInfo(id);
		//List<FileDTO> fileList = memberService.fileList(id); // 사용자가 첨부한 파일 리스트를 불러온다.
		model.addAttribute("memberInfo", memberInfo);
		//model.addAttribute("fileList", fileList);
		return "mypage_update";
	}
	
	@ResponseBody
	@PostMapping(value = "/fileUpload.do")
	public ResponseEntity<List<FileDTO>> fileUpload(@RequestParam("file") MultipartFile[] files, HttpSession session, Model model) {
		String id = (String) session.getAttribute("loginId");
		int empl_idx = memberService.getUploaderIdx(id);
		try {
			memberService.fileUpload(empl_idx, files);
	        List<FileDTO> fileList = memberService.getUploadedFiles(empl_idx);  // 업로드된 파일 리스트
			return ResponseEntity.ok(fileList);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 파일 목록 조회
	@ResponseBody
	@GetMapping("/getFileList.do")
	public ResponseEntity<List<FileDTO>> getFileList(HttpSession session) {
	    String id = (String) session.getAttribute("loginId");
	    int empl_idx = memberService.getUploaderIdx(id);

	    List<FileDTO> fileList = memberService.getUploadedFiles(empl_idx);  // 업로드된 파일 목록 조회
	    return ResponseEntity.ok(fileList);  // 파일 목록을 JSON으로 반환
	}
	
	@ResponseBody // 응답을 JSON 형태로 반환
	@PostMapping(value = "/changePw.ajax") 	// 비밀번호 변경 처리 로직
	public Map<String, String> changePw(HttpSession session, @RequestParam("currentPassword") String currentPw,
			@RequestParam("newPassword") String newPw, @RequestParam("confirmPassword") String confirmPw) {
		Map<String, String> response = new HashMap<>();

		// 현재 로그인한 사용자의 ID를 얻어옵니다.
		String id = (String) session.getAttribute("loginId");

		// 사용자가 입력한 현재 비밀번호와 DB에 저장된 비밀번호 비교
		boolean isValidCurrentPassword = memberService.checkCurrentPassword(id, currentPw);
		logger.info("check" + isValidCurrentPassword);
		if (!isValidCurrentPassword) {
			response.put("status", "error");
			response.put("message", "기존 비밀번호를 확인하세요.");
			return response;
		}
		// 새 비밀번호와 확인 비밀번호가 일치하는지 확인
		if (!newPw.equals(confirmPw)) {
			response.put("status", "error");
			response.put("message", "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			return response;
		}
		// 새 비밀번호로 변경
		memberService.changePw(id, newPw);
		// 성공 메시지
		response.put("status", "success");
		response.put("message", "비밀번호가 성공적으로 변경되었습니다.");
		return response;
	}

	// 회원 정보 수정
	@PostMapping(value = "/myPageUpdate.do")
	public String mypageUpdate(@RequestParam("imageFile") MultipartFile file, MemberDTO memberDTO, HttpSession session,
			Model model) {
		String id = (String) session.getAttribute("loginId");
		memberDTO.setEmpl_id(id);
		if (!file.isEmpty()) { // 이미지 파일을 업로드 했다면.
			try {
				String imageFile = memberService.profileImage(file);
				memberDTO.setEmpl_profile(imageFile); // 프로필 이미지 설정.
			} catch (IOException e) {
				model.addAttribute("msg", "파일 업로드 실패");
				e.printStackTrace();
			}
		}
		// 회원정보 업데이트
		int row = memberService.mypageUpdate(memberDTO);
		if (row > 0) {
			model.addAttribute("msg", "회원정보가 수정되었습니다.");
		} else {
			model.addAttribute("msg", "회원정보 수정이 실패했습니다.");
		}
		return "redirect:/myPage.go";
	}

}
