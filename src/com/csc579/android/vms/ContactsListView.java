package com.csc579.android.vms;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactsListView extends ListActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Get the contacts from the model for display in the view
		String[] contacts = Conversation.getConversation().getContactListing();

		if(contacts != null){		
			//Bind the list to the view object
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, contacts));
			ListView lv = getListView();
			lv.setTextFilterEnabled(true);
			
			// Now lets add a listener for the lcik event, so we can do something usefull
			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// Now we clicked a contact for now let's just return
					returnToCaller(new Bundle());
				}
			});
		}
	}


	public void returnToCaller(Bundle bundle) {
		// sets the result for the calling activity
		setResult(RESULT_OK);
		// equivalent of 'return'
		finish();
	}
	
}
