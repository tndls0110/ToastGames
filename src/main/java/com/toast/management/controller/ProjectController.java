package com.toast.management.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toast.management.dto.DeptDetailMemberDTO;
import com.toast.management.dto.TeamListDTO;
import com.toast.management.service.ProjectService;

@Controller
public class ProjectController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ProjectService projectService;
	
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	
	
	@GetMapping(value="/project_team_list.go")
	public String projectTeamListGo() {
		
		return "project_team_list";
	}
	
	
	@GetMapping(value="/search_team_list.ajax")
	@ResponseBody
	public Map<String, Object>searchDeptMember(@RequestParam(required = false) String teamName,
            @RequestParam(required = false) String teamHead){
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<TeamListDTO> team_list = new ArrayList<>();
		team_list = projectService.searchTeamlist(teamName,teamHead);
		map.put("team_list", team_list);	
		
		return map;
	}
	
	
	@GetMapping(value="/project_team_add.go")
	public String projectTeamAddGO() {
		return "project_team_add";
	}
	
	
	@PostMapping(value="/project_team_add.do")
	public String projectTeamAddDo(@RequestParam Map<String,String> param) {
		
		// logger.info("param : "+param);
		projectService.projectTeamAddDo(param);
		
		return "redirect:/project_team_list";
	}
	
	@GetMapping(value="/project_team_detail.go")
	public String projectTeamDetail(@RequestParam(required = false) String team_idx, Model model) {
		
		projectService.projectTeamDetail(team_idx,model);
		
		return "project_team_detail";
	}
	
	@GetMapping(value="/project_team_update.do")
	public String projectTeamUpdateGo(@RequestParam Map<String,String> param) {
		String team_idx = param.get("team_idx");
		
		return "redirect:/project_team_detail.go?team_idx="+team_idx;
	}
	
	
}
