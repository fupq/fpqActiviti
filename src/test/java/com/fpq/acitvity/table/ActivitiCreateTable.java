package com.fpq.acitvity.table;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.jupiter.api.Test;

class ActivitiCreateTable {

	@Test
	public void testCreateTable25() {
		//配置流程引擎
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver"); // 配置驱动
		processEngineConfiguration.setJdbcUrl("jdbc:mysql://192.168.159.131:3306/fpqActiviti?useUnicode=true&characterEncoding=utf-8&useSSL=false"); // 配置连接地址
		processEngineConfiguration.setJdbcUsername("root"); // 用户名
		processEngineConfiguration.setJdbcPassword("Carson@20131111"); // 密码
		
		/**
		 * 配置模式  true 自动创建和更新表
		 */
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		//创建流程引擎ProcessEngine
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		//运行可生产25张工作流引擎的内置表
	}

}
