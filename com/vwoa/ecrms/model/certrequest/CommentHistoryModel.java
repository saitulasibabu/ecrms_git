/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.model.certrequest;

import java.io.Serializable;

import com.vwoa.ecrms.model.common.BaseModel;

/**
 * @author aparna_deshmukh01
 * 
 */
public class CommentHistoryModel extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5140203980677875682L;
	
	private Integer commentNum; // variable to hold unique comment sequence number for request.
	
	private String commentText; // variable to hold Request Comment text

	/**
	 * @return the commentNum
	 */
	public Integer getCommentNum() {
		return commentNum;
	}

	/**
	 * @param commentNum the commentNum to set
	 */
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	/**
	 * @return the commentText
	 */
	public String getCommentText() {
		return commentText;
	}

	/**
	 * @param commentText the commentText to set
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	
}
