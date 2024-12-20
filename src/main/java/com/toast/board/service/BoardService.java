package com.toast.board.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.toast.board.dao.BoardDAO;

@Service
public class BoardService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private final BoardDAO boardDAO;
	
	public BoardService(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}
	
	// spring.servlet.multipart.location=C:/files 이 경로로 주입. !!! 파일 저장위치 !!!
	@Value("${spring.servlet.multipart.location}")
	private String uploadAddr;

	public Map<String, Object> memberInfo(String id) {
		return boardDAO.memberInfo(id);
	}

	public boolean boardWrite(Map<String, Object> params, MultipartFile[] files) throws Exception {
		
		String file_key = UUID.randomUUID().toString();
		params.put("file_key", file_key);
		int boardIdx = boardDAO.boardWrite(params);
	    int uploader_idx = (Integer) params.get("appo_empl_idx"); // params에서 uploader_idx 가져오기
		if (files != null && files.length > 0) { // 파일이 업로드 된다면.
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					String ori_filename = file.getOriginalFilename();
					String file_type = ori_filename.substring(ori_filename.lastIndexOf("."));
					String new_filename = UUID.randomUUID().toString() + "." + file_type;
					String file_addr = uploadAddr + "/" + new_filename;

					File dest = new File(file_addr);
					file.transferTo(dest);
					
					 Map<String, Object> fileParams = new HashMap<>();
					    fileParams.put("file_key", file_key);
					    fileParams.put("uploader_idx", uploader_idx);
					    fileParams.put("ori_filename", ori_filename);
					    fileParams.put("new_filename", new_filename);
					    fileParams.put("file_type", file_type);
					    fileParams.put("file_addr", file_addr);
					
					boardDAO.saveFile(fileParams);
				}
			}
		}
		return boardIdx > 0;
	}

}
