package org.homecorporation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "warehouse-service", url = "${eco.services.urls.warehouse}" + "/availability")
public interface WarehouseServiceClient {
    @RequestMapping(path = "/{ref}", method = RequestMethod.GET)
    Integer getAvailability(@PathVariable(name = "ref") String ref);

}
