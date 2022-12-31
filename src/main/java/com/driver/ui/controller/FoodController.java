package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
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

import javax.persistence.Access;

@RestController
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodService foodService;

	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodDto foodDto = foodService.getFoodById(id);
		FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
		BeanUtils.copyProperties(foodDto,foodDetailsResponse);
		return foodDetailsResponse;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		FoodDto foodDto = new FoodDto();
		foodDto.setFoodId(UUID.randomUUID().toString());
		BeanUtils.copyProperties(foodDetails,foodDto);
		FoodDto responseDto = foodService.createFood(foodDto);
		FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
		BeanUtils.copyProperties(responseDto,foodDetailsResponse);
		return foodDetailsResponse;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDto foodDto=new FoodDto();
		BeanUtils.copyProperties(foodDetails,foodDto);
		FoodDto response =foodService.updateFoodDetails(id,foodDto);
		FoodDetailsResponse foodDetailsResponse=new FoodDetailsResponse();
		BeanUtils.copyProperties(response,foodDetailsResponse);

		return foodDetailsResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		OperationStatusModel operationStatusModel =new OperationStatusModel();

		if(foodService.getFoodById(id)!=null) {
			foodService.deleteFoodItem(id);
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
	public List<FoodDetailsResponse> getFoods() {
		List<FoodDto>foodDtoList=foodService.getFoods();
		List<FoodDetailsResponse>foodDetailsResponseList=new ArrayList<>();
		for (FoodDto foodDto:foodDtoList) {
			FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
			BeanUtils.copyProperties(foodDto,foodDetailsResponse);
			foodDetailsResponseList.add(foodDetailsResponse);
		}
		return foodDetailsResponseList;
	}
}
