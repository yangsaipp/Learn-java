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
import com.comtop.tcm.tenantPlatform.bpmp.common.ResponseMsgBuilder;
import com.comtop.tcm.tenantPlatform.bpmp.common.ResponseMsgDTO;

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
	 * @param id tenantInfo
	 * @return AuthResultDTO objAuthResultDTO
	 */
	@GetMapping("/getBaseService")
	public ResponseMsgDTO getBaseService(@RequestParam String id){
		try {
			BaseServiceInfoVO objBaseServiceInfoVO = baseServiceFacade.get(id);
			return ResponseMsgBuilder.success(objBaseServiceInfoVO);
		} catch (Exception e) {
			return ResponseMsgBuilder.fail(e);
		}
	}
	
	/**
	 * 新增
	 * @param baseServiceInfoDTO baseServiceInfoDTO
	 * @return AuthResultDTO objAuthResultDTO
	 */
	@PostMapping("/addBaseService")
	public ResponseMsgDTO addBaseService(@RequestBody BaseServiceInfoDTO baseServiceInfoDTO){
		try {
			baseServiceFacade.add(baseServiceInfoDTO);
			return ResponseMsgBuilder.success(baseServiceInfoDTO);
		} catch (Exception e) {
			return ResponseMsgBuilder.fail(e);
		}
	}

	/**
	 * 更新
	 * @param baseServiceInfoDTO baseServiceInfoDTO
	 * @return AuthResultDTO objAuthResultDTO
	 */
	@PostMapping("/updateBaseService")
	public ResponseMsgDTO updateBaseService(@RequestBody BaseServiceInfoDTO baseServiceInfoDTO){
		try {
			baseServiceFacade.update(baseServiceInfoDTO);
			return ResponseMsgBuilder.success(baseServiceInfoDTO);
		} catch (Exception e) {
			return ResponseMsgBuilder.fail(e);
		}
	}
	
	/**
	 * 删除
	 * @param ids ids
	 * @return ResponseMsgDTO
	 */
	@PostMapping("/deleteBaseService")
	public ResponseMsgDTO deleteTenantWithService(@RequestBody List<String> ids){
		try {
			List<BaseServiceInfoVO> lstBaseServiceInfoVO = baseServiceFacade.delete(ids);
			return ResponseMsgBuilder.success(lstBaseServiceInfoVO);
		} catch (Exception e) {
			return ResponseMsgBuilder.fail(e);
		}
	}
	
}
