package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.*;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderService orderService;

	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{
		OrderDetailsResponse orderResponse = new OrderDetailsResponse();
		BeanUtils.copyProperties(orderService.getOrderById(id),orderResponse);
		return orderResponse;
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderId(UUID.randomUUID().toString());
		BeanUtils.copyProperties(order,orderDto);
		orderService.createOrder(orderDto);
		OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
		BeanUtils.copyProperties(orderDto,orderDetailsResponse);
		return orderDetailsResponse;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		OrderDetailsResponse orderResponse = new OrderDetailsResponse();
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order,orderDto);
		BeanUtils.copyProperties(orderService.updateOrderDetails(id,orderDto),orderResponse);
		return orderResponse;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		OperationStatusModel operationStatusModel =new OperationStatusModel();

		if(orderService.getOrderById(id)!=null) {
			orderService.deleteOrder(id);
			operationStatusModel.setOperationName(RequestOperationName.DELETE.toString());
			operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.toString());
		}
		else {
			operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.toString());
			operationStatusModel.setOperationName(RequestOperationName.DELETE.toString());
		}
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDto>orderDtoList=orderService.getOrders();
		List<OrderDetailsResponse>orderDetailsResponseList=new ArrayList<>();
		for (OrderDto orderDto:orderDtoList) {
			OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
			BeanUtils.copyProperties(orderDto,orderDetailsResponse);
			orderDetailsResponseList.add(orderDetailsResponse);
		}
		return orderDetailsResponseList;
	}
}
