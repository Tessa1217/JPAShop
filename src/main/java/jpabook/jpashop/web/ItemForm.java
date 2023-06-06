package jpabook.jpashop.web;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemForm {

    private Long id;

    private String name;

    private Integer price;

    private Integer stockQuantity;

    private String author;

    private String isbn;
}
