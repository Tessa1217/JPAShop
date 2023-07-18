package jpabook.jpashop.repository.order.simplequery;

import jpabook.jpashop.api.dto.SimpleOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private EntityManager em;

    public List<SimpleOrderDTO> findOrderDTOs() {
        return em.createQuery(
                "select new jpabook.jpashop.api.dto.SimpleOrderDTO(o.id, m.name, o.orderDate, o.status, d.address) from Order o" +
                        " join o.member m" +
                        " join o.delivery d", SimpleOrderDTO.class
        ).getResultList();
    }

}
