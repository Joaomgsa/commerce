package com.faro.commerce.repositories;

import com.faro.commerce.entities.Order;
import com.faro.commerce.entities.OrderItem;
import com.faro.commerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {


}
