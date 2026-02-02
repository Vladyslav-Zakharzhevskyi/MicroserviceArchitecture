package org.homecorporation.api;

import org.homecorporation.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/warehouse/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(path = "/{ref}", method = RequestMethod.GET)
    public Integer getAvailability(@PathVariable("ref") String ref) {
        return inventoryService.getInventory(ref);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Integer> getAvailability(@RequestBody List<String> refs) {
        return inventoryService.getInventory(refs);
    }

}
