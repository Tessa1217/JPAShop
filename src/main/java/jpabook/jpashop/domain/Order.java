package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @packageName : jpabook.jpashop.domain
 * @fileName    : Order.java
 * @author      : 권유진
 * @date        : 2023.06.04
 * @description : 주문
 */
@Entity
@Getter @Setter
@Table(name = "ORDERS")
public class Order {
	
	@Id @GeneratedValue
	@Column(name="ORDER_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="DELIVERY_ID")
	private Delivery delivery;
	
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	// 연관관계 메서드
	
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}
	
	// 생성 메서드
	
	 /**
	 * @methodName  : createOrder
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 생성
	 * @param member
	 * @param delivery
	 * @param orderItems
	 * @return Order
	 */
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		 Order order = new Order();
		 order.setMember(member);
		 order.setDelivery(delivery);
		 for (OrderItem orderItem : orderItems) {
			 order.addOrderItem(orderItem);
		 }
		 order.setStatus(OrderStatus.ORDER);
		 order.setOrderDate(LocalDateTime.now());
		 return order;
	 }
	 
	/**
	 * 도메인 모델 패턴 (Domain Model Pattern)
	 * 서비스 계층은 단순히 엔티티에 필요한 요청을 위임하는 역할만을 수행, 엔티티가 비지니스 로직을 가지고 객체 지향의 특성을 적극 활용하는 패턴
	 * 
	 * (<->) 트랜잭션 스크립트 패턴 (Transaction Script Pattern)
	 * 흔히 사용을 많이 하던 패턴으로 엔티티에는 비지니스 로직이 거의 없고 서비스 계층에서 대부분의 비지니스 로직을 처리하는 패턴 
	 */
	 // 비지니스 로직
	 
	 /**
	 * @methodName  : cancelOrder
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 취소
	 */
	public void cancelOrder() {
		 if (delivery.getStatus() == DeliveryStatus.COMP) {
			 throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
		 } 
		 this.setStatus(OrderStatus.CANCEL);
		 for (OrderItem orderItem : orderItems) {
			 orderItem.cancelOrderItem();
		 }
	 }
	
	// 조회 로직
	/**
	 * @methodName  : getTotalPrice
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 전체 가격 조회
	 * @return
	 */
	public Integer getTotalPrice() {
		return orderItems.stream()
				         .mapToInt(OrderItem::getOrderItemTotalPrice)
				         .sum();
	}
	
}
