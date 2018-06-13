package com.ztessc.einvoice.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ztessc.einvoice.filter.JwtFilter;

@Configuration
public class ShiroConfiguration {
	
	//将自己的验证方式加入容器
    @Bean
    public ShiroRealm shiroRealm() {
    	ShiroRealm shiroRealm = new ShiroRealm();
    	shiroRealm.setCachingEnabled(false);
        return shiroRealm;
    }
    
    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
    	DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    	
        securityManager.setRealm(shiroRealm());
        
        //设置不创建session
        securityManager.setSubjectFactory(subjectFactory());

        //设置sessionManager禁用掉会话调度器
        securityManager.setSessionManager(sessionManager());
        
        //无状态需要设置不创建session，禁用使用Sessions 作为存储策略的实现，但它没有完全地禁用Sessions，所以需要配合context.setSessionCreationEnabled(false);
        ((DefaultSessionStorageEvaluator)((DefaultSubjectDAO)securityManager.getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false);

        
        return securityManager;
    }
    
	//Filter工厂，设置对应的过滤条件和跳转条件
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		filters.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filters);
		
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/api/**", "anon");
		
		//注意过滤器配置顺序 不能颠倒
		filterChainDefinitionMap.put("/**", "jwt");
        
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

	}
	
	/**
     * subject工厂管理器.
     * 用于禁用session
     * @return
     */
    @Bean
    public DefaultWebSubjectFactory subjectFactory(){
        StatelessDefaultSubjectFactory subjectFactory = new StatelessDefaultSubjectFactory();
        return subjectFactory;
    }

    /**
     * session管理器
     * sessionManager通过sessionValidationSchedulerEnabled禁用掉会话调度器，
     * 因为我们禁用掉了会话，所以没必要再定期过期会话了。
     * @return
     */
    @Bean
    public DefaultSessionManager sessionManager(){
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }
    
	//加入注解的使用，不加入这个注解不生效 https://www.cnblogs.com/tuifeideyouran/p/7696055.html
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
	
	//凭证匹配器（由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");	//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1);			//散列的次数，比如散列2次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }
    
}
