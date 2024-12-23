package com.toast.schedule.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDTO {

	//내정보
	private int empl_idx;

	private String empl_name;	
	
	//일정
	private int sche_idx;
	private String sche_title;
	private String sche_content;
	private int sche_type;  //1:개인 2:부서 3:팀
	private LocalDateTime sche_start_date;
	private LocalDateTime sche_end_date;
	private int sche_allday;
	private int sche_empl_idx;
	private LocalDateTime sche_write_date;
	private LocalDateTime sche_update_date;
	
	//참여자
	private int sche_parti_empl_idx;
	private List<Integer> sche_parti_empl_idxs;
	
	
	//공용 물품 일정
	private int appo_empl_idx;
	private int dept_idx;
	private int position_idx;
	private int duty_idx;
	private String position_name;
	private String duty_name;
	private String dept_name;
	//private String empl_name;
	
	public int getEmpl_idx() {
		return empl_idx;
	}
	public void setEmpl_idx(int empl_idx) {
		this.empl_idx = empl_idx;
	}
	
	public List<Integer> getSche_parti_empl_idxs() {
		return sche_parti_empl_idxs;
	}
	public void setSche_parti_empl_idxs(List<Integer> sche_parti_empl_idxs) {
		this.sche_parti_empl_idxs = sche_parti_empl_idxs;
	}
	
	public String getEmpl_name() {
		return empl_name;
	}
	public void setEmpl_name(String empl_name) {
		this.empl_name = empl_name;
	}
	public int getAppo_empl_idx() {
		return appo_empl_idx;
	}
	public void setAppo_empl_idx(int appo_empl_idx) {
		this.appo_empl_idx = appo_empl_idx;
	}
	public int getDept_idx() {
		return dept_idx;
	}
	public void setDept_idx(int dept_idx) {
		this.dept_idx = dept_idx;
	}
	public int getPosition_idx() {
		return position_idx;
	}
	public void setPosition_idx(int position_idx) {
		this.position_idx = position_idx;
	}
	public int getDuty_idx() {
		return duty_idx;
	}
	public void setDuty_idx(int duty_idx) {
		this.duty_idx = duty_idx;
	}
	public String getPosition_name() {
		return position_name;
	}
	public void setPosition_name(String position_ame) {
		this.position_name = position_ame;
	}
	public String getDuty_name() {
		return duty_name;
	}
	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public int getSche_parti_empl_idx() {
		return sche_parti_empl_idx;
	}
	public void setSche_parti_empl_idx(int sche_parti_empl_idx) {
		this.sche_parti_empl_idx = sche_parti_empl_idx;
	}
	
	
	public int getSche_idx() {
		return sche_idx;
	}
	public void setSche_idx(int sche_idx) {
		this.sche_idx = sche_idx;
	}
	public String getSche_title() {
		return sche_title;
	}
	public void setSche_title(String sche_title) {
		this.sche_title = sche_title;
	}
	public String getSche_content() {
		return sche_content;
	}
	public void setSche_content(String sche_content) {
		this.sche_content = sche_content;
	}
	public int getSche_type() {
		return sche_type;
	}
	public void setSche_type(int sche_type) {
		this.sche_type = sche_type;
	}
	public LocalDateTime getSche_start_date() {
		return sche_start_date;
	}
	public void setSche_start_date(LocalDateTime sche_start_date) {
		this.sche_start_date = sche_start_date;
	}
	public LocalDateTime getSche_end_date() {
		return sche_end_date;
	}
	public void setSche_end_date(LocalDateTime sche_end_date) {
		this.sche_end_date = sche_end_date;
	}
	public int getSche_allday() {
		return sche_allday;
	}
	public void setSche_allday(int sche_allday) {
		this.sche_allday = sche_allday;
	}
	public int getSche_empl_idx() {
		return sche_empl_idx;
	}
	public void setSche_empl_idx(int sche_empl_idx) {
		this.sche_empl_idx = sche_empl_idx;
	}
	public LocalDateTime getSche_write_date() {
		return sche_write_date;
	}
	public void setSche_write_date(LocalDateTime sche_write_date) {
		this.sche_write_date = sche_write_date;
	}
	public LocalDateTime getSche_update_date() {
		return sche_update_date;
	}
	public void setSche_update_date(LocalDateTime sche_update_date) {
		this.sche_update_date = sche_update_date;
	}
	
	
}
