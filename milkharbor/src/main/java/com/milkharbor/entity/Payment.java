package com.milkharbor.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int p_id;
	private int f_id;
	private int milk_coll_id;
	private float total_bill;
	private String status;
	private Date date_time;
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public int getF_id() {
		return f_id;
	}
	public void setF_id(int f_id) {
		this.f_id = f_id;
	}
	public int getMilk_coll_id() {
		return milk_coll_id;
	}
	public void setMilk_coll_id(int milk_coll_id) {
		this.milk_coll_id = milk_coll_id;
	}
	public float getTotal_bill() {
		return total_bill;
	}
	public void setTotal_bill(float total_bill) {
		this.total_bill = total_bill;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDate_time() {
		return date_time;
	}
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	
}
