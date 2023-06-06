package jpabook.jpashop.service;

import java.util.List;

import jpabook.jpashop.domain.item.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	/**
	 * @methodName  : saveItem
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 상품 등록
	 * @param item
	 */
	@Transactional
	public void saveItem(Item item) {
		itemRepository.save(item);
	}

	/**
	 * @methodName  : updateItem
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 변경 감지를 이용한 UPDATE 방법
	 * @param itemId, bookParam
	 */
	@Transactional
	public void updateItem(Long itemId, String name, int price, int stockQuantity) {
		Item findItem = itemRepository.findOne(itemId);
		findItem.setPrice(price);
		findItem.setName(name);
		findItem.setStockQuantity(stockQuantity);
	}

	/**
	 * @methodName  : findItems
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 상품 목록 조회
	 * @return
	 */
	public List<Item> findItems() {
		return itemRepository.findAll();
	}
	
	/**
	 * @methodName  : findItem
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 상품 조회
	 * @param itemId
	 * @return
	 */
	public Item findItem(Long itemId) {
		return itemRepository.findOne(itemId);
	}
	
	

}
