/**
 * 流程部署方式：1.addClass方式；2.zip流导入方式；
 */
package com.fpq.acitvity.procdef;

import static org.junit.jupiter.api.Assertions.*;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * @author fpq
 *
 */
class DeployProcdef {

	/**
	 * 通过默认方式获取工作流实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署工作流：addClass方式定义
	 * ACT_GE_BYTEARRAY （资源文件表）通用的流程定义和流程资源：存储流程定义的资源文件
	 * ACT_RE_DEPLOYMENT（部署信息表）:用来存储部署时需要持久化保存下来的信息
	 * ACT_RE_PROCDEF（业务流程定义数据表：解析表）:解析成功了，在该表保存一条记录。
	 * act_ge_property（系统配置表）:dbid的取值会变化，记录指针
	 */
	@Test
	public void deployWithAddClass() {
		Deployment deployment = processEngine.getRepositoryService() //获取部署相关service
		.createDeployment() //创建部署
		.addClasspathResource("diagrams/activiti_applyLeave.bpmn") //加载工作流资源文件
		.addClasspathResource("diagrams/activiti_applyLeave.png") //加载工作流资源文件
		.name("请假审批工作流") //设置工作流程的名称
		.deploy();//部署
		System.out.println("流程部署ID:"+deployment.getId()); 
		System.out.println("流程部署Name:"+deployment.getName());
	}

	
	/**
	 * 部署流程定义使用zip方式
	 * ACT_GE_BYTEARRAY （资源文件表）通用的流程定义和流程资源：存储流程定义的资源文件
	 * ACT_RE_DEPLOYMENT（部署信息表）:用来存储部署时需要持久化保存下来的信息
	 * ACT_RE_PROCDEF（业务流程定义数据表：解析表）:解析成功了，在该表保存一条记录。
	 * act_ge_property（系统配置表）:dbid的取值会变化，记录指针
	 */
	@Test
	public void deployWithZip(){
		InputStream inputStream=this.getClass() // 取得当前class对象
			.getClassLoader() // 获取类加载器
			.getResourceAsStream("diagrams/activiti_applyLeave.zip"); // 获取指定文件资源流
		
		ZipInputStream zipInputStream=new ZipInputStream(inputStream); // 实例化zip输入流
		Deployment deployment=processEngine.getRepositoryService() // 获取部署相关Service
				.createDeployment() // 创建部署
				.addZipInputStream(zipInputStream) // 添加zip输入流
				.name("请假审批工作流") // 流程名称
				.deploy(); // 部署
		System.out.println("流程部署ID:"+deployment.getId()); 
		System.out.println("流程部署Name:"+deployment.getName());
	}
	
	
}
