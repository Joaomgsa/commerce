package com.faro.commerce.services;

import com.faro.commerce.dto.OrderDTO;
import com.faro.commerce.dto.OrderItemDTO;
import com.faro.commerce.dto.ProductDTO;
import com.faro.commerce.entities.*;
import com.faro.commerce.repositories.OrderItemRepository;
import com.faro.commerce.repositories.OrderRepository;
import com.faro.commerce.repositories.ProductRepository;
import com.faro.commerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Pedido não Encontrado"));
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order entity = new Order();

        entity.setMoment(Instant.now());
        entity.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        entity.setClient(user);

        for(OrderItemDTO itemDTO : dto.getItems()){
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(entity, product, itemDTO.getQuantity(), itemDTO.getPrice());
            entity.getItems().add(item);
        }
        repository.save(entity);
        orderItemRepository.saveAll(entity.getItems());

        return new OrderDTO(entity);
    }
}
