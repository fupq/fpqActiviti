/**
 * 
 */
package com.fpq.acitvity.flow;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.activiti.engine.task.Task;

/**
 * @author fpq
 *
 */
class HelloWorldProcess {

	/**
	 * 通过默认方式获取工作流实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署工作流
	 * ACT_GE_BYTEARRAY（通用的流程定义和流程资源）:增加2条（工作流资源文件存储的）记录
	 * ACT_RE_DEPLOYMENT（部署信息表）:用来存储部署时需要持久化保存下来的信息
	 * ACT_RE_PROCDEF（业务流程定义数据表：解析表）:解析成功了，在该表保存一条记录。
	 */
	@Test
	public void deploy() {
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
	 * 启动流程实例:eg:张三开始请假了，需要创建一个流程实例
	 * ACT_HI_ACTINST（历史节点表）：历史活动信息。这里记录流程流转过的所有节点，与HI_TASKINST不同的是，taskinst只记录usertask内容。
	 * ACT_HI_IDENTITYLINK （历史流程人员表）：任务参与者数据表。主要存储历史节点参与者的信息。
	 * ACT_HI_PROCINST（历史流程实例信息）核心表：历史的流程实例
	 * ACT_HI_TASKINST（历史任务流程实例信息）核心表：历史的任务实例
	 * ACT_RU_EXECUTION（运行时流程执行实例）：我的代办任务查询表
	 * ACT_RU_IDENTITYLINK（运行时用户关系信息，身份联系）：主要存储当前节点参与者的信息,任务参与者数据表
	 * ACT_RU_TASK(运行时任务数据表)：（执行中实时任务）代办任务查询表，运行时任务
	 */
	@Test
	public void start(){
		ProcessInstance pi=processEngine.getRuntimeService() // 运行时Service
			.startProcessInstanceByKey("myProcess"); // 流程定义表的KEY字段值：myProcess
		System.out.println("流程实例ID:"+pi.getId());
		System.out.println("流程定义ID:"+pi.getProcessDefinitionId()); 
	}
	
	/**
	 * 查看申请人的做的任务
	 * 查询如下表，未写任何表
	 * ACT_RU_TASK(运行时任务数据表)：（执行中实时任务）代办任务查询表，运行时任务
	 */
	@Test
	public void findTaskEmployee(){
		List<Task> taskList=processEngine.getTaskService() // 任务相关Service
			.createTaskQuery() // 创建任务查询
			.taskAssignee("employee") // 请假申请人
			.list();
		for(Task task:taskList){
			System.out.println("任务ID:"+task.getId()); 
			System.out.println("任务名称:"+task.getName());
			System.out.println("任务创建时间:"+task.getCreateTime());
			System.out.println("任务委派人:"+task.getAssignee());
			System.out.println("流程实例ID:"+task.getProcessInstanceId());
		}
	}
	/*
	任务ID:7504
	任务名称:请假
	任务创建时间:Fri Aug 10 01:26:38 CST 2018
	任务委派人:employee
	流程实例ID:7501
	
	任务ID:7504
	任务名称:请假
	任务创建时间:Fri Aug 10 01:26:38 CST 2018
	任务委派人:employee
	流程实例ID:7501
	 */
	
	/**
	 * 查看审批人的代办任务
	 * 查询如下表，未写任何表
	 * ACT_RU_TASK(运行时任务数据表)：（执行中实时任务）代办任务查询表，运行时任务
	 */
	@Test
	public void findTaskEmployer(){
		List<Task> taskList=processEngine.getTaskService() // 任务相关Service
			.createTaskQuery() // 创建任务查询
			.taskAssignee("employer") // 请假审批人
			.list();
		for(Task task:taskList){
			System.out.println("任务ID:"+task.getId()); 
			System.out.println("任务名称:"+task.getName());
			System.out.println("任务创建时间:"+task.getCreateTime());
			System.out.println("任务委派人:"+task.getAssignee());
			System.out.println("流程实例ID:"+task.getProcessInstanceId());
		}
	}
	/*运行结果：
	 任务ID:10002
	任务名称:审批
	任务创建时间:Fri Aug 10 02:00:47 CST 2018
	任务委派人:employer
	流程实例ID:5005
	
	任务ID:15002
	任务名称:审批
	任务创建时间:Fri Aug 10 02:17:29 CST 2018
	任务委派人:employer
	流程实例ID:7501
	 */
	
	
	
	/**
	 * 完成流程中一个任务修改的表：
	 * ACT_HI_ACTINST（历史节点表）:历史活动信息,历史的流程实例。这里记录流程流转过的所有节点，与HI_TASKINST不同的是，taskinst只记录usertask内容。
	 * ACT_HI_IDENTITYLINK （历史流程人员表）：任务参与者数据表。主要存储历史节点参与者的信息。
	 * ACT_HI_TASKINST（历史任务流程实例信息）核心表：历史的任务实例
	 * ACT_RU_IDENTITYLINK（运行时用户关系信息，身份联系）：主要存储当前节点参与者的信息,任务参与者数据表。
	 * 
	 * 完成整个流程修改的表：
	 *ACT_HI_ACTINST（历史节点表）：历史活动信息。这里记录流程流转过的所有节点，与HI_TASKINST不同的是，taskinst只记录usertask内容。
	 *ACT_RU_EXECUTION（运行时流程执行实例）：我的代办任务查询表
	 *ACT_RU_IDENTITYLINK（运行时用户关系信息，身份联系）：主要存储当前节点参与者的信息,任务参与者数据表。
	 *ACT_RU_TASK(运行时任务数据表)：（执行中实时任务）代办任务查询表，运行时任务
	 */
	@Test
	public void completeTask(){
		processEngine.getTaskService() // 任务相关Service
			.complete("15002");
	}
}
