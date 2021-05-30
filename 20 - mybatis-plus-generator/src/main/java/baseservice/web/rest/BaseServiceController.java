/******************************************************************************
* Copyright (C) 2018 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package com.comtop.tcm.tenantPlatform.bpmp.baseservice.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comtop.tcm.tenantPlatform.bpmp.baseservice.facade.BaseServiceFacade;
import com.comtop.tcm.tenantPlatform.bpmp.baseservice.model.BaseServiceInfoDTO;
import com.comtop.tcm.tenantPlatform.bpmp.baseservice.model.BaseServiceInfoVO;

/**
 * BaseService相关接口
 * @author  yangsai 2021-5-21
 * @since   1.0
 */
@RestController
@RequestMapping("/bpmpTcm/baseService")
public class BaseServiceController {
	
	/** 注入Facade  */
	@Autowired
	private BaseServiceFacade baseServiceFacade;

	/** */
//	@Autowired
//	private TcmEventPublisher tcmEventPublisher;
	
	
	/**
	 * 根据id查询信息
	 * @param id id
	 * @return objBaseServiceInfoVO
	 */
	@GetMapping("/getBaseService")
	public BaseServiceInfoVO getBaseService(@RequestParam String id){
		return baseServiceFacade.get(id);
	}
	
	/**
	 * 新增
	 * @param baseServiceInfoDTO baseServiceInfoDTO
	 */
	@PostMapping("/addBaseService")
	public void addBaseService(@RequestBody BaseServiceInfoDTO baseServiceInfoDTO){
		baseServiceFacade.add(baseServiceInfoDTO);
	}

	/**
	 * 更新
	 * @param baseServiceInfoDTO baseServiceInfoDTO
	 */
	@PostMapping("/updateBaseService")
	public void updateBaseService(@RequestBody BaseServiceInfoDTO baseServiceInfoDTO){
		baseServiceFacade.update(baseServiceInfoDTO);
	}
	
	/**
	 * 删除
	 * @param ids ids
	 * @return ResponseMsgDTO
	 */
	@PostMapping("/deleteBaseService")
	public void deleteTenantWithService(@RequestBody List<String> ids){
		baseServiceFacade.delete(ids);
	}
	
}
