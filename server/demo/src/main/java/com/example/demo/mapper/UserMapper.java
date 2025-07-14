package com.example.demo.mapper;

import org.mapstruct.Mapper;
import com.example.demo.dto.request.UserSignRequest;
import com.example.demo.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity UserSignRequestToUserEntity(UserSignRequest userSignRequest);

}
