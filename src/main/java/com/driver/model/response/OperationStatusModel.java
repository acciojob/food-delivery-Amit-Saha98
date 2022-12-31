package com.driver.model.response;

import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@ToString
public class OperationStatusModel {

	@Enumerated(EnumType.STRING)
	private String operationResult;

	@Enumerated(EnumType.STRING)
	private String operationName;
	public String getOperationResult() {
		return operationResult;
	}

	public void setOperationResult(String operationResult) {
		this.operationResult = operationResult;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

}
