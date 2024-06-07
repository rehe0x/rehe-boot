package com.rehe.modules.admin.system.mapstruct;

import com.rehe.common.mapstruct.MapstructBaseMapper;
import com.rehe.modules.admin.system.entity.User;
import com.rehe.modules.admin.system.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapstruct extends MapstructBaseMapper<UserVo, User> {
}
