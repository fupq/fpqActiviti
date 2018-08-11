/**
 * 
 */
package com.fpq.acitvity.entity;

import java.io.Serializable;

/**
 * @author fpq
 *
 */
public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Student() {
		
	}
	
	public Student(Integer id,String name) {
		this.id = id;
		this.name = name;
	}
	
	private Integer id;
	
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}
	
	
}
