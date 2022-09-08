package com.catalogolibri.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@RequiredArgsConstructor
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role_name")
	private Roles roleName;
	
	public Role(Roles roleName) {
		super();
		this.roleName = roleName;
	}
	
	

}
