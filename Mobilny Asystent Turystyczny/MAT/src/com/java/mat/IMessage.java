package com.java.mat;

public interface IMessage {
	// Zwraca tekst wiadomo�ci
	String getMsg();

	// Zwraca adres obrazka
	String getFile();

	// Zwraca kod powodzenia operacji: "0" serwer zg�asza OK,
	// "1" serwer zg�asza b��d, "-1" aplikacja zg�asza b��d.
	// Opr�cz tego zwracane moga by� te� wiadomo�ci wyj�tk�w.
	String getCode();

	// Zwraca kod powodzenia operacji j.w.
	String sendMsg(String key, String email, String emailRecv, String text,
			String filePath, Boolean compression);
	
	// Zwraca kod powodzenia operacji j.w.
	String recvMsgs(String key, String email);
}
