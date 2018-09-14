package com.gojekassessment.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;




public class Connection {
	
	
	
	
	/**
	 * @author VTuli
	 * @param url
	 * @return HttpURLConnection
	 * @purpose - Method to connect to the given endpoint Url( both http/https). 
	 */
	
	public HttpURLConnection connect(String url)
	{
		HttpURLConnection conn = null;
		
		try
		{
			if(url.contains("https://"))
			{
				conn = getHttpsConnection(url, "GET");
			}
			else if(url.contains("http://"))
			{
				conn = getHttpConnection(url, "GET");
			}
			else
			{
				System.out.println("Transfer protocol information missing in the input Url.");
			}
		}
		catch(IOException ioe)
		{
			System.out.println("Connection interrupted...!!! ");
			System.out.println("Unable to get connection with the Url : " + url + "/n" + ioe);
		}
		
		return conn;
	}
	
	
	
	
	/**
	 * @author VTuli
	 * @param url
	 * @param methodType
	 * @throws IOException
	 * @return HttpsURLConnection
	 * @purpose - Method to get connection to secured transfer protocol Url. 
	 */
	
	public HttpsURLConnection getHttpsConnection(String url, String methodType) throws IOException
	{

		HttpsURLConnection httpsCon = (HttpsURLConnection) (new URL(url)).openConnection();

		httpsCon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

		httpsCon.setRequestMethod(methodType);

		httpsCon.setConnectTimeout(3000);

		return httpsCon;
	}
	
	
	
	
	
	/**
	 * @author VTuli
	 * @param url
	 * @param methodType
	 * @throws IOException
	 * @return HttpURLConnection
	 * @purpose - Method to get connection to standard transfer protocol Url. 
	 */
	
	public HttpURLConnection getHttpConnection(String url, String methodType) throws IOException
	{
		HttpURLConnection httpCon = (HttpURLConnection) (new URL(url)).openConnection();

		httpCon.setRequestMethod(methodType);

		httpCon.setConnectTimeout(3000);

		return httpCon;
	}
	
	
	
	
	/**
	 * @author VTuli
	 * @return StringBuffer
	 * @purpose - Method to get response from the API end point.
	 */

	public StringBuffer readResponseHttp(HttpURLConnection conn) {

		StringBuffer content = null;
		String inputLine;
		BufferedReader in = null;

		try {
			
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			content = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {

				content.append(inputLine);

			}

		} catch (IOException e) {
			
			System.out.println();
			
		} finally {

			try {
				
				in.close();
				conn.disconnect();
				
			} catch (IOException e) {
				
				System.out.println("Exception occured while closing Input Stream / disconnecting connection to Url." + e);
			}

		}

		return content;
	}


}
