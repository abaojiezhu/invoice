package com.ztessc.einvoice.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ztessc.einvoice.service.UserService;
import com.ztessc.einvoice.util.JwtUtil;
import com.ztessc.einvoice.util.Log;
import com.ztessc.einvoice.util.PageData;

public class ShiroRealm extends AuthorizingRealm{

	private static final Logger log = Log.get();
	@Autowired
	private UserService userService;
	
	 @Override
	    public boolean supports(AuthenticationToken token) {
	        return token instanceof JwtToken;
	    }
	
	/**
	 * 登录信息和用户验证信息验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
		String token = (String) auth.getCredentials();
		// 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getAccountNo(token);
		PageData pd = new PageData();
		pd.put("username", username);
		PageData user = userService.findBy(pd);
		if (user == null || user.isEmpty()) {
			throw new UnknownAccountException("用户不存在");
		}
		return new SimpleAuthenticationInfo(token, token, this.getName());
	}
	
	/**
	 * 角色权限和对应权限添加
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String token = principals.toString();
		
		PageData pd = new PageData();
		pd.put("id", JwtUtil.getUserId(token));
		
		List<String> roles = new ArrayList<String>();
		List<String> permissions = new ArrayList<String>();
		try {
			roles = userService.listUserRoles(pd);
			permissions = userService.listUserPermissions(pd);
		} catch (Exception e) {
			log.error("查询用户权限信息发生异常", e);
		}
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		if (roles != null) {
			authorizationInfo.addRoles(roles);
		}
		if (permissions != null) {
			authorizationInfo.addStringPermissions(permissions);
		}
		return authorizationInfo;
	}

	
}
