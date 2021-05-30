package com.miaoying.generator.modulardb.personal.appservice;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtop.bizp.dart.framework.orm.dao.CoreDAO;
import com.miaoying.generator.modulardb.personal.model.UserVO;

/**
 * @auther CodeGenerator
 * @create 2021-05-30 23:59:04
 * @describe 人员和组织的关联对象表appService
 */
@Service
public class UserAppService {

	/**
	 * 注入DAO
	 */
	@Autowired
	private CoreDAO<UserVO> DAO;
	
	/**
	 * 根据id查询记录
	 * @param id id
	 * @return id对应的vo
	 */
	public UserVO get(String id) {
		UserVO objParamVO = new UserVO();
		objParamVO.setUserId(id);
		return DAO.load(objParamVO);
	}

	/**
	 * 新增
	 * @param objUserVO objUserVO
	 * @return id
	 */
	public String insert(UserVO objUserVO) {
		return (String)DAO.insert(objUserVO);
	}

	/**
	 * 新增
	 * @param objUserVO objUserVO
	 * @return true=成功 false=失败
	 */
	public boolean update(UserVO objUserVO) {
		return DAO.update(objUserVO);
		
	}

	/**
	 * 删除
	 * @param ids ids
	 * @return 删除记录数
	 */
	public int delete(List<String> ids) {
		return DAO.delete("com.miaoying.generator.modulardb.personal.model.deleteUserVO", ids);
	}

	/**
	 * 查询集合
	 * @param ids ids
	 * @return 结果集合
	 */
	public List<UserVO> queryList(List<String> ids) {
		return DAO.queryList("com.miaoying.generator.modulardb.personal.model.queryUserVOList", ids);
	}

}