package com.project.login.repository;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.project.login.common.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // pk 자동증가는 id, generate type 설정이 필요하다. 
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(name="token")
	private String token;
	
	@Column(name="isDeleted")
	private boolean isDeleted;
	
	@CreationTimestamp
	@Column(name="create_time")
	private Date createTime;
	
	@UpdateTimestamp
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="last_token_check_time")
	private Date lastTokenCheckTime;
	
	
}
