package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @packageName : jpabook.jpashop.domain
 * @fileName    : Address.java
 * @author      : 권유진
 * @date        : 2023.06.04
 * @description : 주소 (임베디드 타입)
 */
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	
	private String city;
	
	private String street;
	
	private String zipcode;

}
