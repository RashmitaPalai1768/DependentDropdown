package com.txt.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class AllEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int all_id;
	@ManyToOne
	private countryEntity country_id1;
	@ManyToOne
	private stateEntity state_id1;
	@ManyToOne
	private districtEntity district_id1;

	/*
	 * @Lob private byte[] data;
	 */
	public AllEntity() {
		
	}

	/*
	 * public AllEntity(countryEntity country_id, stateEntity state_id,
	 * districtEntity district_id, byte[] data) { this.country_id1 = country_id;
	 * this.state_id1 = state_id; this.district_id1 = district_id; this.data = data;
	 * } public byte[] getData() { return data; }
	 */
	/*
	 * public void setData(byte[] data) { this.data = data; }
	 */
	public int getAll_id() {
		return all_id;
	}
	public void setAll_id(int all_id) {
		this.all_id = all_id;
	}
	public countryEntity getCountry_id1() {
		return country_id1;
	}
	public void setCountry_id1(countryEntity country_id1) {
		this.country_id1 = country_id1;
	}
	public stateEntity getState_id1() {
		return state_id1;
	}
	public void setState_id1(stateEntity state_id1) {
		this.state_id1 = state_id1;
	}
	public districtEntity getDistrict_id1() {
		return district_id1;
	}
	public void setDistrict_id1(districtEntity district_id1) {
		this.district_id1 = district_id1;
	}
	
	
	
}
