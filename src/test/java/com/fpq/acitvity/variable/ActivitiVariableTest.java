/**
 * 工作流变量variable学习--使用TaskService设置变量
 * 注：使用任务ID taskID设置变量
 * act_ru_variable（运行时流程变量数据表）：
 * act_hi_varinst（历史变量信息）：历史的流程运行中的变量信息
 */
package com.fpq.acitvity.variable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import com.fpq.acitvity.entity.Student;
/**
 * 流程变量测试
 * @author fpq
 *
 */
class ActivitiVariableTest {

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
		.addClasspathResource("diagrams/StudentApplyLeave.bpmn") //加载工作流资源文件
		.addClasspathResource("diagrams/StudentApplyLeave.png") //加载工作流资源文件
		.name("学生请假电子流") //设置工作流程的名称
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
		ProcessInstance pi=processEngine.getRuntimeService() // 运行时Service
			.startProcessInstanceByKey("studentApplyLeave_process"); // 流程定义表的KEY字段值：myProcess
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
			.taskAssignee("student") // 请假申请人
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
		processEngine.getTaskService() // 任务相关Service
			.complete("97502");
	}

	/**
	 * 设置流程变量数据
	 */
	@Test
	public void setVariable() {
		String taskId = "62504";
		Student carson = new Student(1,"付柯翔"); //Student类必须序列化
		TaskService taskService = processEngine.getTaskService();//获取任务server
		taskService.setVariable(taskId, "days", 2);
		taskService.setVariable(taskId, "startDate", new Date());
		taskService.setVariable(taskId, "reason", "生病了需要住院看病");
		taskService.setVariable(taskId, "student", carson);
		System.out.println("流程变量设置完毕！");
	}
	
	/**
	 * 获取流程变量数据
	 */
	@Test
	public void getVariable() {
		String taskId = "87502";
		Student student = new Student();
		try {
			TaskService taskService = processEngine.getTaskService();//获取任务server
			Integer days = (Integer)taskService.getVariable(taskId, "days");
			Date startDate = (Date)taskService.getVariable(taskId, "startDate");
			String reason = (String)taskService.getVariable(taskId, "reason");
			student = (Student)taskService.getVariable(taskId, "student");
			
			if(null != student ) {
				System.out.println("获取到的变量如下：");
				System.out.println("请假天数："+days);
				System.out.println("开始日期："+startDate);
				System.out.println("请假原因："+reason);
				System.out.println("请假人："+student.toString());
			}else {
				System.out.println("没查到数据，请确认您输入的任务号："+taskId+"是当前正在执行的任务吗？");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * 设置流程变量数据-批量
	 */
	@Test
	public void setVariables() {
		String taskId = "92504";
		Student carson = new Student(1,"付柯翔"); //Student类必须序列化
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("days", 2);
		map.put("startDate", new Date());
		map.put("reason", "生病了需要住院看病");
		map.put("student", carson);
		TaskService taskService = processEngine.getTaskService();//获取任务server
		taskService.setVariables(taskId, map);
		System.out.println("流程变量设置完毕！");
	}
	
	
	
	/**
	 * 获取流程变量数据-批量
	 */
	@Test
	public void getVariables() {
		String taskId = "102502";
		TaskService taskService = processEngine.getTaskService();//获取任务server
		Map<String, Object> map= taskService.getVariables(taskId);
		if(null != map) {
			System.out.println("获取到的变量如下：");
			System.out.println("请假天数："+map.get("days"));
			System.out.println("开始日期："+map.get("startDate"));
			System.out.println("请假原因："+map.get("reason"));
			System.out.println("请假人："+((Student)map.get("student")).toString());
		}else {
			System.out.println("没查到数据，请核对输入的任务号："+taskId+"是否是当前任务id");
		}
	}
	
	
	
	/**
	 * 设置局部流程变量数据-批量
	 */
	@Test
	public void setVariablesLocal() {
		try{
			String taskId = "97502";
			Student carson = new Student(1,"付琪"); //Student类必须序列化
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("days2", 3);
			map.put("startDate2", new Date());
			map.put("reason2", "牙痛，需要看牙医治疗");
			map.put("student2", carson);
			
			TaskService taskService = processEngine.getTaskService();//获取任务server
			taskService.setVariablesLocal(taskId, map);
			System.out.println("流程变量设置完毕！");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	/**
	 * 获取本地流程变量数据-批量
	 * 本地变量：在某个节点设置后只能在当前节点查询，其他节点查询不到
	 */
	@Test
	public void getVariablesLocal() {
		try{
			String taskId = "102502";
			TaskService taskService = processEngine.getTaskService();//获取任务server
			Map<String, Object> map= taskService.getVariablesLocal(taskId);
			if(null != map) {
				System.out.println("获取到的变量如下：");
				System.out.println("请假天数："+map.get("days2"));
				System.out.println("开始日期："+map.get("startDate2"));
				System.out.println("请假原因："+map.get("reason2"));
				System.out.println("请假人："+((Student)map.get("student2")).toString());
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
