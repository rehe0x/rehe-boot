package com.rehe.modules.admin.system.service.impl;

import com.rehe.modules.admin.system.entity.User;
import com.rehe.modules.admin.system.mapper.UserMapper;
import com.rehe.modules.admin.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author xiech
 * @since 2024-01-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
