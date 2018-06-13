package com.ztessc.einvoice;

import org.apache.shiro.SecurityUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
//@Transactional
//@Rollback(false)
public class BaseJunit4Test {
	
	@Autowired
    private org.apache.shiro.mgt.SecurityManager securityManager;

    @Before
    public void setUp() throws Exception {
    	SecurityUtils.setSecurityManager(securityManager);
    }
    
}
