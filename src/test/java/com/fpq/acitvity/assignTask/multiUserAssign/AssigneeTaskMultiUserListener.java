/**
 * 
 */
package com.fpq.acitvity.assignTask.multiUserAssign;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
/**
 * @author fpq
 *
 */
public class AssigneeTaskMultiUserListener implements org.activiti.engine.delegate.TaskListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7686211878742612907L;
//	private static String assignee;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		delegateTask.addCandidateUser("付品欣");
		delegateTask.addCandidateUser("王淑华");
	}

//	public String getAssignee() {
//		return assignee;
//	}
//
//	public void setAssignee(String assignee) {
//		this.assignee = assignee;
//	}

	
}
