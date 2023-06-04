package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

/**
 * @packageName : jpabook.jpashop.domain
 * @fileName    : Category.java
 * @author      : 권유진
 * @date        : 2023.06.04
 * @description : 카테고리
 */
@Entity
@Getter @Setter
public class Category {
	
	@Id @GeneratedValue
	@Column(name="CATEGORY_ID")
	private Long id;
	
	private String name;
	
	@ManyToMany
	@JoinTable(name="CATEGORY_ITEM",
	           joinColumns = @JoinColumn(name="CATEGORY_ID"),
	           inverseJoinColumns = @JoinColumn(name="ITEM_ID")
	)
	private List<Item> items = new ArrayList<Item>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID")
	private Category parent;
	
	@OneToMany(mappedBy="parent", cascade = CascadeType.ALL)
	private List<Category> child = new ArrayList<Category>();
	

}
