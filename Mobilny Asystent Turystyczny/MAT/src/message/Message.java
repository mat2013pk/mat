package message;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

public class Message implements IMessage {	
	private String code = null, msg = null , file = null;
	Bitmap tmpBitmap;
	HttpClient client = new DefaultHttpClient();
	HttpGet request = new HttpGet();
	HttpResponse response = null;
	
	public String getCode() {
		return code;
	}
	
	public String getMsg(){
		return msg;
	}
	
	public String getFile(){
		return file;
	}
	
	private void resetCode(){
		code = "-1";
		response = null;
	}
	
	public String sendMsg(String key, String email, String emailRecv , String text, String filePath, Boolean compression){
		
		resetCode();
		
		// to kodowanie jest potrzebne gdy w gecie s¹ znaki specjalne np. spacje
		try {
			text = URLEncoder.encode(text, "UTF8");
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		}
		
		String uri = "http://mat.sofect.com/server.php?"
				+ "&key=" + key
				+ "&email=" + email
				+ "&msg=" + text;
		
		//adresat konkretny
		if(emailRecv != null)
			uri += "&function=sendMsg"
				+ "&emailRecv=" + emailRecv;
		//lub adresat grupowy
		else 
			uri += "&function=sendMsgToAll";

		//Wiadomosci bez obrazka œmigaj¹
		
		//czy wysy³amy obrazek
		if(filePath != null){
		//to jest jeszcze nieskoñczone
			
//			Bitmap tmpBitmap;
//			//czy kompresujemy
//			if(compression){
//				tmpBitmap = resizeAndCompressImage(filePath, 2);
//			}else 
//				tmpBitmap = resizeAndCompressImage(filePath, 1);
//			
//			string adresPliku = uploadImage(tmpBitmap);
//			uri += "&file=" + adresPliku;
		}		
		
		return useURI(uri);
	}
	
	private String uploadImage(String sciezkaPliku) {
	    HttpPost request2Upload = new HttpPost("http://mat.sofect.com/upload.php?");
	    HttpContext localContext = new BasicHttpContext();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("image", sciezkaPliku));
		
		StringBuilder sb = new StringBuilder();
		//UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
    	MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
    	//entity.addPart("image", new FileBody(new File(sciezkaPliku) ));
        
    	request2Upload.setEntity(entity);
        
		try {
	        response = client.execute(request2Upload, localContext);
	    } catch (ClientProtocolException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
		  	InputStream data;
			try {
				data = response.getEntity().getContent();
			
		        InputStreamReader reader = new InputStreamReader(data);
		        BufferedReader buffer = new BufferedReader(reader);
		        
		        String cur;
		        while ( (cur = buffer.readLine()) != null)
		        	sb.append(cur + "\n");
		        
				data.close();	
			} catch (IllegalStateException e) {
				return e.getMessage();
			} catch (IOException e) {
				return e.getMessage();
			}
	    
	    return sb.toString();
	}
	
	
// funkcja zwraca wiadomoœæ	
	public String recvMsgs(String key, String email){	

		resetCode();
		
		String uri = "http://mat.sofect.com/server.php?"
			+ "&key=" + key
			+ "&function=recvMsg"
			+ "&email=" + email;
		
		try {
			request.setURI(new URI(uri));
		} catch (URISyntaxException e) {
			
			return e.getMessage();
		}

		try {
			response = client.execute(request);
			InputStream in = response.getEntity().getContent();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(in);
			code = null;
			msg = null;
			file = null;
			NodeList tmpList = doc.getElementsByTagName("*");
			for (int j = 0; j < tmpList.getLength(); j++) {
				if (tmpList.item(j).getNodeName().equals("code")) {
					code = tmpList.item(j).getTextContent();
				} else if (tmpList.item(j).getNodeName().equals("msg")) {
					msg = tmpList.item(j).getTextContent();
				} else if (tmpList.item(j).getNodeName().equals("file")) {
					file = tmpList.item(j).getTextContent();
					//msg += "\nZa³¹czono plik: " + file;	//do³¹czenie do wiadomoœci info o za³¹czonym Pliku i jego adres
				}
			}
			in.close();
		} catch (ClientProtocolException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		} catch (ParserConfigurationException e) {
			return e.getMessage();
		} catch (SAXException e) {
			return e.getMessage();
		}

		return msg;
	}
	
	private static Bitmap resizeAndCompressImage(String filePath, int scale)
	{
		int sampleSize=2;
		Bitmap bitmap = null;
		ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = scale;//2;	//zmniejszenie iloœci linii pionowych i poziomych dwukrotnie=>rozdzielczoœæ zmniejszona 4krotnie 
		
		while (true)//zmniejszanie rozdzielczoœci w przypadku zbyt du¿ej rozdzielczoœci
		{
	        	try {
	                options.inSampleSize=sampleSize;
	                bitmap=BitmapFactory.decodeFile(filePath, options);
	                break;
	            } catch (Exception ex) {
	                sampleSize*=sampleSize;
	            }
		} 
		if(bitmap.compress(Bitmap.CompressFormat.JPEG, 60, imageStream))//kompresja bitmapy do strumienia
			bitmap=BitmapFactory.decodeStream(new ByteArrayInputStream(imageStream.toByteArray()));
		
		return bitmap;
	}
	
	private String useURI(String uri) {
		
		resetCode();
		
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
}
