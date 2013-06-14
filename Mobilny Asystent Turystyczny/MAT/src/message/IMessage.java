package message;

import java.util.ArrayList;

public interface IMessage {
	// Zwraca kod powodzenia operacji: "0" serwer zg�asza OK,
	// "1" serwer zg�asza b��d, "-1" aplikacja zg�asza b��d.
	// Opr�cz tego zwracane moga by� te� wiadomo�ci wyj�tk�w.
	String getCode();

	// Zwraca kod powodzenia operacji j.w.
	// W przypadku wysy�ania wiadomo��i do wszystkich nale�y jako emailRecv
	// poda� null
	String sendMsg(String key, String email, String emailRecv, String text,
			String filePath, Boolean compression);

	// Zwraca kod powodzenia operacji j.w., pobiera pierwsz� nieodczytan�
	// wiadomo�� z serwera
	// w razie istnienia wielu takich wiadomo�ci nale�y funkcj� wywo�a�
	// wielokrotnie
	// Pobrane wiadomo�ci zapisywane s� na li�cie
	String recvMsg(String key, String email);
	
	void recvMsgAll(String key, String email);

	/**
	 * FUNKCJE OBS�UGI LISTY Z WIADOMO�CIAMI
	 * FUNKCJE POBIERANIA OBIEKT�W WIADOMO�CI
	 * w razie niepowodzenia zwracaj� warto�� null
	 * funkcja zwracaj�ca pierwsz� od pocz�tku listy nieprzeczytan� wiadomo��
	 * je�eli takich brak, to zwraca ostatni� na li�cie
	 * @return 
	 */
	public Msg getMsg();

	// funkcja zwraca wiadomo�� o odpowiednim id
	public Msg getMsgByIndex(int id);

	// funkcja zwraca list� z wiadomo�ciami w postaci tablicey
	public ArrayList<Msg> getMsgAll();
	
	public ArrayList<String> getMsgByTextAll();

	// funkcja zwraca wiadomo�� znajduj�c� si� na miejscu i na li�cie(numeracja
	// od 0 do size-1)
	public Msg getMsgByListNumBer(int i);

	// FUNKCJE USUWANIA WIADOMO�CI Z LISTY
	// niepowodzenie operacji zwracane -1, powodzenie zwracane 0
	// funkcja usuwa wiadomo�� o podanym id
	public int rmvMsgByIndex(int id);

	// funkcja usuwa wiadomo�� z miejsca i na li�cie
	public int rmvMsgByListNumBer(int i);

	// FUNKCJE POBIERANIA WIELKO�CI LISTY, ORAZ OSTATNIEGO NADANEGO NUMERU ID
	// funkcja do pobierania id ostatniej odebranej wiadomo�ci
	// warto�� -1 oznacza i� �adna wiadomo�� nie zosta�a jeszcze pobrana
	public int getLastIndex();

	// funkcja do pobierania rozmiaru listy
	// gdy lista jest pusta rozmiar to 0
	public int getSize();

	// FUNKCJE DO OTCZYTYWANIA ZAWARTO�CI WIADOMO�CI
	// funkcja zwracaj�ca tre�� z przekazanego do niej obiektu wiadomo�ci
	public String getTextFromMsg(Msg message);

	// funkcja zwracaj�ca adres pliku z przekazanego do niej obiektu wiadomo�ci
	public String getFileFromMsg(Msg message);

	// funkcja zwracaj�ca numer id z przekazanego do niej obiektu wiadomo�ci
	public int getIdFromMsg(Msg message);

}
