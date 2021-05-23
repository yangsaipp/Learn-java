package com.comtop.tcm.tenantPlatform.bpmp.baseservice.facade;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtop.tcm.tenantPlatform.bpmp.baseservice.appservice.BaseServiceAppService;
import com.comtop.tcm.tenantPlatform.bpmp.baseservice.model.BaseServiceInfoDTO;
import com.comtop.tcm.tenantPlatform.bpmp.baseservice.model.BaseServiceInfoVO;

/**
 * 
 * @author  yangsai 2021-5-21
 * @since   1.0
 */
@Service
public class BaseServiceFacade {

	/** */
	@Autowired
	private BaseServiceAppService baseServiceAppService;
	
	/**
	 * 根据id查询记录
	 * @param id id
	 * @return id对应的vo
	 */
	public BaseServiceInfoVO get(String id) {
		return baseServiceAppService.get(id);
	}

	/**
	 * 新增
	 * @param baseServiceInfoDTO baseServiceInfoDTO
	 */
	public void add(BaseServiceInfoDTO baseServiceInfoDTO) {
		BaseServiceInfoVO objBaseServiceInfoVO = new BaseServiceInfoVO();
		BeanUtils.copyProperties(baseServiceInfoDTO, objBaseServiceInfoVO);
		String id = baseServiceAppService.insert(objBaseServiceInfoVO);
		baseServiceInfoDTO.setBaseServiceId(id);
	}

	/**
	 * 更新
	 * @param baseServiceInfoDTO baseServiceInfoDTO
	 */
	public void update(BaseServiceInfoDTO baseServiceInfoDTO) {
		BaseServiceInfoVO objBaseServiceInfoVO = new BaseServiceInfoVO();
		BeanUtils.copyProperties(baseServiceInfoDTO, objBaseServiceInfoVO);
		baseServiceAppService.update(objBaseServiceInfoVO);
	}

	/**
	 * 删除
	 * @param ids 注解id
	 * @return 删除的记录
	 */
	public List<BaseServiceInfoVO> delete(List<String> ids) {
		List<BaseServiceInfoVO> lstBaseServiceInfoVO = baseServiceAppService.queryList(ids);
		baseServiceAppService.delete(ids);
		return lstBaseServiceInfoVO;
	}

}
