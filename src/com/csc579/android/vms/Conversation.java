package com.csc579.android.vms;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;

/*
 * This class stores the conversation information for a voice message
 */
public class Conversation {

	private String path;
	private Contact contact;
	private static Conversation conversation;
	private List<Contact> contactList  = new ArrayList<Contact>();
	private static String SERVERIP = "192.168.0.103"; 
	private static final int SERVERPORT = 8080;
	
	public static Conversation getConversation()
	  {
		  if(conversation == null){
			  conversation = new Conversation();
		  }
		  return conversation;
	  }
	
	public void setContact (Contact contact){
		this.contact = contact;
	}
	
	public void setFilePathtoRec(String path){
		this.path = path;
	}
	public String getFilePathToRec(){
		return this.path;
	}
	
	public void addContact(Contact newContact)
	{
		this.contactList.add(newContact);
	}
	
	public List<Contact> getContacts(){
		return this.contactList;
	}
	
	/*
	 * Get a list in a string array form for display viewing
	 */
	public String [] getContactListing(){
		
		String [] contactsForDisplay = new String[contactList.size()] ; 
		for (int i=0; i< contactList.size(); i++){
			contactsForDisplay[i] = contactList.get(i).getDisplayName();
			Log.d("Convo: " ,contactsForDisplay[i]);
		}
		return contactsForDisplay;
	}
	
	/*
	 * Retrieve the contact list from the server
	 * This acts as the model contact listing, the view will query
	 * the return contact list for the contact info for a message send.
	 */
	public void retrieveContactListFromServer(){
		Socket socket = null;
		PrintWriter command = null;
		DataInputStream contacts = null;
		String contacts_string = "";
		
		try{
			InetAddress serverAddress = InetAddress.getByName(SERVERIP);
			Log.d("ContactList","Connecting to " + SERVERIP);
			socket = new Socket(serverAddress, SERVERPORT);
		
			Log.d("ContactList","Sending Contact List request");
			command = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			command.println("Contact Request");
			
			//Now we need to set ourselves to receive the list
			contacts = new DataInputStream(socket.getInputStream());
			String line = null;
			
			while((line = contacts.readLine())!=null){
				Log.d("Contacts List", line);
				contacts_string += line;
				if(contacts_string.indexOf("DONE") !=-1){
					break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String[] tokens = null;
		if(contacts_string!=null){
			//Let's parse the contacts list
			String delims = "[:;]";
			tokens = contacts_string.split(delims);
			List<String> list = new ArrayList<String>(Arrays.asList(tokens));
			//Get rid of the end message and beginning message
			list.remove(0);
			list.remove(list.size()-1);
			
			/* 
			 * Make a new contact for each one received from the bootstrap server
			 */
			for( int i = 0; i < list.size(); i+=2){
				Conversation.getConversation().addContact(new Contact(list.get(i+1), list.get(i)));
			}
			
		}
	}
}
