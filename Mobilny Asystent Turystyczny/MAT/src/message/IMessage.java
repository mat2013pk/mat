package message;

public interface IMessage {
	// Zwraca obiekt wiadomosci
	Msg getMsg();

	// Zwraca adres obrazka
	String getFile();

	// Zwraca kod powodzenia operacji: "0" serwer zg³asza OK,
	// "1" serwer zg³asza b³¹d, "-1" aplikacja zg³asza b³¹d.
	// Oprócz tego zwracane moga byæ te¿ wiadomoœci wyj¹tków.
	String getCode();

	// Zwraca kod powodzenia operacji j.w.
	String sendMsg(String key, String email, String emailRecv, String text,
			String filePath, Boolean compression);
	
	// Zwraca kod powodzenia operacji j.w.
	String recvMsg(String key, String email);
}
