package com.rehe.modules.admin.system.mapstruct;

import com.rehe.common.mapstruct.MapstructVoBaseMapper;
import com.rehe.modules.admin.system.entity.User;
import com.rehe.modules.admin.system.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapstruct extends MapstructVoBaseMapper<UserVo, User> {
    UserMapstruct INSTANCE = Mappers.getMapper(UserMapstruct.class);
}
