package com.csc579.android.vms;

public class Contact {

	private String name;
	private String ip;
	
	public Contact (String name, String ip){
		this.name = name;
		this.ip = ip;
	}
	
	public String getDisplayName(){
		return this.name;
	}
	public String getContactIp(){
		return this.ip;
	}
}
