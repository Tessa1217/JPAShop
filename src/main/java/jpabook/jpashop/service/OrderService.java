package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	private final MemberRepository memberRepository;
	
	private final ItemRepository itemRepository;
	
	/**
	 * @methodName  : order
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 등록
	 * @param memberId
	 * @param itemId
	 * @param count
	 * @return Long
	 */
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		
		// 필요한 엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);
		
		// 배송 정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		
		// 주문 상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
		
		// 주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);
		
		// 주문 등록
		orderRepository.save(order);
		
		return order.getId();
		
	}
	
	/**
	 * @methodName  : cancelOrder
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 취소
	 * @param orderId
	 */
	@Transactional
	public void cancelOrder(Long orderId) {
		
		// 주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);
		
		// 주문 취소 (별도로 UPDATE 쿼리 날리지 않아도 JPA에서 Dirty Checking 해서 변경 상태에 대해서 반영함)
		order.cancelOrder();
		
	}
	
	/**
	 * @methodName  : searchOrders
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 검색
	 * @return List<Order>
	 */
	public List<Order> searchOrders() {
		return orderRepository.findAll();
	}

}
