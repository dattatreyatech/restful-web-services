package com.quicktour.model;

import io.swagger.annotations.ApiModel;

@ApiModel
public class UpdatePackage {
	protected String newPackageName;
	protected String comments;

	public String getNewPackageName() {
		return newPackageName;
	}

	public void setNewPackageName(String newPackageName) {
		this.newPackageName = newPackageName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}