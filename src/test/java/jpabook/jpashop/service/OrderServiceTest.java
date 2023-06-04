package jpabook.jpashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
	
	@Autowired
	EntityManager em;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Test
	public void order() throws Exception {
		
		// given
		
		Member member = createMember();
		
		Item item = createItem();
		
		Integer orderCount = 2;
		
		// when
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
		
		// then
		Order getOrder = orderRepository.findOne(orderId);
		
		assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
		assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, getOrder.getOrderItems().size());
		assertEquals(1, getOrder.getOrderItems().size());
		
	}
	
	@Test
	public void cancel() throws Exception {
		
		// given
		Member member = createMember();
		Item item = createItem();
		
		int orderCount = 2;
		
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
		
		// when
		orderService.cancelOrder(orderId);
		
		// then
		Order getOrder = orderRepository.findOne(orderId);
		assertEquals("주문 취소시 상태는 CANCEL이다.", OrderStatus.CANCEL, getOrder.getStatus());
		/* assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity()); */
		
	}
	
	@Test(expected = NotEnoughStockException.class)
	public void stockOver() throws Exception {
		
		// given
		Member member = createMember();
		
		Item item = createItem();
		
		Integer orderCount = 12;
		
		// when
		
		orderService.order(member.getId(), item.getId(), orderCount);
		
		// then
		fail("재고 수량 부족 예외가 발생해야 한다.");
		
	}
	
	private Member createMember() {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "경기", "123-123"));
		em.persist(member);
		return member;
	}
	
	private Item createItem() {
		Item item = new Book();
		item.setName("JPA 강의 책");
		item.setPrice(10000);
		item.setStockQuantity(10);
		em.persist(item);
		return item;
	}

}
