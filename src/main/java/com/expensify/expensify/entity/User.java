package com.expensify.expensify.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.expensify.expensify.entity.split.Split;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(mappedBy = "groupUsers", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Group> userGroups;
	private String userFirstName;
	private String userLastName;
	private String userName;
	private String userMobileNumber;
	private String userEmail;

	// @Column(length = 60)
	private String userPassword;
//
	private Boolean enabled = false;
//    private String role;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Split> expenses;

}