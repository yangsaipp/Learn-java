package com.comtop.tcm.tenantPlatform.bpmp.baseservice.appservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtop.bizp.dart.framework.orm.dao.CoreDAO;
import com.comtop.tcm.tenantPlatform.bpmp.baseservice.model.BaseServiceInfoVO;

/**
 * 
 * @author  yangsai 2021-5-21
 * @since   1.0
 */
@Service
public class BaseServiceAppService {

	/**
	 * 注入DAO
	 */
	@Autowired
	private CoreDAO<BaseServiceInfoVO> DAO;
	
	/**
	 * 根据id查询记录
	 * @param id id
	 * @return id对应的vo
	 */
	public BaseServiceInfoVO get(String id) {
		BaseServiceInfoVO objParamVO = new BaseServiceInfoVO();
		objParamVO.setBaseServiceId(id);
		return DAO.load(objParamVO);
	}

	/**
	 * 新增
	 * @param objBaseServiceInfoVO objBaseServiceInfoVO
	 * @return id
	 */
	public String insert(BaseServiceInfoVO objBaseServiceInfoVO) {
		return (String)DAO.insert(objBaseServiceInfoVO);
	}

	/**
	 * 新增
	 * @param objBaseServiceInfoVO objBaseServiceInfoVO
	 * @return true=成功 false=失败
	 */
	public boolean update(BaseServiceInfoVO objBaseServiceInfoVO) {
		return DAO.update(objBaseServiceInfoVO);
		
	}

	/**
	 * 删除
	 * @param ids ids
	 * @return 删除记录数
	 */
	public int delete(List<String> ids) {
		return DAO.delete("com.comtop.tcm.tenantPlatform.bpmp.baseservice.model.deleteBaseServiceInfo", ids);
	}

	/**
	 * 查询集合
	 * @param ids ids
	 * @return 结果集合
	 */
	public List<BaseServiceInfoVO> queryList(List<String> ids) {
		return DAO.queryList("com.comtop.tcm.tenantPlatform.bpmp.baseservice.model.queryBaseServiceList", ids);
	}

}
