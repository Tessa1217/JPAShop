package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @packageName : jpabook.jpashop.domain
 * @fileName    : OrderSearch.java
 * @author      : 권유진
 * @date        : 2023.06.04
 * @description : 주문 검색
 */
@Getter @Setter
public class OrderSearch {
	
	private String memberName;
	
	private OrderStatus orderStatus;

}
