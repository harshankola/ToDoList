/**
 * 
 */
package com.paad.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Harrsh
 *
 */
public class ToDoItem {

	String task;
	Date created;
	
	public ToDoItem(String _task) {
		this(_task, new Date(java.lang.System.currentTimeMillis()));
	}
	
	public ToDoItem(String _task, Date _created) {
		task = _task;
		created = _created;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String dateString = sdf.format(created);
		return "(" + dateString + ") " + task;
	}

	/**
	 * @return the task
	 */
	public String getTask() {
		return task;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

}
