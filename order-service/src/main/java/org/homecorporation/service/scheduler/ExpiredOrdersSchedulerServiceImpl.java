package org.homecorporation.service.scheduler;

import org.homecorporation.exception.CantReleaseReservationExceptionForOrder;
import org.homecorporation.feign.WarehouseReservationClient;
import org.homecorporation.model.Order;
import org.homecorporation.model.OrderItem;
import org.homecorporation.model.ReleaseReservationOutbox;
import org.homecorporation.repository.OrderRepository;
import org.homecorporation.repository.ReleaseReservationOutBoxRepository;
import org.homecorporation.service.OrdersService;
import org.homecorporation.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.homecorporation.model.ReleaseReservationOutbox.Status.FAILED;
import static org.homecorporation.model.ReleaseReservationOutbox.Status.TO_EXECUTE;
import static org.homecorporation.util.TimeUtil.ZONE_ID_KYIV;

@Component
public class ExpiredOrdersSchedulerServiceImpl implements ExpiredOrdersSchedulerService {
    private static final Logger log = LoggerFactory.getLogger(ExpiredOrdersSchedulerServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WarehouseReservationClient warehouseReservationClient;
    @Autowired
    private ExecutorService forkJoinPool;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ReleaseReservationOutBoxRepository releaseReservationOutBoxRepository;

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void findExpiredOrders() {

        List<Order> orders = orderRepository.findExpired(TimeUtil.getDateTimeAt(ZONE_ID_KYIV));

        for (Order order : orders) {
            ordersService.makeOrderExpiredAndSetUpReleaseReservation(order);
        }
    }

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    @Override
    public void releaseExpired() {
        List<ReleaseReservationOutbox> candidates = releaseReservationOutBoxRepository.findAllByStatusIn(List.of(TO_EXECUTE, FAILED));

        for (ReleaseReservationOutbox expired : candidates) {
            try {
                ordersService.release(expired.getWarehouseRef(), expired.getQuantity());
                expired.setStatus(ReleaseReservationOutbox.Status.COMPLETED);
                expired.setFinishedAt(TimeUtil.getDateTimeAt(ZONE_ID_KYIV));
                //reduce amount of used memory in db
//                expired.setLastErrorMessage("");
            } catch (Exception ex) {
                expired.setLastErrorMessage(ex.getMessage());
                expired.setStatus(ReleaseReservationOutbox.Status.FAILED);
            } finally {
                releaseReservationOutBoxRepository.save(expired);
            }
        }

    }
}
