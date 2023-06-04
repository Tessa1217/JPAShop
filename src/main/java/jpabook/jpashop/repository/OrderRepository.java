package jpabook.jpashop.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import lombok.RequiredArgsConstructor;

/**
 * @packageName : jpabook.jpashop.repository
 * @fileName    : OrderRepository.java
 * @author      : 권유진
 * @date        : 2023.06.04
 * @description : 주문 레포지토리
 */
@Repository
@RequiredArgsConstructor
public class OrderRepository {
	
	private final EntityManager em;
	
	/**
	 * @methodName  : save
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 등록
	 * @param order
	 */
	public void save(Order order) {
		em.persist(order);
	}
	
	/**
	 * @methodName  : findOne
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 조회
	 * @param orderId
	 * @return
	 */
	public Order findOne(Long orderId) {
		return em.find(Order.class, orderId);
	}
	
	/**
	 * @methodName  : findAll
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 주문 검색 목록 조회
	 * @return
	 */
	public List<Order> findAll() {
		return em.createQuery("select o from Order o join o.member m", Order.class).getResultList();
	}
	
	/**
	 * @methodName  : findAllByCriteria
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : Criteria로 동적 쿼리 생성
	 * @param orderSearch
	 * @return List<Order>
	 */
	public List<Order> findAllByCriteria(OrderSearch orderSearch) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Order> o = cq.from(Order.class);
		Join<Object, Object> m = o.join("member", JoinType.INNER);
		
		List<Predicate> criteria = new ArrayList<>();
		
		if(orderSearch.getOrderStatus() != null) {
			Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
			criteria.add(status);
		}
		
		if(StringUtils.hasText(orderSearch.getMemberName())) {
			Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
			criteria.add(name);
		}
		
		cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
		return query.getResultList();
			
	}
	
	/**
	 * @methodName  : findAll
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : QueryDSL 이용한 검색 쿼리
	 * @param orderSearch
	 * @return
	 */
	/*
	 * public List<Order> findAll(OrderSearch orderSearch) { QOrder order =
	 * QOrder.order; QMember member = QMember.member;
	 * 
	 * 
	 * 
	 * }
	 */

}
