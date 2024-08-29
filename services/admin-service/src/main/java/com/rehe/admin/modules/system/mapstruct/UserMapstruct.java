package com.rehe.admin.modules.system.mapstruct;

import com.rehe.common.mapstruct.MapstructDtoBaseMapper;
import com.rehe.common.result.Page;
import com.rehe.admin.modules.system.dto.UserDto;
import com.rehe.admin.modules.system.dto.reqeust.UserCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.UserUpdateDto;
import com.rehe.admin.modules.system.entity.User;
import com.rehe.admin.modules.system.dto.response.UserResponseDto;
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
public interface UserMapstruct extends MapstructDtoBaseMapper<UserDto, User> {
    UserMapstruct INSTANCE = Mappers.getMapper(UserMapstruct.class);

    Page<UserResponseDto> toUserResponseDto(Page<UserDto> page);

    UserResponseDto toUserResponseDto(UserDto userDto);

    List<UserResponseDto> toUserResponseDto(List<UserDto> userDtoList);

    User toEntity(UserCreateDto userAddDto);

    User toEntity(UserUpdateDto userUpdateDto);


}
