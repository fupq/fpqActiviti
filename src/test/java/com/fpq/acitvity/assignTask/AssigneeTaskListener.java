/**
 * 
 */
package com.fpq.acitvity.assignTask;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
/**
 * @author fpq
 *
 */
public class AssigneeTaskListener implements org.activiti.engine.delegate.TaskListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7686211878742612907L;
	private static String assignee;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		delegateTask.setAssignee(this.assignee); //设置节点负责人
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	
}
