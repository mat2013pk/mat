package com.java.mat;

public class Autoryzacja {

	private static boolean zalogowany = false;
	private static String mail ="";
	private Autoryzacja(){}
	public static boolean getState()
	{
		return zalogowany;
	}
	public static String getMail()
	{
		return mail;
	}
	public static boolean zarejestruj(String email, String imie, String nazwisko){
		String url = GlobalSettings.getInstance().getHost()+ "&function=registerUser&name=" 
	            +imie+"&surname="
				+nazwisko.toString()+"&email="
				+email;
	    
		if(Connection.connectToServer(url) == null){
			return false;
		} else {
			return true;
		}
	}
	public static boolean zaloguj(String email, String hasloMd5 )
	{
		
		String url = GlobalSettings.getInstance().getHost() + "&function=loginUser&email=" 
	            +email+"&password="
				+hasloMd5;
		
		if(Connection.connectToServer(url) == null){
			return false;
		} else {
			return true;
		}
	}
	
}