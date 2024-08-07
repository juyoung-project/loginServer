package com.project.login.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.login.common.ResponsePojo;

@RestController
public class InitController {

	@RequestMapping(value = "/health/check")
	@ResponseBody
	public ResponsePojo healthCheck() {
		System.out.println("sayHellow1111");
		return ResponsePojo.success(null, "sayhellow");
	}
	
}
