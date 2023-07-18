package jpabook.jpashop.api.controller;

import jpabook.jpashop.api.dto.SimpleOrderDTO;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * x To One (ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 * */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        // 무한 루프 발생 : 별도의 로직이 필요 (JsonIgnore 등)
        // 지연 로딩인 경우에는 Proxy 객체를 반환하기 때문에 Proxy 관련 오류가 발생 (Hibernate 모듈(Hibernate5Module)을 별도로 설치 필요)
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDTO> orderV2() {
        /*
        V2의 문제점: simpleOrderDTOList를 생성하기 위해 orders 루프를 돌릴 경우
                  Member의 이름과 Delivery의 주소를 가지고 올 때 LAZY 로딩으로 인하여 SELECT 쿼리가 하나하나 나가는 문제점이 발생

         */
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<SimpleOrderDTO> simpleOrderDTOList = orders.stream().map(o -> new SimpleOrderDTO(o)).collect(Collectors.toList());
        return simpleOrderDTOList;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDTO> orderV3() {
        // Fetch Join을 사용할 시 LAZY 로딩으로 되어있어도 JOIN해서 한번에 들고옴, N + 1 문제 해결할 수 있음
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDTO> simpleOrderDTOList = orders.stream().map(o -> new SimpleOrderDTO(o)).collect(Collectors.toList());
        return simpleOrderDTOList;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderDTO> orderV4() {
        /*
        V3 vs. V4
        V4는 화면에 최적화되어있지만 (원하는 필드만 끌고오기 때문에) 재사용성은 떨어짐
        V3의 경우에는 엔티티를 조회했기 때문에 내부에서 로직을 추가할 수 있으므로 재사용성이 V4보다 높음
        */
//        return orderRepository.findOrderDTOs();
        return orderSimpleQueryRepository.findOrderDTOs();
    }


}
