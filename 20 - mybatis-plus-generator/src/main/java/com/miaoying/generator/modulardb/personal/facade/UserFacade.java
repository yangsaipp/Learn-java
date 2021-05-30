package com.miaoying.generator.modulardb.personal.facade;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miaoying.generator.modulardb.personal.appservice.UserAppService;
import com.miaoying.generator.modulardb.personal.model.UserDTO;
import com.miaoying.generator.modulardb.personal.model.UserVO;

/**
 * @auther CodeGenerator
 * @create 2021-05-30 23:59:04
 * @describe 人员和组织的关联对象表Facade
 */
@Service
public class UserFacade {

	/** */
	@Autowired
	private UserAppService objUserAppService;
	
	/**
	 * 根据id查询记录
	 * @param id id
	 * @return id对应的vo
	 */
	public UserVO get(String id) {
		return objUserAppService.get(id);
	}

	/**
	 * 新增
	 * @param objUserDTO objUserDTO
	 */
	public void add(UserDTO objUserDTO) {
		UserVO objUserVO = new UserVO();
		BeanUtils.copyProperties(objUserDTO, objUserVO);
		String id = objUserAppService.insert(objUserVO);
		objUserDTO.setUserId(id);
	}

	/**
	 * 更新
	 * @param objUserDTO objUserDTO
	 */
	public void update(UserDTO objUserDTO) {
		UserVO objUserVO = new UserVO();
		BeanUtils.copyProperties(objUserDTO, objUserVO);
		objUserAppService.update(objUserVO);
	}

	/**
	 * 删除
	 * @param ids 注解id
	 * @return 删除的记录
	 */
	public List<UserVO> delete(List<String> ids) {
		List<UserVO> lstUserVO = objUserAppService.queryList(ids);
		objUserAppService.delete(ids);
		return lstUserVO;
	}

}