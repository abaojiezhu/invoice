package com.ztessc.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.ztessc.einvoice.dao.DaoSupport;
import com.ztessc.einvoice.exception.BizException;
import com.ztessc.einvoice.util.PageData;

@Service
public class SysRoleService {
	@Autowired
	private DaoSupport daoSupport;
	

	/**
	 * 角色列表信息
	 * @return
	 */
	public List<PageData> listSysRole(PageData pd) {
		
		
		return daoSupport.listForPageData("SysRoleMapper.listSysRole", pd);
	}
	
	/**
	 * 获取角色信息
	 * @param pd
	 * @return
	 */
	public PageData findSysRoleById(PageData pd) {
		return daoSupport.findForPageData("SysRoleMapper.findSysRoleById", pd);
	}
	
	/**
	 * 新增判断角色编码是否重复
	 * @param pd
	 * @return
	 */
	public PageData findSysRoleByCode(PageData pd) {
		return daoSupport.findForPageData("SysRoleMapper.findSysRoleByCode", pd);
	}
	/**
	 * 修改判断角色编码是否重复
	 * @param pd
	 * @return
	 */
	public PageData findSysRoleByIdAndCode(PageData pd) {
		return daoSupport.findForPageData("SysRoleMapper.findSysRoleByIdAndCode", pd);
	}
	
	
	public PageData findSysRoleByRoleId(PageData pd) {
		return daoSupport.findForPageData("SysRoleMapper.findSysRoleByRoleId", pd);
	}
	
	/**
	 * 新增角色
	 * @param pd
	 */
	@Transactional
	public void save(PageData pd) {
		PageData role = this.findSysRoleByCode(pd);
		if(role != null && role.size() > 0) {
			throw new BizException("角色编号已存在");
		}
		
		//获取当前登陆人的ID 和当前时间
		pd.fillCreatedData();
		
		pd.fillUpdatedData();
		
		daoSupport.save("SysRoleMapper.save", pd);
	}
	
	/**
	 * 修改角色
	 * @param pd
	 */
	@Transactional
	public void update(PageData pd) {
		PageData role = this.findSysRoleByIdAndCode(pd);
		if(role != null && role.size() > 0) {
			throw new BizException("角色编号已存在");
		}
		
		//获取当前时间
		pd.fillUpdatedData();
		
		daoSupport.update("SysRoleMapper.update", pd);
	}
	
	/**
	 * 查询角色对应下的成员信息
	 * @param pd
	 * @return
	 */
	public List<PageData> ListSysRoleUserInfo(PageData pd) {
		return daoSupport.listForPageData("SysRoleMapper.ListSysRoleUserInfo", pd);
	}
	
	/**
	 * 删除角色用户
	 * @param pd
	 */
	@Transactional
	public void deleteRoleUser(PageData pd) {
		
		daoSupport.delete("SysRoleMapper.deleteRoleUserById", pd);
	}
	
	/**
	 * 查询新增角色用户信息
	 * @param pd
	 * @return
	 */
	public List<PageData> listAddRoleUsers(PageData pd) {
		return daoSupport.listForPageData("SysRoleMapper.listAddRoleUsers", pd);
	}
	
	
	
	
	/**
	 * 保存新增角色用户
	 * @param pd
	 */
	@Transactional
	public void assignRoles(PageData pd) {
		
		//获取当前登陆人的ID 和当前时间
		pd.fillCreatedData();
		
		pd.fillUpdatedData();
		
		
		String roleIds = pd.getString("roleIds");
		String userId = pd.getString("userId");
		
		if(StringUtils.isEmpty(roleIds)|| StringUtils.isEmpty(userId)) {
			throw new BizException("角色信息和用户信息不能为空");
		}
		
		String[] roles = roleIds.split(",");
		for (String roleId : roles) {
			pd.put("roleId", roleId);
			pd.put("userId", userId);
			daoSupport.save("SysRoleMapper.assignRoles", pd);
		}
		
	}
	
	/**
	 * 获取所有菜单
	 * @param pd
	 * @return
	 */
	public List<PageData> listSysFunc() {
		
		return daoSupport.listForPageData("SysRoleMapper.listSysFunc", null);
		
	}
	
	/**
	 * 对应角色查询角色权限
	 * @param pd
	 * @return
	 */
	public List<PageData> listSysRoleFunc(PageData pd) {
		
		return daoSupport.listForPageData("SysRoleMapper.listSysRoleFunc", pd);
		
	}
	
	/**
	 * 保存角色对应的权限
	 * @param pd
	 */
	@Transactional
	public void saveSysRoleFunc(PageData pd) {
		String funcIds = pd.getString("funcIds");
		String roleId = pd.getString("roleId");
		String halfFuncIds = pd.getString("halfFuncIds");
		
//		if(StringUtils.isEmpty(funcIds)|| StringUtils.isEmpty(roleId)) {
//			throw new BizException("角色信息和权限信息不能为空");
//		}
		
		
		//获取当前登陆人的ID 和当前时间
		
		pd.fillUpdatedData();
		
		pd.put("roleId", roleId);
		
		//先移除角色对应的权限
		daoSupport.delete("SysRoleMapper.deleteSysRoleFunc", pd);
		
		String[] funcs = funcIds.split(",");
		for (String funcId : funcs) {
			pd.fillCreatedData();
			pd.put("funcId", funcId);
			
			daoSupport.save("SysRoleMapper.saveSysRoleFunc", pd);
		}
		
		String[] halfs = halfFuncIds.split(",");
		for (String halfFuncId : halfs) {
			pd.fillCreatedData();
			pd.put("funcId", halfFuncId);
			
			daoSupport.save("SysRoleMapper.saveSysRoleFunc", pd);
		}
		
		
		
	}
	
	public PageData findSysFuncByBackGroundId(PageData pd) {
		return daoSupport.findForPageData("SysRoleMapper.findSysFuncById", pd);
	}
	
	public PageData findSysFuncByAppId(PageData pd) {
		return daoSupport.findForPageData("SysRoleMapper.findSysFuncById", pd);
	}
	
	public PageData findSysFuncByFuncCode(PageData pd) {
		return daoSupport.findForPageData("SysRoleMapper.findSysFuncByFuncCode", pd);
	}
	
	
	
}
