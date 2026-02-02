package org.homecorporation.api;

import org.homecorporation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/warehouse/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.POST, path = "/reserve/{productRef}/{count}")
    public Long makeReservation(@PathVariable String productRef,
                                @PathVariable(value = "count") Integer reservationCount) {
        if (reservationCount <= 0) {
            return 0L;
        } else {
           return reservationService.makeReservation(productRef, reservationCount);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/cancelReservation/{productRef}/{count}")
    public Boolean cancelReservation(@PathVariable String productRef,
                                     @PathVariable(value = "count") Integer reservationCount) {
        if (reservationCount <= 0) {
            return false;
        } else {
            reservationService.cancelReservation(productRef, reservationCount);
            return Boolean.TRUE;
        }
    }

}
