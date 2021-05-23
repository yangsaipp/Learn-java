package com.miaoying.generator.modulardb.personal.service.impl;

import com.miaoying.generator.modulardb.personal.entity.User;
import com.miaoying.generator.modulardb.personal.mapper.UserMapper;
import com.miaoying.generator.modulardb.personal.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人员和组织的关联对象表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2021-05-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
