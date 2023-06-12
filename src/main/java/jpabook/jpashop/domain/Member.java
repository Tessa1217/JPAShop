package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * @packageName : jpabook.jpashop.domain
 * @fileName    : Member.java
 * @author      : 권유진
 * @date        : 2023.06.04
 * @description : 회원
 */
@Entity
@Getter @Setter
public class Member {
	
	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	@NotEmpty
	private String name;
	
	@Embedded
	private Address address;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<Order>();

}
