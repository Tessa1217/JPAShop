package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
	
	private final EntityManager em;
	
	/**
	 * @methodName  : save
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 상품 등록
	 * @param item
	 */
	public void save(Item item) {
		if (item.getId() == null) {
			em.persist(item);
		} else {
			em.merge(item);
		}
	}
	
	/**
	 * @methodName  : findOne
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 상품 조회
	 * @param itemId
	 * @return
	 */
	public Item findOne(Long itemId) {
		return em.find(Item.class, itemId);
	}
	
	/**
	 * @methodName  : findAll
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 상품 목록 조회
	 * @return
	 */
	public List<Item> findAll() {
		return em.createQuery("select i from Item i", Item.class)
				 .getResultList();
	}

}
