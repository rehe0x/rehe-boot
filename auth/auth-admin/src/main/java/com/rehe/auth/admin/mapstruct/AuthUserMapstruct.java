package com.rehe.auth.admin.mapstruct;

import com.rehe.auth.admin.dto.AuthMenuDto;
import com.rehe.auth.admin.dto.AuthUserDto;
import com.rehe.auth.admin.dto.JwtUserDto;
import com.rehe.auth.admin.dto.response.AuthMenuResponseDto;
import com.rehe.auth.admin.entity.User;
import com.rehe.auth.admin.dto.response.AuthUserResponseDto;
import com.rehe.common.mapstruct.MapstructDtoBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthUserMapstruct{
    AuthUserMapstruct INSTANCE = Mappers.getMapper(AuthUserMapstruct.class);


    @Mappings({
            @Mapping(source = "id", target = "userId")
    })
    AuthUserDto toDto(User user);

    JwtUserDto toDto(AuthUserDto authUserDto);

    AuthUserResponseDto toResponseDto(AuthUserDto authUserDto);

    AuthMenuResponseDto toResponseDto(AuthMenuDto authMenuDto);

    List<AuthMenuResponseDto> toResponseDto(List<AuthMenuDto> authMenuDto);

}
