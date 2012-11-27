/**
 * 
 */
package com.paad.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Harrsh
 *
 */
public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

	int resource;
	
	/**
	 * @param context
	 * @param textViewResourceId
	 * @param objects
	 */
	public ToDoItemAdapter(Context context, int textViewResourceId,
			List<ToDoItem> items) {
		super(context, textViewResourceId, items);
		// TODO Auto-generated constructor stub
		resource = textViewResourceId;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout todoView;
		// TODO Auto-generated method stub
		ToDoItem item = getItem(position);
		
		// Get the data from the item object.
		String taskString = item.getTask();
		Date createdDate = item.getCreated();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String dateString = sdf.format(createdDate);
		
		// Inflate the view if it doesn't exists, otherwise update the view.
		if (convertView == null) {
			todoView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li;
			li = (LayoutInflater) getContext().getSystemService(inflater);
			li.inflate(resource, todoView, true);
		} else {
			todoView = (LinearLayout) convertView;
		}
		
		//get layout resources and update their text.
		TextView dateView = (TextView) todoView.findViewById(R.id.rowDate);
		TextView taskView = (TextView) todoView.findViewById(R.id.row);
		
		dateView.setText(dateString);
		taskView.setText(taskString);
		
		return todoView;
	}
}
