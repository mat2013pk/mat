package message;

import java.util.ArrayList;

public interface IMessage {
	// Zwraca kod powodzenia operacji: "0" serwer zg³asza OK,
	// "1" serwer zg³asza b³¹d, "-1" aplikacja zg³asza b³¹d.
	// Oprócz tego zwracane moga byæ te¿ wiadomoœci wyj¹tków.
	String getCode();

	// Zwraca kod powodzenia operacji j.w.
	// W przypadku wysy³ania wiadomoœæi do wszystkich nale¿y jako emailRecv
	// podaæ null
	String sendMsg(String key, String email, String emailRecv, String text,
			String filePath, Boolean compression);

	// Zwraca kod powodzenia operacji j.w., pobiera pierwsz¹ nieodczytan¹
	// wiadomoœæ z serwera
	// w razie istnienia wielu takich wiadomoœci nale¿y funkcjê wywo³aæ
	// wielokrotnie
	// Pobrane wiadomoœci zapisywane s¹ na liœcie
	String recvMsg(String key, String email);
	
	void recvMsgAll(String key, String email);

	/**
	 * FUNKCJE OBS£UGI LISTY Z WIADOMOŒCIAMI
	 * FUNKCJE POBIERANIA OBIEKTÓW WIADOMOŒCI
	 * w razie niepowodzenia zwracaj¹ wartoœæ null
	 * funkcja zwracaj¹ca pierwsz¹ od pocz¹tku listy nieprzeczytan¹ wiadomoœæ
	 * je¿eli takich brak, to zwraca ostatni¹ na liœcie
	 * @return 
	 */
	public Msg getMsg();

	// funkcja zwraca wiadomoœæ o odpowiednim id
	public Msg getMsgByIndex(int id);

	// funkcja zwraca listê z wiadomoœciami w postaci tablicey
	public ArrayList<Msg> getMsgAll();
	
	public ArrayList<String> getMsgByTextAll();

	// funkcja zwraca wiadomoœæ znajduj¹c¹ siê na miejscu i na liœcie(numeracja
	// od 0 do size-1)
	public Msg getMsgByListNumBer(int i);

	// FUNKCJE USUWANIA WIADOMOŒCI Z LISTY
	// niepowodzenie operacji zwracane -1, powodzenie zwracane 0
	// funkcja usuwa wiadomoœæ o podanym id
	public int rmvMsgByIndex(int id);

	// funkcja usuwa wiadomoœæ z miejsca i na liœcie
	public int rmvMsgByListNumBer(int i);

	// FUNKCJE POBIERANIA WIELKOŒCI LISTY, ORAZ OSTATNIEGO NADANEGO NUMERU ID
	// funkcja do pobierania id ostatniej odebranej wiadomoœci
	// wartoœæ -1 oznacza i¿ ¿adna wiadomoœæ nie zosta³a jeszcze pobrana
	public int getLastIndex();

	// funkcja do pobierania rozmiaru listy
	// gdy lista jest pusta rozmiar to 0
	public int getSize();

	// FUNKCJE DO OTCZYTYWANIA ZAWARTOŒCI WIADOMOŒCI
	// funkcja zwracaj¹ca treœæ z przekazanego do niej obiektu wiadomoœci
	public String getTextFromMsg(Msg message);

	// funkcja zwracaj¹ca adres pliku z przekazanego do niej obiektu wiadomoœci
	public String getFileFromMsg(Msg message);

	// funkcja zwracaj¹ca numer id z przekazanego do niej obiektu wiadomoœci
	public int getIdFromMsg(Msg message);

}
