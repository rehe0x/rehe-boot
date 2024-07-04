package com.rehe.auth.admin.mapstruct;

import com.rehe.auth.admin.entity.AuthUser;
import com.rehe.auth.admin.entity.User;
import com.rehe.common.mapstruct.MapstructVoBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthUserMapstruct extends MapstructVoBaseMapper<AuthUser, User> {
}
