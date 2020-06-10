package com.tyss.survey.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminResponse {

	private boolean error;

	private String message;

	private Object data;


}
