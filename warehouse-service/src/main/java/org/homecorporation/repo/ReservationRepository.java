package org.homecorporation.repo;

public interface ReservationRepository {
    Long reserve(String productRef, Integer reservationCount);
    void release(String productRef, Integer reservationCount);
}
