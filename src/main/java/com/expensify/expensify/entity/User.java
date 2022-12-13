package com.expensify.expensify.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.expensify.expensify.entity.expense.Expense;
import com.expensify.expensify.entity.split.Split;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "username_unique", columnNames = "userName"),
		@UniqueConstraint(name = "email_unique", columnNames = "userEmail") })
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "groupUsers", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Group> userGroups;
	private String userFirstName;
	private String userLastName;
	@Column(unique = true)
	private String userName;
	private String userMobileNumber;
	@Column(unique = true)
	private String userEmail;

	// @Column(length = 60)
	private String userPassword;

	private Boolean enabled = false;

	@JsonIgnore
	private String role;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "expensePaidBy", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Expense> paidExpense;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Split> expenses;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dueA1mountPK.userFrom")
	@JsonIgnore
	private List<DueAmount> userFrom;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dueA1mountPK.userTo")
	@JsonIgnore
	private List<DueAmount> userTo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Activity> activities;

	@ManyToMany(fetch = FetchType.LAZY)
	@JsonIgnore
	private List<User> friends;

	public User(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date timestamp;

	@PrePersist
	private void onCreate() {
		timestamp = new Date();
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>() {
			{
				add(new SimpleGrantedAuthority(role));
			}
		};
		return authority;
	}

	@Override
	public String getPassword() {
		return this.userPassword;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	public String getUserName() {
		return userName;
	}

}