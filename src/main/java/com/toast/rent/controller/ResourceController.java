package com.toast.rent.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toast.rent.dto.ResourceDTO;
import com.toast.rent.dto.ResourcePhotoDTO;
import com.toast.rent.service.ResourceService;

@Controller
public class ResourceController {
	
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private ResourceService resourceService;
	private HttpSession session;
	public int empl_idx = 0;
	
	public ResourceController(ResourceService resourceService, HttpSession session) {
		this.resourceService = resourceService;
		this.session = session;
	}

	
	
	
	/*공용 물품 대여*/
	
	//카테고리 목록 가져오기
	@RequestMapping(value="/rent_list.go")
	public String rentList(Model model) {
		List<ResourceDTO> categoryList =resourceService.resourceCate();
		String loginId = (String) session.getAttribute("loginId");
		session.setAttribute("empl_idx", resourceService.getEmpl(loginId));
		empl_idx = (int) session.getAttribute("empl_idx");
		model.addAttribute("categoryList", categoryList);
		return "rent_list";
	}
	
	//카테고리 검색 목록 가져오기
	@GetMapping(value="/categroySearch.ajax")
	@ResponseBody
	public Map<String, Object> categroySearch(
	        @RequestParam("keyword") String keyword) {
		
	    return resourceService.categroySearch(keyword); // 특정 카테고리
	}
	
	//내가 대여한 물품 페이지 이동
	//@RequestMapping(value="/rent_mylist.do")
	
	
	//물품 목록 보기(대여 가능 여부 및 반납일시 포함)
	@GetMapping(value="/resourceList.ajax")
	@ResponseBody
	public Map<String, Object> resourceList(
			@RequestParam("page") String page, 
	        @RequestParam("cnt") String cnt, 
	        @RequestParam("category") String category) {
		
		int page_ = Integer.parseInt(page);
		int cnt_ = Integer.parseInt(cnt);
	    // 카테고리에 따른 처리
	    if ("all".equals(category)) {
	        return resourceService.resourceList(page_, cnt_); // 전체 보기
	    } else {
	        return resourceService.resourceFilterList(category, page_, cnt_); // 특정 카테고리
	    }
	}

	//물품 검색별 보기
	@PostMapping(value="/resourceSearch.ajax")
	@ResponseBody
	public Map<String, Object> resourceSearch(
            @RequestParam("page") String page,
            @RequestParam("cnt") String cnt,
            @RequestParam("category") String category,
            @RequestParam("option") String option,
            @RequestParam("keyword") String keyword){
		
		int page_ = Integer.parseInt(page);
		int cnt_ = Integer.parseInt(cnt);
		
	    if ("all".equals(category)) {
	        return resourceService.resourceSearch(page_, cnt_,option, keyword); // 전체 보기
	    } else {
	        return resourceService.resourceFilterSearch(category, page_, cnt_,option, keyword); // 특정 카테고리
	    }

	}
	

	//물품 상세보기(사진, 첨부파일 필요함)
	@RequestMapping(value="/prodDetail.go")
	public String prodDetail(@RequestParam("prod_idx") int prod_idx, Model model) {
		logger.info("prod_idx:"+prod_idx);
		ResourceDTO detail = resourceService.prodDetail(prod_idx);
		List<ResourcePhotoDTO> files = resourceService.prodFile(prod_idx);
		model.addAttribute("detail", detail);
		model.addAttribute("files", files);
		return "rent_detail";
	}
	
	//물품 상태 상세보기
	@GetMapping(value = "/prodRentDetail.ajax")
	@ResponseBody
	public ResourceDTO prodRentDetail(@RequestParam("prod_idx") int prod_idx) {
	    return resourceService.prodRentDetail(prod_idx);
	}
	
	//물품 대여 신청하기
	@PostMapping(value="/rentRequest.do")
	public  String rentRequest(@RequestParam Map<String, String> requestData) {
	    String prod_idx = requestData.get("prod_idx");
	    String prod_exp_date = requestData.get("prod_exp_date");
	    String prod_rent_reason = requestData.get("prod_rent_reason");
	    
	    LocalDateTime prodExpDate = null;

	    // prod_return_date를 LocalDateTime으로 변환
	    if (prod_exp_date != null && !prod_exp_date.isEmpty()) {
	        prodExpDate = LocalDateTime.parse(prod_exp_date + "T18:00:00"); // 시간 추가
	    }

	    logger.info("prodExpDate: " + prodExpDate);
	    logger.info("empl_idx: " + empl_idx);

	    // 현재 시간 설정
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    
	    // DTO 생성 및 값 설정
	    ResourceDTO dto = new ResourceDTO();
	    dto.setProd_idx(Integer.parseInt(prod_idx));
	    dto.setProd_exp_date(prodExpDate); // LocalDateTime 설정
	    dto.setProd_rent_reason(prod_rent_reason);
	    dto.setProd_rent_empl_idx(empl_idx);
	    dto.setProd_rent_date(currentDateTime);
	    
	    // 서비스 호출
	    resourceService.rentRequest(dto);

	 // 성공 메시지 반환
	    return "redirect:/prodDetail.go?prod_idx=" + requestData.get("prod_idx");
	}
	
	
	
	//물품 첨부파일 확인하기 및 다운받기
	@RequestMapping(value="/download.do")
	public ResponseEntity<Resource> fileDownloads(String new_filename, String ori_filename) {
		return resourceService.fileDownload(new_filename,ori_filename);
	}

	
	//내가 대여한 물품(목록 보기 물품 상태 포함)
	
	//내가 대여한 물품 종류별 보기
	
	//내가 대여한 물품 상세보기(반납장소 포함)
	
	//대여 승인 시 반납일정 일정 표시
	
	//사용연한 지나면 물품 상태 0으로 업뎃
	
	
	
	
	
	
}
