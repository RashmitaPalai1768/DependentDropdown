package com.txt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class districtEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "district_id")
	private int district_id;
	@Column(name = "district_name")
	private String district_name;
	@ManyToOne
	private stateEntity s_id;
	
	public stateEntity getS_id() {
		return s_id;
	}
	public void setS_id(stateEntity s_id) {
		this.s_id = s_id;
	}
	public districtEntity() {
	}
	public districtEntity(String district_name) {
		this.district_name = district_name;
	}
	public int getDistrict_id() {
		return district_id;
	}
	public void setDistrict_id(int district_id) {
		this.district_id = district_id;
	}
	public String getDistrict_name() {
		return district_name;
	}
	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}
	
	
}
