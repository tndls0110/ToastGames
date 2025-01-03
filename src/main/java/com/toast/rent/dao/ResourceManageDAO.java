package com.toast.rent.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.toast.rent.dto.ResourceManageDTO;
import com.toast.rent.dto.ResourcePhotoDTO;

@Mapper
public interface ResourceManageDAO {

	//사원정보 가져오기
	ResourceManageDTO getEmplMg(String loginId);

	//카테고리 목록 가져오기
	List<ResourceManageDTO> resourceCateMg();
	
	//카테고리 검색
	Map<String, Object> categroySearch(String keyword);

	//총 페이지
	int allCount(int cnt);

	//물품 목록 가져오기
	List<ResourceManageDTO> resourceList(int limit, int offset);

	//카테고리별 페이지
	int filterListCount(int cnt, String category);

	//카테고리별 목록 가져오기
	List<ResourceManageDTO> resourceFilterList(String category,int limit, int offset);

	//카테고리 & 검색 총 페이지
	int allSearchCount(Map<String, Object> map);
	
	//전체보기 카테고리 & 검색 목록 가져오기
	List<ResourceManageDTO> resourceSearchList(Map<String, Object> map);

	//카테고리 선택 & 검색 페이지
	int filterSearchCount(Map<String, Object> map);

	//카테고리 선택 & 검색 목록 가져오기
	List<ResourceManageDTO> filterSearchList(Map<String, Object> map);

	//물품 사진 등록
	int prodFileAdd(ResourcePhotoDTO photo_dto);

	//물품 등록
	int prodWrite(ResourceManageDTO dto);
	
	//물품 대여상세 가져오기
	ResourceManageDTO prodMgDetail(int prod_idx);

	//물품 대여 사원정보가져오기
	ResourceManageDTO rentEmpl(int prod_rent_empl_idx);

	//물품 파일 가져오기
	List<ResourcePhotoDTO> prodMgFile(int prod_idx);

	//대여기록 총 페이지
	int allHisCount(int cnt, int prodIdx);

	//대여 기록 페이징
	List<ResourceManageDTO> rentManageList(int prodIdx,int limit, int offset);

	//대여 승인
	int permitProd(int prod_idx);

	//물품 반납
	int permitReturn(int prod_idx);

	//반납일시 입력
	int insertReturnDate(ResourceManageDTO dto);

	//물품 정보 가져오기
	ResourceManageDTO getProductinfo(int prod_idx);
	
	


}
