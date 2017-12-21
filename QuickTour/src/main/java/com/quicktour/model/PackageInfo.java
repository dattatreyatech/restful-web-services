package com.quicktour.model;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@ApiModel
@XmlRootElement
public class PackageInfo {
	protected String packageName;
	protected float amount;
	protected String comments;
	protected String status;
	protected String orderNum;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNnum(String orderNo) {
		this.orderNum = orderNo;
	}

}