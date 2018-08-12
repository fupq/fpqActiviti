/**
 * 
 */
package com.fpq.acitvity.group;

import static org.junit.jupiter.api.Assertions.*;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

/**
 * @author fpq
 *
 */
class IdentityTest {

	/**
	 * 通过默认方式获取工作流实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 添加用户
	 */
	@Test
	public void testSaveUser() {
		IdentityService identityService = processEngine.getIdentityService(); //定义身份相关的service
		User user = new UserEntity(); //实例化用户实体
		user.setId("付枫傲");
		user.setPassword("fupinqin");
		user.setEmail("18025853669@189.cn");
		identityService.saveUser(user);
	}
	
	/**
	 * 删除用户
	 */
	@Test
	public void testDeleteUser() {
		IdentityService identityService = processEngine.getIdentityService(); //定义身份相关的service
		identityService.deleteUser("fpq");	
	}

	/**
	 * 添加组
	 */
	@Test
	public void testSaveGroup() {
		IdentityService identityService = processEngine.getIdentityService(); //定义身份相关的service
        Group group = new GroupEntity();
        group.setId("delete");
        group.setName("test组");
        group.setType("fpq");
		identityService.saveGroup(group);
	}
	
	/**
	 * 删除组
	 */
	@Test
	public void testDeleteGroup() {
		IdentityService identityService = processEngine.getIdentityService(); //定义身份相关的service
		identityService.deleteGroup("delete");
	}
	
	/**
	 * 添加用户和组的关联关系
	 */
	@Test
	public void testCreateMembership() {
		try {
			IdentityService identityService = processEngine.getIdentityService(); //定义身份相关的service
			identityService.createMembership("付柯翔", "aprove");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * 测试删除用户和组的关联关系
	 */
	@Test
	public void testDeleteMembership() {
		try {
			IdentityService identityService = processEngine.getIdentityService(); //定义身份相关的service
			identityService.deleteMembership("付柯翔", "aprove");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
