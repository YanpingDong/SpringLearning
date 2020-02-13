package com.dyp.demo.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.servlet.http.HttpServletRequest;

@ApiModel(description = "Uniform error")
public class Error {
	
	@ApiModelProperty(required = true, value = "Api address, such as /closerTV/epg/tvservice/list")
	private String uri;
	
	@ApiModelProperty(required = true, value = "Which kind of method do you invoke, such as GET/PUT/POST/DELETE")
	private String method;
	
	@ApiModelProperty(required = true, value = "Custom error code, such as 499 mean unknow error")
	private Integer errorCode;
	
	@ApiModelProperty(required = true, value = "error detial info")
	private String errorMessage;
	
	@ApiModelProperty(required = false, value = "other info, commonly using for error description")
	private String moreInfo;
	
	public Error(){}
	
	public Error(String uri, String method, Integer errorCode, String errorMessage, String moreInfo){
		this.uri = uri;
		this.method = method;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.moreInfo = moreInfo;
	}
	
	public Error(HttpServletRequest request, Integer errorCode, String errorMessage, String moreInfo){
		this.uri = request.getRequestURI();
		this.method = request.getMethod();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.moreInfo = moreInfo;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getMoreInfo() {
		return moreInfo;
	}
	
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

}