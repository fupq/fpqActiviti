/**
 * 
 */
package com.fpq.acitvity.assignTask.multiUserAssign;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import com.fpq.acitvity.entity.Student;

/**
 * @author fpq
 *
 */
class AsignTaskMultiUserVariable {

	/**
	 * 通过默认方式获取工作流实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署工作流
	 * ACT_GE_BYTEARRAY （资源文件表）通用的流程定义和流程资源：存储流程定义的资源文件
	 * ACT_RE_DEPLOYMENT（部署信息表）:用来存储部署时需要持久化保存下来的信息
	 * ACT_RE_PROCDEF（业务流程定义数据表：解析表）:解析成功了，在该表保存一条记录。
	 * act_ge_property（系统配置表）:dbid的取值会变化，记录指针
	 */
	@Test
	public void deployStudentLeaveApplyProcess() {
		Deployment deployment = processEngine.getRepositoryService() //获取部署相关service
		.createDeployment() //创建部署
		.addClasspathResource("diagrams/activiti_applyLeave_assignmentTask_multiUserVariable.bpmn") //加载工作流资源文件
		.addClasspathResource("diagrams/activiti_applyLeave_assignmentTask_multiUserVariable.png") //加载工作流资源文件
		.name("学生请假电子流_多用户任务分配_使用流程变量") //设置工作流程的名称
		.deploy();//部署
		System.out.println("流程部署ID:"+deployment.getId()); 
		System.out.println("流程部署Name:"+deployment.getName());
	}

	/**
	 * 启动流程实例:eg:学生开始请假了，需要创建一个流程实例
	 * ACT_RU_EXECUTION（流程实例运行时 执行对象表）：我的代办任务查询表
	 * ACT_RU_IDENTITYLINK（流程实例运行时 身份联系表）：主要存储当前节点参与者的信息,任务参与者数据表。
	 * ACT_RU_TASK(流程实例运行时 用户任务表)：（执行中实时任务）代办任务查询表，运行时任务
	 * ACT_HI_ACTINST（活动节点历史表）：记录流程流转过的所有节点，与HI_TASKINST不同的是，act_hi_taskinst只记录usertask内容。
	 * ACT_HI_IDENTITYLINK （历史身份联系表）：任务参与者数据表。主要存储历史节点参与者的信息。
	 * ACT_HI_PROCINST（历史流程实例表）核心表：历史的流程实例
	 * ACT_HI_TASKINST（历史任务表）核心表：历史的任务实例
	 */
	@Test
	public void start(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("applyId", "carson");
		ProcessInstance pi=processEngine.getRuntimeService() // 运行时Service
			.startProcessInstanceByKey("activitiAssignmentTaskMultiUserVariable",map); // 流程定义表的KEY字段值：myProcess
		System.out.println("流程实例ID:"+pi.getId());
		System.out.println("流程定义ID:"+pi.getProcessDefinitionId()); 
	}
	
	
	/**
	 * 查看申请人的做的任务
	 * 查询如下表，未写任何表
	 * ACT_RU_TASK(流程实例运行时 用户任务表)：（执行中实时任务）代办任务查询表，运行时任务
	 */
	@Test
	public void findTask(){
		List<Task> taskList=processEngine.getTaskService() // 任务相关Service
			.createTaskQuery() // 创建任务查询
			.taskAssignee("carson") // 请假申请人
			.list();
		for(Task task:taskList){
			System.out.println("任务ID:"+task.getId()); 
			System.out.println("任务名称:"+task.getName());
			System.out.println("任务创建时间:"+task.getCreateTime());
			System.out.println("任务委派人:"+task.getAssignee());
			System.out.println("流程实例ID:"+task.getProcessInstanceId());
		}
	}
	
	
	/**
	 * 查看申请人的做的任务
	 * 查询如下表，未写任何表
	 * ACT_RU_TASK(流程实例运行时 用户任务表)：（执行中实时任务）代办任务查询表，运行时任务
	 */
	@Test
	public void findTaskMultiUser(){
		try {
			List<Task> taskList=processEngine.getTaskService() // 任务相关Service
				.createTaskQuery() // 创建任务查询
				.taskCandidateUser("王淑华")//指定候选人
				.list();
			for(Task task:taskList){
				System.out.println("任务ID:"+task.getId()); 
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务创建时间:"+task.getCreateTime());
				System.out.println("任务委派人:"+task.getAssignee());
				System.out.println("流程实例ID:"+task.getProcessInstanceId());
				System.out.println("负责人:"+task.getAssignee());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * 完成流程中一个任务修改的表：
	 * ACT_HI_ACTINST（活动节点历史表）：记录流程流转过的所有节点，与HI_TASKINST不同的是，act_hi_taskinst只记录usertask内容。
	 * ACT_HI_IDENTITYLINK （历史身份联系表）：任务参与者数据表。主要存储历史节点参与者的信息。
	 * ACT_HI_TASKINST（历史任务表）核心表：历史的任务实例
	 * ACT_RU_IDENTITYLINK（流程实例运行时 身份联系表）：主要存储当前节点参与者的信息,任务参与者数据表。
	 * 
	 * 完成整个流程修改的表：
	 ****ACT_RU运行时相关的6张表数据都清空
	 *ACT_RU_EXECUTION（流程实例运行时 执行实例表）：
	 *ACT_RU_IDENTITYLINK（流程实例运行时 身份联系表）：主要存储当前节点参与者的信息,任务参与者数据表。
	 *ACT_RU_TASK(流程实例运行时 用户任务表)：（执行中实时任务）代办任务查询表，运行时任务
	 *act_ru_event_subscr（运行时事件表）：
	 *act_ru_job（运行时定时任务表）：
	 *act_ru_variable（运行时流程变量数据表）：
	 **** ACT_HI历史相关的如下8张表数据会增加或被修改
	 * ACT_HI_ACTINST（活动节点历史表）：记录流程流转过的所有节点，与HI_TASKINST不同的是，act_hi_taskinst只记录usertask内容。
	 * ACT_HI_IDENTITYLINK （历史身份联系表）：任务参与者数据表。主要存储历史节点参与者的信息。
	 * ACT_HI_PROCINST（历史流程实例表）核心表：历史的流程实例
	 * ACT_HI_TASKINST（历史任务表）核心表：历史的任务实例
	 * act_hi_attachment(历史的流程附件):记录历史的流程附件数据
	 * act_hi_comment（历史审批意见表）：
	 * act_hi_detail（历史详情表）：流程中产生的变量详细，包括控制流程流转的变量，业务表单中填写的流程需要用到的变量等
	 * act_hi_varinst（历史变量信息）：历史的流程运行中的变量信息

	 *ACT_HI_ACTINST（历史节点表）：历史活动信息。这里记录流程流转过的所有节点，与HI_TASKINST不同的是，taskinst只记录usertask内容。

	 *ACT_RU_EXECUTION（运行时流程执行实例）：我的代办任务查询表
	 *ACT_RU_IDENTITYLINK（运行时用户关系信息，身份联系）：主要存储当前节点参与者的信息,任务参与者数据表。
	 *ACT_RU_TASK(运行时任务数据表)：（执行中实时任务）代办任务查询表，运行时任务
	 */
	@Test
	public void completeTask(){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			//map.put("userIds", "付品欣,王淑华");
			map.put("approveId", "付品欣");
			processEngine.getTaskService() // 任务相关Service
				.complete("337504",map);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 完成流程时设置变量
	 * 完成流程中一个任务修改的表：
	 * ACT_HI_ACTINST（活动节点历史表）：记录流程流转过的所有节点，与HI_TASKINST不同的是，act_hi_taskinst只记录usertask内容。
	 * ACT_HI_IDENTITYLINK （历史身份联系表）：任务参与者数据表。主要存储历史节点参与者的信息。
	 * ACT_HI_TASKINST（历史任务表）核心表：历史的任务实例
	 * ACT_RU_IDENTITYLINK（流程实例运行时 身份联系表）：主要存储当前节点参与者的信息,任务参与者数据表。
	 * 
	 * 完成整个流程修改的表：
	 ****ACT_RU运行时相关的6张表数据都清空
	 *ACT_RU_EXECUTION（流程实例运行时 执行实例表）：
	 *ACT_RU_IDENTITYLINK（流程实例运行时 身份联系表）：主要存储当前节点参与者的信息,任务参与者数据表。
	 *ACT_RU_TASK(流程实例运行时 用户任务表)：（执行中实时任务）代办任务查询表，运行时任务
	 *act_ru_event_subscr（运行时事件表）：
	 *act_ru_job（运行时定时任务表）：
	 *act_ru_variable（运行时流程变量数据表）：
	 **** ACT_HI历史相关的如下8张表数据会增加或被修改
	 * ACT_HI_ACTINST（活动节点历史表）：记录流程流转过的所有节点，与HI_TASKINST不同的是，act_hi_taskinst只记录usertask内容。
	 * ACT_HI_IDENTITYLINK （历史身份联系表）：任务参与者数据表。主要存储历史节点参与者的信息。
	 * ACT_HI_PROCINST（历史流程实例表）核心表：历史的流程实例
	 * ACT_HI_TASKINST（历史任务表）核心表：历史的任务实例
	 * act_hi_attachment(历史的流程附件):记录历史的流程附件数据
	 * act_hi_comment（历史审批意见表）：
	 * act_hi_detail（历史详情表）：流程中产生的变量详细，包括控制流程流转的变量，业务表单中填写的流程需要用到的变量等
	 * act_hi_varinst（历史变量信息）：历史的流程运行中的变量信息

	 *ACT_HI_ACTINST（历史节点表）：历史活动信息。这里记录流程流转过的所有节点，与HI_TASKINST不同的是，taskinst只记录usertask内容。

	 *ACT_RU_EXECUTION（运行时流程执行实例）：我的代办任务查询表
	 *ACT_RU_IDENTITYLINK（运行时用户关系信息，身份联系）：主要存储当前节点参与者的信息,任务参与者数据表。
	 *ACT_RU_TASK(运行时任务数据表)：（执行中实时任务）代办任务查询表，运行时任务
	 */
	@Test
	public void completeTaskSetVariable(){
		try {
			Student junpu = new Student(1,"付俊浦"); //Student类必须序列化
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("days", 14);
			map.put("startDate", new Date());
			map.put("reason", "生病了，需要住院治疗");
			map.put("student", junpu);
			
			processEngine.getTaskService() // 任务相关Service
				.complete("185004", map); //完成任务时设置变量
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * 历史任务查询
	 */
	@Test
	public void findHistoryTask() {
		List<HistoricTaskInstance> taskListHistory = processEngine.getHistoryService() //获取历史的server
		.createHistoricTaskInstanceQuery() //创建历史任务实例查询
		.processInstanceId("287501") //根据任务id查询
		.finished() //查询已经完成的任务
		.list();
		
		for(HistoricTaskInstance hti:taskListHistory) {
			System.out.println("任务ID:"+hti.getId());
			System.out.println("流程实例ID:"+hti.getProcessInstanceId());
			System.out.println("任务名称："+hti.getName());
			System.out.println("办理人："+hti.getAssignee());
			System.out.println("开始时间："+hti.getStartTime());
			System.out.println("结束时间："+hti.getEndTime());
			System.out.println("=================================");
		}
	}
	
	
	
	/**
	 * 历史活动查询
	 */
	@Test
	public void historyActInstanceList() {
		List<HistoricActivityInstance> historicActivityInstanceList = processEngine.getHistoryService() //获取历史的server
				.createHistoricActivityInstanceQuery() //创建历史活动实例查询
				.processInstanceId("125001") //指定流程实例id
				.finished() //只查询完结的流程实例
				.list();

		for(HistoricActivityInstance hai:historicActivityInstanceList) {
			System.out.println("活动ID:"+hai.getId());
			System.out.println("流程实例ID:"+hai.getProcessInstanceId());
			System.out.println("活动名称："+hai.getActivityName());
			System.out.println("办理人："+hai.getAssignee());
			System.out.println("开始时间："+hai.getStartTime());
			System.out.println("结束时间："+hai.getEndTime());
			System.out.println("=================================");
		}
	}
	

}
