package org.homecorporation.repository;

import org.homecorporation.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query(""" 
        SELECT o
        FROM Order o
        WHERE o.expiredAt < :now
        AND o.status NOT IN ('COMPLETED', 'EXPIRED', 'PAYMENT_COMPLETED')
        """)
    List<Order> findExpired(@Param("now") ZonedDateTime zdt);
}
