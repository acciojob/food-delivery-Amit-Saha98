package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto user) throws Exception {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user,userEntity);
        userRepository.save(userEntity);
        return user;
    }

    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity = userRepository.findByEmail(email);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity,userDto);
        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity,userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userRepository.save(userEntity);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity,userDto);
        return userDto;
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        userRepository.delete(userRepository.findByUserId(userId));
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity>userEntities=(List<UserEntity>)userRepository.findAll();
        List<UserDto>userDtoList=new ArrayList<>();
        for(UserEntity entity:userEntities){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(entity,userDto);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}