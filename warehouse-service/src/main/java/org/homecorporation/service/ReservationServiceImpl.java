package org.homecorporation.service;

import org.homecorporation.repo.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository repository;
    @Override
    public Long makeReservation(String productRef, Integer reservationCount) {
        return repository.reserve(productRef, reservationCount);
    }

    @Override
    public void cancelReservation(String productRef, Integer reservationCount) {
        repository.release(productRef, reservationCount);
    }

}
