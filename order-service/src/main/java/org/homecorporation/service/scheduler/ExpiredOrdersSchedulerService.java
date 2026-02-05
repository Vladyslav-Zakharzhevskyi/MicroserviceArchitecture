package org.homecorporation.service.scheduler;

public interface ExpiredOrdersSchedulerService {
    void findExpiredOrders();
    void releaseExpired();
}
