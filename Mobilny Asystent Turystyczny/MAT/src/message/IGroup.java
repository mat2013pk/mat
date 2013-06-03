package message;

import java.util.ArrayList;

import org.json.JSONObject;

public interface IGroup {
	// Zwraca kod powodzenia operacji: "0" serwer zg�asza OK,
	// "1" serwer zg�asza b��d, "-1" aplikacja zg�asza b��d.
	// Opr�cz tego zwracane moga by� te� wiadomo�ci wyj�tk�w.
	String getCode();

	// Zwraca kod powodzenia operacji j.w.
	String createGroup(String key, String email, String groupName,
			String groupPassword);

	// Zwraca kod powodzenia operacji j.w.
	String addUserToGroup(String key, String email, String emailAdd,
			String groupPassword);

	// Zwraca kod powodzenia operacji j.w.
	String delUserFromGroup(String key, String email, String emailDel);
	
	// Zwraca imie, nazwisko i adres mail uzytkownikow z grupy
	
	ArrayList<JSONObject> getListGroup(String key, String email);
}
