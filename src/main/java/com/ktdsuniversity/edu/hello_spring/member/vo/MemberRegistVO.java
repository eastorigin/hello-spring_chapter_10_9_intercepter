package com.ktdsuniversity.edu.hello_spring.member.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MemberRegistVO {

	@NotBlank(message = "이메일을 입력해주세요")
	@Email(message = "올바른 이메일 형식을 입력해주세요")
	private String email;
	
	@NotBlank(message = "이름을 입력해주세요")
	private String name;
	
	@NotBlank(message = "비밀번호를 입력해주세요")
	@Size(min = 10, message = "비밀번호는 최소 10자리 이상 입력해주세요")
	private String password;
	
	@NotBlank(message = "비밀번호 확인을 입력해주세요")
	private String confirmPassword;
	
	private String salt;
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
