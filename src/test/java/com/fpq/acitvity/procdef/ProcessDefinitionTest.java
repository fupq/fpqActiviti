/**
 * 
 */
package com.fpq.acitvity.procdef;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;

/**
 * @author fpq
 *
 */
class ProcessDefinitionTest {

	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 查询流程定义 返回流程定义集合  对应表 ACT_RE_PROCDEF（业务流程定义数据表）
	 */
	@Test
	public void list(){
		String processDefinitionKey="myProcess";
		List<ProcessDefinition> pdList=processEngine.getRepositoryService() // 获取部署相关的service
			.createProcessDefinitionQuery() // 创建流程定义查询
			.processDefinitionKey(processDefinitionKey) // 设置流程key
			.list();  // 返回一个集合
		for(ProcessDefinition pd:pdList){
			System.out.println("ID_ ： "+pd.getId());
			System.out.println("NAME_ ： "+pd.getName());
			System.out.println("KEY_ ： "+pd.getKey());
			System.out.println("VERSION_ ： "+pd.getVersion());
			System.out.println("=========");
		}
	}
	
	/**
	 * 通过ID查询某个流程定义
	 */
	@Test
	public void getById(){
		String processDefinitionId="myProcess:3:5004";
		ProcessDefinition pd=processEngine.getRepositoryService() // 获取service
			.createProcessDefinitionQuery() // 创建流程定义查询
			.processDefinitionId(processDefinitionId) // 通过id查询
			.singleResult();
		
		System.out.println("ID_ ： "+pd.getId());
		System.out.println("NAME_ ： "+pd.getName());
		System.out.println("KEY_ ： "+pd.getKey());
		System.out.println("VERSION_ ： "+pd.getVersion());
			
	}

}
