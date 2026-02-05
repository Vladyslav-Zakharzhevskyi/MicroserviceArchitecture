import data.TestData;
import org.homecorporation.OrdersServiceApplication;
import org.homecorporation.model.Order;
import org.homecorporation.model.ReleaseReservationOutbox;
import org.homecorporation.repository.OrderItemRepository;
import org.homecorporation.repository.OrderRepository;
import org.homecorporation.repository.ReleaseReservationOutBoxRepository;
import org.homecorporation.service.OrdersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import testcontainers.PostgresTestContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = OrdersServiceApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderServiceTest extends PostgresTestContainer {
    @Autowired
    private OrdersService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ReleaseReservationOutBoxRepository outboxRepository;

    @AfterEach
    void tearDown() {
        orderItemRepository.deleteAllInBatch();
        outboxRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
    }

    @Test
    void shouldSaveOrderWithOrdersItems_successfully() {
        Order order = TestData.orderWithItems();
        Order saved = orderRepository.save(order);

        Order reloadedOrder = orderRepository.findById(saved.getId()).orElseThrow();
        Assertions.assertNotNull(reloadedOrder);

        orderService.makeOrderExpiredAndSetUpReleaseReservation(saved);

        //outbox saved
        List<ReleaseReservationOutbox> findOutbox = outboxRepository.findAll();
        Assertions.assertEquals(order.getItems().size(), findOutbox.size());
        //status updated
        Order reloadedOrderWithExpiredStatus = orderRepository.findById(saved.getId()).orElseThrow();
        assertThat(reloadedOrderWithExpiredStatus.getStatus()).isEqualTo(Order.Status.EXPIRED);

    }

    @Test
    void shouldRollbackEverything_whenExceptionOccurs() {
        Order order = TestData.orderWithItems();
        Order saved = orderRepository.save(order);

        Order reloadedOrder = orderRepository.findById(saved.getId()).orElseThrow();
        Assertions.assertNotNull(reloadedOrder);

        assertThatThrownBy(() -> {
                    //set field in null where db expected to be not null
                    saved.setExpiredAt(null);
                    orderService.makeOrderExpiredAndSetUpReleaseReservation(saved);
                }
        ).isInstanceOf(DataIntegrityViolationException.class);

        // outbox has not saved
        assertThat(outboxRepository.findAll()).isEmpty();

        // order is not EXPIRED
        Order reloaded = orderRepository.findById(saved.getId()).orElseThrow();
        assertThat(reloaded.getStatus()).isNotEqualTo(Order.Status.EXPIRED);
    }



}
