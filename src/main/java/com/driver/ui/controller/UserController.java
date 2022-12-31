package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.*;
import com.driver.service.UserService;
import com.driver.shared.dto.FoodDto;
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
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(userService.getUserByUserId(id),userResponse);
		return userResponse;
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails,userDto);
		userDto.setUserId(UUID.randomUUID().toString());
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(userService.createUser(userDto),userResponse);
		return userResponse;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserResponse userResponse = new UserResponse();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails,userDto);
		BeanUtils.copyProperties(userService.updateUser(id,userDto),userResponse);
		return userResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		OperationStatusModel operationStatusModel =new OperationStatusModel();

		if(userService.getUserByUserId(id)!=null) {
			userService.deleteUser(id);
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
	public List<UserResponse> getUsers(){
		List<UserDto>userDtoList=userService.getUsers();
		List<UserResponse>userDetailsResponseList=new ArrayList<>();
		for (UserDto userDto:userDtoList) {
			UserResponse userDetailsResponse = new UserResponse();
			BeanUtils.copyProperties(userDto,userDetailsResponse);
			userDetailsResponseList.add(userDetailsResponse);
		}
		return userDetailsResponseList;
	}
	
}
