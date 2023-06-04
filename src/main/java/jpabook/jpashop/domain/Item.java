package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

/**
 * @packageName : jpabook.jpashop.domain
 * @fileName    : Item.java
 * @author      : 권유진
 * @date        : 2023.06.04
 * @description : 상품
 */
@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
	
	@Id @GeneratedValue
	@Column(name="ITEM_ID")
	private Long id;
	
	private String name;
	
	private Integer price;
	
	private Integer stockQuantity;
	
	@ManyToMany(mappedBy = "items", cascade = CascadeType.ALL)
	private List<Category> categories = new ArrayList<Category>();
	
	// 비지니스 로직
	
	/**
	 * @methodName  : increaseStock
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 재고 수량 더하기
	 * @param quantity
	 */
	public void increaseStock(Integer quantity) {
		this.stockQuantity += quantity;
	}
	
	/**
	 * @methodName  : decreaseStock
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 재고 수량 빼기
	 * @param quantity
	 */
	public void decreaseStock(Integer quantity) {
		Integer restStock = this.stockQuantity - quantity;
		if(restStock < 0) {
		throw new NotEnoughStockException("need more stock");	
		}
		this.stockQuantity = restStock;
	}

}
