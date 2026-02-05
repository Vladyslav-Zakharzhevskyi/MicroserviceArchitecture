package org.homecorporation.repository;

import org.homecorporation.model.ReleaseReservationOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReleaseReservationOutBoxRepository extends JpaRepository<ReleaseReservationOutbox, UUID> {
    List<ReleaseReservationOutbox> findAllByStatusIn(List<ReleaseReservationOutbox.Status> status);
}
