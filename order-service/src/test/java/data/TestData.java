package data;

import org.homecorporation.model.Order;
import org.homecorporation.model.OrderItem;
import org.homecorporation.util.TimeUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestData {

    public static Order orderWithItems() {
        Order order = new Order();
        order.setStatus(Order.Status.CREATED);
        order.setExpiredAt(TimeUtil.getDateTimeAt(TimeUtil.ZONE_ID_KYIV).plusMinutes(15));

        List<OrderItem> orderItems = new ArrayList<>();
        order.setItems(orderItems);

        orderItems.add(makeOrderItem(order, "REF-000", new BigDecimal("900.00"), new BigDecimal("1800"), 2));
        orderItems.add(makeOrderItem(order, "REF-001", new BigDecimal("1000.00"), new BigDecimal("3000"), 3));

        return order;
    }

    private static OrderItem makeOrderItem(Order order, String warehouseRef, BigDecimal productPrice, BigDecimal totalPrice, Integer quantity) {
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setWarehouseRef(warehouseRef);
        item.setProductPrice(productPrice);
        item.setProductId(UUID.randomUUID());
        item.setTotalPrice(totalPrice);
        item.setQuantity(quantity);
        return item;
    }

}
