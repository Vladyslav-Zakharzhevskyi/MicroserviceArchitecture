package org.homecorporation.service;

import org.homecorporation.dto.OrderCreatedDto;
import org.homecorporation.dto.ProductDTO;
import org.homecorporation.feign.ProductServiceClient;
import org.homecorporation.feign.WarehouseServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class OrderServiceImpl implements OrdersService {

    @Autowired
    private ProductServiceClient productServiceClient;
    @Autowired
    private WarehouseServiceClient warehouseServiceClient;

    @Override
    public OrderCreatedDto order(UUID productId, Integer count) {
        ProductDTO product = productServiceClient.getProductById(productId, true);
        Integer availabilityForItem = warehouseServiceClient.getAvailability(product.warehouseRef());

        
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new ProductNotFoundException(productId));
////        Boolean enoughForOrder2 = warehouseClient.isEnoughForOrder(new IsEnoughForOrderAvailabilityRequest(product.getWarehouseRef(), count));
////
////
////        System.out.println("1.");
//
//
//        Future<Boolean> future = fixedThreadPool.submit(() -> {
//            System.out.println("Поток: " + Thread.currentThread().getName());
//            return warehouseClient.isEnoughForOrder(new IsEnoughForOrderAvailabilityRequest(product.getWarehouseRef(), count));
//
//        });
//
//
//        Boolean hasAvailableCount = null;
//        try {
//            hasAvailableCount = future.get();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        return new OrderCreatedDto(productId, count, hasAvailableCount);
        return null;
    }
}
