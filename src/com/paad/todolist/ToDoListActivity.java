package com.paad.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class ToDoListActivity extends Activity implements NewItemFragment.OnNewItemAddedListener{

    private ArrayList<ToDoItem> todoItems;
	private ToDoItemAdapter aa;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Inflate your view
        setContentView(R.layout.main);
        
        // Get references to the Fragments
        FragmentManager fm = getFragmentManager();
        ToDoListFragment todoListFragment =
        		(ToDoListFragment) fm.findFragmentById(R.id.TodoListFragment);
        
        //Create the array list of to do items
        todoItems = new ArrayList<ToDoItem>();
        
        // Create the array adapter to bind the array to the list view
        int resID = R.layout.todolist_item;
        aa = new ToDoItemAdapter(this,resID,todoItems);
        
        // Bind the Array Adapter to the List View
        todoListFragment.setListAdapter(aa);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onNewItemAdded(String newItem) {
		// TODO Auto-generated method stub
		ToDoItem newToDoItem = new ToDoItem(newItem);
		todoItems.add(0, newToDoItem);
		aa.notifyDataSetChanged();
	}

    
}
