package com.ztessc.einvoice.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ztessc.einvoice.service.SysRoleService;
import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;
import com.ztessc.einvoice.util.TreeUtil;

@RequestMapping(value = "/role")
@RestController
public class SysRoleController extends BaseController {
	
	@Autowired
	private SysRoleService sysRoleService;
	
	/**
	 * 获取角色列表数据
	 * @author TQM
	 * @param {"roleName":"角色名称","pageNum":"0","pageSize":"10"}
	 */
    @PostMapping(value = "/list")
    public PageData list() {
    	PageData params = this.getPageData();
    	
    	String pageNum = params.getString("pageNum");
    	String pageSize = params.getString("pageSize");
    	Page<Object> page = PageHelper.startPage(Integer.parseInt(pageNum) , Integer.parseInt(pageSize));
    	List<PageData> list = sysRoleService.listSysRole(params);
		PageData pageResult = this.getPageResult(page, list);
        return MessageUtil.getSuccessMessage(pageResult);
    	
      //  return MessageUtil.getSuccessMessage(list);
    }
    
    
	/**
	 * 获取角色数据
	 * @author Diy
	 * @param {"id":"aa"}
	 */
    @PostMapping(value = "/findSysRoleById")
    public PageData findSysRoleById() {
    	PageData params = this.getPageData();
    	
    	PageData data = sysRoleService.findSysRoleById(params);
    	
        return MessageUtil.getSuccessMessage(data);
    }
    
    /**
     * 角色保存或修改
     * @author Diy
     * @param {"id":"aa","roleCode":"角色编码","roleName":"角色名称","remark":"备注"}
     */
    @PostMapping(value = "/saveOrUpdateRole")
	public PageData save() {
		PageData params = this.getPageData();
		
		String id = params.getString("id");
		
		if(StringUtils.isBlank(id)){  
			
			sysRoleService.save(params);
			
		}else{
			
			sysRoleService.update(params);
			
		}
		
		return MessageUtil.getSuccessMessage(Const.MESSAGE_SUCCESS_SAVE);
	}
	
	
	/**
	 * 角色成员界面信息
	 * @author Diy
	 * @param {"roleId":"aa"}
	 * @return
	 */
    @PostMapping(value = "/getRoleUsers")
	public PageData getRoleUsers(){
		
		PageData params = this.getPageData();
		
		PageData data = sysRoleService.findSysRoleByRoleId(params);
		
		return MessageUtil.getSuccessMessage(data);
	}
	
	
	/**
	 * 获取角色对应下的成员信息
	 * @author Diy
	 * @param {"roleId":"aaa","accountNo":"账号","userName":"用户名称","pageNum":"0","pageSize":"10"}
	 * @return
	 */
    @PostMapping(value = "/ListSysRoleUserInfo")
	public PageData ListSysRoleUserInfo() {
		PageData params = this.getPageData();
		
    	String pageNum = params.getString("pageNum");
    	String pageSize = params.getString("pageSize");
    	Page<Object> page = PageHelper.startPage(Integer.parseInt(pageNum) , Integer.parseInt(pageSize));
    	List<PageData> list = sysRoleService.ListSysRoleUserInfo(params);
		PageData pageResult = this.getPageResult(page, list);
        return MessageUtil.getSuccessMessage(pageResult);
		
	}
	
	
    /**
     * 删除角色用户
     * @author TQM
     * @param {"id":"aaa"}
     * 
     */
    @PostMapping(value = "/deleteRoleUser")
	public PageData deleteRoleUser() {
		
		PageData params = this.getPageData();
		
		sysRoleService.deleteRoleUser(params);
		
		
		return MessageUtil.getSuccessMessage(Const.MESSAGE_SUCCESS_DELETE);
	}
	
	
	
	
	/**
	 * 查询新增角色用户信息
	 * @author Diy
	 * @param {"roleId":"aaa","accountNo":"账号","userName":"用户名称","pageNum":"0","pageSize":"10"}
	 */
    @PostMapping(value = "/listAddRoleUsers")
	public PageData listAddRoleUsers() {
		
		PageData params = this.getPageData();
    	String pageNum = params.getString("pageNum");
    	String pageSize = params.getString("pageSize");
    	Page<Object> page = PageHelper.startPage(Integer.parseInt(pageNum) , Integer.parseInt(pageSize));
    	List<PageData> list = sysRoleService.listAddRoleUsers(params);
		PageData pageResult = this.getPageResult(page, list);
        return MessageUtil.getSuccessMessage(pageResult);
		
	}
    
	
	
    /**
     * 保存新增角色用户
     * @author Diy
     * @param {"roleIds":"aaa,bbb,ccc","userId":"用户ID"}
     */
    @PostMapping(value = "/assignRoles")
	public PageData assignRoles() {
		
		PageData params = this.getPageData();
		
		sysRoleService.assignRoles(params);
		
		return MessageUtil.getSuccessMessage(Const.MESSAGE_SUCCESS_SAVE);
	}
	
	/**
	 * 对应角色查询角色权限
	 * @author TQM
	 * @param {"roleId":"aa"}
	 * @return
	 */
    @PostMapping(value = "/listSysRoleFunc")
	public PageData listSysRoleFunc() {
		PageData params = this.getPageData();
		//app菜单
		List<PageData> listAPPList  = new ArrayList<PageData>();
		//web菜单
		List<PageData> listWEBList  = new ArrayList<PageData>();
		
		List<PageData> allList = sysRoleService.listSysFunc();
		if(allList != null && allList.size() != 0){
			for (PageData data : allList) {
				if("APP_MENU_AUTHORITY".equals(data.getString("funcCode"))) {
					listAPPList.add(data);
					TreeUtil.buildTrees(allList, data.getString("id"), listAPPList);
				}
				if("BACKSTAGE_MANAGE_AUTHORITY".equals(data.getString("funcCode"))) {
					listWEBList.add(data);
					TreeUtil.buildTrees(allList, data.getString("id"), listWEBList);
				}
				if("E_INVOICE_SYSTEM".equals(data.getString("funcCode"))) {
					listAPPList.add(data);
					listWEBList.add(data);
				}
			}
		}
		
		//获取角色下对应的权限
		List<PageData> list = sysRoleService.listSysRoleFunc(params);
		
		List<PageData> childList  = new ArrayList<PageData>();
		
		if(list != null && list.size() != 0){
			for (PageData child : list) {
				boolean bool = true;
				for (PageData data : list) {
					if(child.getString("id").equals(data.getString("parentId"))) {
						bool = false;
						break;
					}
				}
				if(bool) {
					childList.add(child);
				}
			}
		}
		params.put("backStageList", listWEBList);
		params.put("appList", listAPPList);
		params.put("list", childList);
		
		return MessageUtil.getSuccessMessage(params);
	}
	
	/**
	 * 保存角色权限
	 * @author Diy
	 * @param {"roleId":"aa","funcIds":"aaa,bbb,ccc","halfFuncIds":"aaa,bbb,ccc"}
	 * @return
	 */
    @PostMapping(value = "/saveSysRoleFunc")
	public PageData saveSysRoleFunc() {
		
		PageData params = this.getPageData();
		
		sysRoleService.saveSysRoleFunc(params);
		
		return MessageUtil.getSuccessMessage(Const.MESSAGE_SUCCESS_SAVE);
	}

}
