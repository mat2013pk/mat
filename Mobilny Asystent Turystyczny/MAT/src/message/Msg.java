package message;

//klasa przechowuj�ca wiadomo�� wraz z funkcjami do pobierania zawarto�ci
public class Msg {
	private int id;
	private String message;
	private String file;
	public Msg(int new_id, String new_message, String new_file){
		id=new_id;
		message=new_message;
		file=new_file;
	}
public int getid(){return id;}
public String getmsg(){return message;}
public String getfile(){return file;}
}
