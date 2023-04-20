package com.service.suite.orderservice.service;

import com.service.suite.orderservice.dto.InventoryResponse;
import com.service.suite.orderservice.dto.OrderLineItemDto;
import com.service.suite.orderservice.dto.OrderRequest;
import com.service.suite.orderservice.model.Order;
import com.service.suite.orderservice.model.OrderLineItem;
import com.service.suite.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemList(orderLineItems);
        List<String> skuCodes = order.getOrderLineItemList().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();
        // Call Inventory Service, and place order if product
        // is in stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder
                                .queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::getIsInStock);
        if(allProductsInStock){
            orderRepository.save(order);
            return "order placed successfully";
        } else {
            throw new IllegalArgumentException("Product is Not in stock, please try again later");
        }
    }

    private OrderLineItem mapToDto(OrderLineItemDto dto) {

        return OrderLineItem
                .builder()
                    .price(dto.getPrice())
                    .quantity(dto.getQuantity())
                    .skuCode(dto.getSkuCode())
                .build();
    }
}
