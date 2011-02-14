package com.csc579.android.vms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VoiceMessenger extends Activity {
	
	private static final String TAG = "VMS";
	private Conversation conversation = Conversation.getConversation();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Setup up the UI
        setContentView(R.layout.main);
        
        // Add the listeners to the UI
        Button btnRecord = (Button) findViewById(R.id.button1);
        btnRecord.setOnClickListener(recordListener);
        
        Button btnStop = (Button) findViewById(R.id.button2);
        btnStop.setOnClickListener(stopListener);
        
        Button btnPlay = (Button) findViewById(R.id.button3);
        btnPlay.setOnClickListener(playListener);
        
        Button btnContacts = (Button) findViewById(R.id.button4);
        btnContacts.setOnClickListener(contactListener);
        
        conversation.retrieveContactListFromServer();
    }
    
    private OnClickListener contactListener = new OnClickListener()
    {
    	public void onClick(View arg0){
    		/*Let's start the contact list so someone can choose what 
			 * User to send the message to.
			 */
			Intent intent = new Intent(VoiceMessenger.this, ContactsListView.class);
			startActivity(intent);
    	}
    };
    
    /* Hook into the view buttons for testing
     * See if we can get a file to be created
     */
    private OnClickListener recordListener = new OnClickListener()
    {
		public void onClick(View arg0) {
	        // Start recording
			try {
				AudioRecorder.getAudioRecorder().start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}    	
    };
    
    private OnClickListener stopListener = new OnClickListener()
    {
		public void onClick(View arg0) {
			try {
				AudioRecorder.getAudioRecorder().stop();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  	
    };
    
    private OnClickListener playListener = new OnClickListener()
    {
		public void onClick(View arg0) {
			// play the file somehow;
			try {
				AudioRecorder.getAudioRecorder().play();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}	
    	
    };
    
    protected void onActivityResult(int requestCode, 
    		int resultCode, String strdata, Bundle bundle)
    {
    	Log.i(TAG, "VMS got a response from ListView");

    	if (resultCode == Activity.RESULT_CANCELED) {
    		Log.i(TAG,
    		"ListView was cancelled");
    		Log.i(TAG,
    				"ListView was cancelled - data =" + bundle);
    	}
    	else{
        	Log.i(TAG, "MainDriver main-activity got result from sub-activity");
    	}
    }
    
}