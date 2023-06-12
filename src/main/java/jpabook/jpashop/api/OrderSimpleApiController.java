package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        // 무한 루프 발생 : 별도의 로직이 필요 (JsonIgnore 등)
        // 지연 로딩인 경우에는 Proxy 객체를 반환하기 때문에 Proxy 관련 오류가 발생 (Hibernate 모듈(Hibernate5Module)을 별도로 설치 필요)
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        return all;
    }


}
