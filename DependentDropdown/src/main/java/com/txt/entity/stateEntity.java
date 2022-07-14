package com.txt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class stateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "state_id")
	private int state_id;
	@Column(name = "state_name")
	private String state_name;
	@ManyToOne
	private countryEntity c_id;
	
	public int getState_id() {
		return state_id;
	}
	public void setState_id(int state_id) {
		this.state_id = state_id;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public stateEntity() {
	}
	public stateEntity(String state_name) {
		this.state_name = state_name;
	}
	public countryEntity getC_id() {
		return c_id;
	}
	public void setC_id(countryEntity c_id) {
		this.c_id = c_id;
	}
	
	
}
