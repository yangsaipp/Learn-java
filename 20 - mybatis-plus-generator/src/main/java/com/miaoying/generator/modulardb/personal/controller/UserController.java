package com.miaoying.generator.modulardb.personal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miaoying.generator.modulardb.personal.facade.UserFacade;
import com.miaoying.generator.modulardb.personal.model.UserDTO;
import com.miaoying.generator.modulardb.personal.model.UserVO;

/**
 * @auther CodeGenerator
 * @create 2021-05-30 23:59:04
 * @describe 人员和组织的关联对象表前端控制器
 */

@RestController
@RequestMapping("/personal")
public class UserController {
	
	/** 注入Facade  */
	@Autowired
	private UserFacade objUserFacade;

	
	/**
	 * 根据id查询信息
	 * @param id id
	 * @return objUserVO
	 */
	@GetMapping("/getUser")
	public UserVO getUser(@RequestParam String id){
		return objUserFacade.get(id);
	}
	
	/**
	 * 新增
	 * @param objUserDTO objUserDTO
	 */
	@PostMapping("/addUser")
	public void addUser(@RequestBody UserDTO objUserDTO){
		objUserFacade.add(objUserDTO);
	}

	/**
	 * 更新
	 * @param objUserDTO objUserDTO
	 */
	@PostMapping("/updateUser")
	public void updateUser(@RequestBody UserDTO objUserDTO){
		objUserFacade.update(objUserDTO);
	}
	
	/**
	 * 删除
	 * @param ids ids
	 */
	@PostMapping("/deleteUser")
	public void deleteUser(@RequestBody List<String> ids){
		objUserFacade.delete(ids);
	}
	
}