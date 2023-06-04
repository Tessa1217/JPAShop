package jpabook.jpashop.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * @packageName : jpabook.jpashop.domain
 * @fileName    : Delivery.java
 * @author      : 권유진
 * @date        : 2023.06.04
 * @description : 배송
 */
@Entity
@Getter @Setter
public class Delivery {
	
	@Id @GeneratedValue
	@Column(name="DELIVERY_ID")
	private Long id;
	
	@OneToOne(mappedBy = "delivery", cascade = CascadeType.ALL)
	private Order order;
	
	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;

}
