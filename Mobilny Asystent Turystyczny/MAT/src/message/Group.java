package message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.java.mat.Connection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;


public class Group implements IGroup {
	// Kod ostatnio otrzymany od serwera
	private String code = "-1";	//-1 aplikacja zglasza b³¹d, 0 serwer zg³asza OK, 1 serwer zg³asza b³¹d
	private String uri = "";
	private HttpClient client = new DefaultHttpClient();
	private HttpGet request = new HttpGet();
	private HttpResponse response = null;

	public String getCode() {
		return code;
	}
	
	private void resetCode(){
		code = "-1";
		response = null;
	}

	public String createGroup(String key, String email, String groupName,
			String groupPassword) {

		resetCode();
		
		//przewodnik nowej grupy jest usuwany ze starej 
		//delUserFromGroup(key, email, email);
		
		// to kodowanie jest potrzebne gdy w gecie s¹ znaki specjalne np. spacje
		try {
			groupName = URLEncoder.encode(groupName, "UTF8");
			groupPassword = URLEncoder.encode(groupPassword, "UTF8");
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		}

		uri = "http://mat.sofect.com/server.php?" + "key=" + key
				+ "&function=createGroup" + "&email=" + email + "&groupname="
				+ groupName + "&groupPassword=" + groupPassword;

		// do testowania
		// uri = "http://mat.sofect.com/server.php?key=412fg68kw378&function=createGroup&email=dominik@gmail.com&groupname=testowa&groupPassword=tajnne";
		// uri = "https://www.googleapis.com/plus/v1/people/114827005039421548109";
		return useURI(uri);
	}
	
	public String addUserToGroup(String key, String email, String emailAdd,
			String groupPassword) {
		
		resetCode();
		
		//u¿ytkownik przed dodaniem do nowej grupy jest usuwany ze starej 
		//delUserFromGroup(key, emailAdd, emailAdd);
		
		// to kodowanie jest potrzebne gdy w gecie s¹ znaki specjalne np. spacje
		try {
			groupPassword = URLEncoder.encode(groupPassword, "UTF8");
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		}

		uri = "http://mat.sofect.com/server.php?" + "key=" + key
				+ "&function=addUserToGroup" + "&email=" + email + "&emailAdd="
				+ emailAdd + "&groupPassword=" + groupPassword;

		return useURI(uri);
	}
	
	public String delUserFromGroup(String key, String email, String emailDel) {
		
		resetCode();

		uri = "http://mat.sofect.com/server.php?" + "key=" + key
				+ "&function=delUserFromGroup" + "&email=" + email
				+ "&emailDel=" + emailDel;

		return useURI(uri);
	}
	
	// funkcja przyjmuje URI, ustawia je do obiektu request, wykonuje, parsuje i
	// zwraca kod lub id wyjatku
	private String useURI(String uri) {
		// wczeœniejsze kodowanie wymusza tu takie przechwytywanie wyj¹tków
		try {
			request.setURI(new URI(uri));
		} catch (URISyntaxException e) {
			return e.getMessage();
		}

		try {
			response = client.execute(request);
			code = Parser.parseOnlyCode(response);
			return code;
		} catch (ClientProtocolException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	@Override
	public ArrayList<JSONObject> getListGroup(String key, String email) {
			resetCode();
						
			ArrayList<JSONObject> groupList = new ArrayList<JSONObject>();

			uri = "http://mat.sofect.com/server.php?" + "key=" + key
					+ "&function=getGroupUsers" + "&email=" + email;
//			Log.e("uri", uri);
			
			groupList = Connection.connectToServerGetArray(uri);
			if(groupList.size() > 1){
				groupList.remove(groupList.get(0));
				code = "0";
			}
//			String rozmiar = "" + groupList.size();
//			try {
//				Log.e("2 funkcja", (groupList.get(3).getString("name")).toString());
//				Log.e("Rozmiar", rozmiar);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return groupList;
	}

}
