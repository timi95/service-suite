package com.service.suite.orderservice.service;

import com.service.suite.orderservice.dto.OrderLineItemDto;
import com.service.suite.orderservice.dto.OrderRequest;
import com.service.suite.orderservice.model.Order;
import com.service.suite.orderservice.model.OrderLineItem;
import com.service.suite.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemList(orderLineItems);
        orderRepository.save(order);
    }

    private OrderLineItem mapToDto(OrderLineItemDto dto) {

        return new OrderLineItem()
                .builder()
                    .price(dto.getPrice())
                    .quantity(dto.getQuantity())
                    .skuCode(dto.getSkuCode())
                .build();
    }
}
