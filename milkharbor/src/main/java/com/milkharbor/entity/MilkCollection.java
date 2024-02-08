package com.milkharbor.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class MilkCollection {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int m_id;
	private int milk_qnt;
	private float milk_fat;
	private float milk_lac_deg;
	
	@Transient
	private String fName;
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	private float milk_snf;
	public float getMilk_snf() {
		return milk_snf;
	}
	public void setMilk_snf(float milk_snf) {
		this.milk_snf = milk_snf;
	}
	private int f_id;
	private float price_per_liter;
	private float total;
	
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date  date_time;
	
	public float getPrice_per_liter() {
		return price_per_liter;
	}
	public void setPrice_per_liter(float price_per_liter) {
		this.price_per_liter = price_per_liter;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public int getMilk_qnt() {
		return milk_qnt;
	}
	public void setMilk_qnt(int milk_qnt) {
		this.milk_qnt = milk_qnt;
	}
	public float getMilk_fat() {
		return milk_fat;
	}
	public void setMilk_fat(float milk_fat) {
		this.milk_fat = milk_fat;
	}
	public float getMilk_lac_deg() {
		return milk_lac_deg;
	}
	public void setMilk_lac_deg(float milk_lac_deg) {
		this.milk_lac_deg = milk_lac_deg;
	}
	public Date getDate_time() {
		return date_time;
	}
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	public int getF_id() {
		return f_id;
	}
	public void setF_id(int f_id) {
		this.f_id = f_id;
	}
	
}
