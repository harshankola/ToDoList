package com.paad.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class ToDoListActivity extends FragmentActivity implements
		NewItemFragment.OnNewItemAddedListener, LoaderCallbacks<Cursor> {

	private ArrayList<ToDoItem> todoItems;
	private ToDoItemAdapter aa;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Inflate your view
		setContentView(R.layout.main);

		// Get references to the Fragments
		FragmentManager fm = getSupportFragmentManager();
		ToDoListFragment todoListFragment = (ToDoListFragment) fm
				.findFragmentById(R.id.TodoListFragment);

		// Create the array list of to do items
		todoItems = new ArrayList<ToDoItem>();

		// Create the array adapter to bind the array to the list view
		int resID = R.layout.todolist_item;
		aa = new ToDoItemAdapter(this, resID, todoItems);

		// Bind the Array Adapter to the List View
		todoListFragment.setListAdapter(aa);

		getSupportLoaderManager().initLoader(0, null, this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getSupportLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onNewItemAdded(String newItem) {
		// TODO Auto-generated method stub
		ContentResolver cr = getContentResolver();
		
		ContentValues values = new ContentValues();
		values.put(ToDoContentProvider.KEY_TASK, newItem);
		
		cr.insert(ToDoContentProvider.CONTENT_URI, values);
		getSupportLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		CursorLoader loader = new CursorLoader(this,
				ToDoContentProvider.CONTENT_URI, null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		int keyTaskIndex = cursor
				.getColumnIndexOrThrow(ToDoContentProvider.KEY_TASK);

		todoItems.clear();
		while (cursor.moveToNext()) {
			ToDoItem newItem = new ToDoItem(cursor.getString(keyTaskIndex));
			todoItems.add(newItem);
		}

		aa.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

}
