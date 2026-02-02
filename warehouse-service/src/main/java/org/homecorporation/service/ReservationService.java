package org.homecorporation.service;

public interface ReservationService {
    Long makeReservation(String productRef, Integer reservationCount);

    void cancelReservation(String productRef, Integer reservationCount);
}
















