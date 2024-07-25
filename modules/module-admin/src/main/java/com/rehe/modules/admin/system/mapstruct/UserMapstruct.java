package com.rehe.modules.admin.system.mapstruct;

import com.rehe.common.mapstruct.MapstructVoBaseMapper;
import com.rehe.common.result.Page;
import com.rehe.modules.admin.system.dto.UserDto;
import com.rehe.modules.admin.system.dto.reqeust.UserCreateDto;
import com.rehe.modules.admin.system.dto.reqeust.UserUpdateDto;
import com.rehe.modules.admin.system.entity.User;
import com.rehe.modules.admin.system.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapstruct extends MapstructVoBaseMapper<UserDto, User> {
    UserMapstruct INSTANCE = Mappers.getMapper(UserMapstruct.class);

    Page<UserResponseDto> toUserResponseDto(Page<UserDto> page);

    UserResponseDto toUserResponseDto(UserDto userDto);

    List<UserResponseDto> toUserResponseDto(List<UserDto> userDtoList);

    User toEntity(UserCreateDto userAddDto);

    User toEntity(UserUpdateDto userUpdateDto);


}
