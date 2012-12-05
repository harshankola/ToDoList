/**
 * 
 */
package com.paad.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.EditText;

/**
 * @author Harrsh
 *
 */
public class NewItemFragment extends Fragment {

	public interface OnNewItemAddedListener {
		public void onNewItemAdded(String newItem);

	}
	private OnNewItemAddedListener onNewItemAddedListener;

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.new_item_fragment, container, false);
		
		final EditText myEditText = (EditText) view.findViewById(R.id.myEditText);
		myEditText.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN)
					if ((keyCode ==  KeyEvent.KEYCODE_DPAD_CENTER) || 
							(keyCode == KeyEvent.KEYCODE_ENTER)) {
						String newItem = myEditText.getText().toString();
						onNewItemAddedListener.onNewItemAdded(newItem);
						myEditText.setText("");
						return true;
					}
				return false;
			}
		});
		return view;
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		try {
			onNewItemAddedListener = (OnNewItemAddedListener)activity;
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			throw new ClassCastException(activity.toString() +
					" must implement OnNewItemAddedListener");
		}
	}

}
