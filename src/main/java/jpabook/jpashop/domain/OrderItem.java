package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @packageName : jpabook.jpashop.domain
 * @fileName    : OrderItem.java
 * @author      : 권유진
 * @date        : 2023.06.04
 * @description : 주문 상품
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자에 대한 제약
public class OrderItem {
	
	@Id @GeneratedValue
	@Column(name="ORDER_ITEM_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ITEM_ID")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORDER_ID")
	private Order order;
	
	private Integer orderPrice;
	
	private Integer count;
	
	// 생성 메서드
	
	/**
	 * @methodName  : createOrderItem
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 상품 생성
	 * @param item
	 * @param orderPrice
	 * @param count
	 * @return OrderItem
	 */
	public static OrderItem createOrderItem(Item item, Integer orderPrice, Integer count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		item.decreaseStock(count);
		return orderItem;
	}
	
	// 비지니스 로직
	
	/**
	 * @methodName  : cancelOrderItem
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 아이템 취소 (재고 수량 원복)
	 */
	public void cancelOrderItem() {
		getItem().increaseStock(count);
	}
	
	/**
	 * @methodName  : getOrderItemTotalPrice
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 상품 금액
	 * @return
	 */
	public Integer getOrderItemTotalPrice() {
		return getOrderPrice() * getCount();
	}

}
