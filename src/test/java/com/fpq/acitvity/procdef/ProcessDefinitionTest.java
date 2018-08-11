/**
 * 
 */
package com.fpq.acitvity.procdef;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
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

	/**
	 * 根据部署ID和资源名称获取资源（图片）
	 */
	@Test
	public void getPictureByDeployIdAndName() {
		InputStream  inputStream  = processEngine.getRepositoryService() //获取部署相关的sever
		.getResourceAsStream("20001", "diagrams/activiti_applyLeave.png"); //获取资源流
		try {
			FileUtils.copyInputStreamToFile(inputStream, new File("D:\\activiti\\eclipseWorkSpase\\程序用的文件夹\\20180811.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询最新版本的流程实例
	 * 1.根据流程定义表act_re_procdef表的版本号升序排序
	 * 2.定义有序map,按顺序put相同key值的流程，高版本覆盖低版本
	 * 3.将有序map放入list中，该list中存放的就是各个流程的最新版本
	 */
	@Test
	public void getlastVersion() {
		List<ProcessDefinition> listpdAll = processEngine.getRepositoryService() //获取部署相关的server
		.createProcessDefinitionQuery() //创建流程定义查询
		.orderByProcessDefinitionVersion().asc() //根据流程定义版本升序
		.list();
		
		Map<String,ProcessDefinition> map = new LinkedHashMap<String,ProcessDefinition>();
		
		for(ProcessDefinition pd:listpdAll) {
			map.put(pd.getKey(), pd);
		}
		List<ProcessDefinition> listLastpd = new LinkedList<ProcessDefinition>(map.values());
		for(ProcessDefinition pd:listLastpd) {
			System.out.println("ID_ ： "+pd.getId());
			System.out.println("NAME_ ： "+pd.getName());
			System.out.println("KEY_ ： "+pd.getKey());
			System.out.println("VERSION_ ： "+pd.getVersion());
			System.out.println("=========");
		}
	}
	
	
	/**
	 * 删除key相同的所有流程
	 */
	@Test
	public void deleteProcessByKey() {
		List<Deployment> listpd = processEngine.getRepositoryService() //获取流程部署的server
		.createDeploymentQuery() //创建查询
		.processDefinitionKey("feeExpensesReimbursement1") //根据key查询流程部署
		.list();
		
		for(Deployment dpm:listpd) {
			String dpmInfo = "ID_ ： "+dpm.getId()+",NAME_ ： "+dpm.getName()+",部署时间： "+dpm.getDeploymentTime();
			//processEngine.getRepositoryService().deleteDeployment(dpm.getId()); //只删除部署表的实例
			processEngine.getRepositoryService().deleteDeployment(dpm.getId(),true);
			System.out.println(dpmInfo+"已经被删除！");
		}
	}
	
}
