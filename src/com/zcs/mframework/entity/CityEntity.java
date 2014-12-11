package com.zcs.mframework.entity;

public class CityEntity {
	private String cityName;// 城市名称
	private String sortCode;// 首字母

	public CityEntity() {
	}

	public CityEntity(String sortCode, String cityName) {
		this.sortCode = sortCode;
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

}
