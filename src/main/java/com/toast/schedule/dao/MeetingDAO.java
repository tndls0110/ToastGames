package com.toast.schedule.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.toast.schedule.dto.MeetingDTO;
import com.toast.schedule.dto.MeetingPhotoDTO;

@Mapper
public interface MeetingDAO {
	
	
	
	//내 정보
	MeetingDTO myInfo(String myId);
	
	//회의실 가기(회의실 이름+idx)
	List<Map<String, Object>> meetingGo();

	//사원 정보 가져오기(사원+idx)
	List<Map<String, Object>> meetingParti();
	
	//회의실 (정보) 추가
	int roomAdd(MeetingDTO dto);
	
	//회의실 사진 추가
	int roomFileAdd(MeetingPhotoDTO photo_dto);
	
	//회의실 사진 file_key추가
	int roomKeyAdd(String photo_key, int room_idx);
	
	//회의실 정보보기
	Map<String, Object> meetingDetail(int room_idx);

	//회의실 정보변경
	int updateMeetingRoom(MeetingDTO meetingDto);
	
	//회의실 일정 존재 확인
	int checkMeetingRoom(int roomIdx);
	
	//회의실 삭제
	int deleteMeetingRoom(int roomIdx);
	
	//회의실 사진 삭제
	int deleteMeetingRoomPhoto(int uploader_idx, String new_filename);
	
	//회의실 사진보기
	MeetingPhotoDTO meetingPhoto(String file_key);
	
	//회의 일정 추가
	int addMeeting(MeetingDTO dto);

	//회의 참여자 추가
	int addMeetingParti(MeetingDTO meeting_parti);	
	
	//회의 일정 보기
	List<MeetingDTO> getMeeting(int room_idx);

	//모든일정에 관한 참여자 가져오기
	List<Integer> getAllParti(int meet_rent_idx);
	
	//내가 포함된 회의일정 보기
	List<MeetingDTO> getMyMeeting(MeetingDTO searchMeeting);
	
	//회의 일정 수정
	int updateMeeting(MeetingDTO dto);

	//회의 참여자 삭제
	int deleteParti(int meet_rent_idx);
	
	//회의 일정 삭제
	int deleteMeeting(int rent_idx);

	//회의 일정 시간변경
	int dateUpdateMeeting(MeetingDTO dto);

	//내 부서 정보
	int myDept(String myId);






















	


}
