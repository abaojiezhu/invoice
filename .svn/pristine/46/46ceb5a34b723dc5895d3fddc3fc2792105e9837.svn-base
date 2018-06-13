package com.ztessc.einvoice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztessc.einvoice.dao.DaoSupport;
import com.ztessc.einvoice.util.DateUtil;
import com.ztessc.einvoice.util.MD5;
import com.ztessc.einvoice.util.PageData;
import com.ztessc.einvoice.util.ShiroUtil;
import com.ztessc.einvoice.util.TreeUtil;

@Service
public class UserService {

	@Autowired
	private DaoSupport daoSupport;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	/**
	 * 根据帐号获取用户
	 * @author: 徐益森
	 * @date: 2018年4月9日 下午7:06:33
	 * @param {"username":"1001"}
	 * @return PageData
	 */
	public PageData findBy(PageData pd) {
		return (PageData) daoSupport.findForPageData("UserMapper.findBy", pd);
	}
	
	/**
	 * 获取用户所有角色
	 * @author: 徐益森
	 * @date: 2018年4月9日 下午6:56:25
	 * @param {"id":"asdkjhdsf"}
	 * @return List<PageData>
	 */
	public List<String> listUserRoles(PageData pd) {
		return (List<String>) daoSupport.listForString("UserMapper.listUserRoles", pd);
	}
	
	/**
	 * 获取用户所以权限
	 * @author: 徐益森
	 * @date: 2018年4月9日 下午7:03:58
	 * @param {"id":"sdfkhsdkjfh"}
	 * @return List<PageData>
	 */
	public List<String> listUserPermissions(PageData pd) {
		return (List<String>) daoSupport.listForString("UserMapper.listUserPermissions", pd);
	}
	
	/**
	 * 根据关键字搜索用户(根据工号、姓名模糊查找前5条)
	 * @author: 徐益森
	 * @date: 2018年4月26日 下午3:37:55
	 * @param {"keyword":"张三"}
	 * @return List<PageData>
	 */
	public List<PageData> listUserByKeyword(PageData pd) {
		return daoSupport.listForPageData("UserMapper.listUserByKeyword", pd);
	}
	
	public List<PageData> listBaseUser(PageData pd) {
		String registerStartDt = pd.getString("registerStartDt");
		String registerEndDt = pd.getString("registerEndDt");
		if(StringUtils.isNotBlank(registerStartDt) && StringUtils.isNotBlank(registerEndDt)){
			Date data = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				 data = sdf.parse(registerEndDt);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.DAY_OF_MONTH, 1);
			
			Date time = c.getTime();
			pd.put("registerEndDt",time);
		}
		return daoSupport.listForPageData("UserMapper.listBaseUser", pd);
	}
	
	
	
	@Transactional
	public void save(PageData pd) {
		
		String pwd = pd.getString("password");
		
		if(StringUtils.isNotBlank(pwd)){
			String password = MD5.md5(pwd);
			pd.put("password", password);
		}
		
		//获取当前时间
		pd.fillCreatedData();
		
		pd.fillUpdatedData();
		
		pd.put("registerDt", DateUtil.getCurrentDateTime());
		
		daoSupport.save("UserMapper.save", pd);
	}

	@Transactional
	public void update(PageData pd) {
		
		String enabled = pd.getString("enabled");
		String pwd = pd.getString("password");
		
		if(StringUtils.isNotBlank(enabled)){
			if("Z001002".equals(enabled)){
				pd.put("disableDt", DateUtil.getCurrentDateTime());
			}
		}
		
		if(StringUtils.isNotBlank(pwd)){
			String password = MD5.md5(pwd);
			pd.put("password", password);
		}
		
		//获取当前时间
		pd.fillUpdatedData();
		pd.transEmptyToNull();
		daoSupport.update("UserMapper.update", pd);
	}

	/**
	 * 根据用户id获取用户菜单权限
	 * @author: 徐益森
	 * @date: 2018年4月26日 下午4:54:12
	 * @param {"id":"aaa"}
	 * @return void
	 */
	public List<PageData> listFuncsByUser(PageData pd) {
		return daoSupport.listForPageData("UserMapper.listFuncsByUser", pd);
	}
	
	/**
	 * 根据用户id获取菜单权限列表
	 * @author: TQM
	 * @date: 2018年4月26日19:40:12
	 * @return List
	 */
	public List<PageData> listUserFuncByUser() {
		
        PageData currentUserData = ShiroUtil.getCurrentUserData();
        
        List<PageData> authList = this.listFuncsByUser(currentUserData);
        
        List<PageData> allList = sysRoleService.listSysFunc();
        //web菜单
		List<PageData> menuList  = new ArrayList<PageData>();
        if(allList != null && allList.size() != 0){
			for (PageData data : allList) {
				if("BACKSTAGE_MANAGE_AUTHORITY".equals(data.getString("funcCode"))) {
					menuList.add(data);
					TreeUtil.buildTrees(allList, data.getString("id"), menuList);
				}
				if("E_INVOICE_SYSTEM".equals(data.getString("funcCode"))) {
					menuList.add(data);
				}
			}
		}
        
        List<PageData> list = new ArrayList<PageData>();
        if(menuList != null && menuList.size() != 0){
			for (PageData data : menuList) {
				for (PageData auth : authList) {
    				if(data.getString("id").equals(auth.getString("id"))) {
    					PageData node = new PageData();
    					node.put("path", "/"+data.getString("funcCode"));
    					node.put("name", data.getString("funcName"));
    					list.add(node);
    				}
    			}
			}
        }
		return list;
	}

	public PageData findByToken(String token) {
		return (PageData) daoSupport.findForPageData("UserMapper.findByToken", token);
	}
	
	
}
