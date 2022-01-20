package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class,id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {

        String jqpl = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jqpl += " where";
                isFirstCondition = false;
            }
            else jqpl+= " and";
        }
        return em.createQuery(jqpl , Order.class)
                .setMaxResults(1000) // 최대 1000 건
                .getResultList();
    }

}
