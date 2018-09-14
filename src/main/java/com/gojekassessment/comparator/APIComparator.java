package com.gojekassessment.comparator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import com.gojekassessment.connection.Connection;






public class APIComparator implements IComparator {

	
	
	/**
	 * @author VTuli
	 * @param String , String
	 * @return boolean
	 * @purpose - Method to compare both JSON/XML response received from the given endpoint Urls(both http/https) taken from 2 different files respectively. 
	 */
	
	public boolean compare(String resp1, String resp2)  {

		try {

			if (resp1.startsWith("{") && resp2.startsWith("{")) {

				JSONObject jObj1 = new JSONObject(resp1);
				JSONObject jObj2 = new JSONObject(resp2);

				/**
				 * JSON Assert to compare 2 JSON objects are equal or not. 
				 * The assertEquals method of JSONAssert will throw AssertionError on failure and moves to the
				 * next line if Assertion passes.
				 */

				JSONAssert.assertEquals(jObj1, jObj2, true);
				

			} else if (resp1.startsWith("<") && resp2.startsWith("<")) {
				
			        XMLUnit.setIgnoreWhitespace(true);
			        XMLUnit.setIgnoreAttributeOrder(true);
			        XMLUnit.setCompareUnmatched(true);
			        
			        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(resp1, resp2));
			        
			        if(!diff.similar())
			        	throw new AssertionError();

			} else {
				
				throw new AssertionError("Response type mis-match error...!!!");
			}
			
			return true;

		} catch (AssertionError | Exception ae) {
			
			return false;
		}
		
		
		
	}

	
	

	/**
	 * @author VTuli
	 * @param filePath
	 * @throws IOException
	 * @purpose - Method to get the data(API end point Url) from the input file. 
	 */
	
	public void getData(File file1, File file2)
	{
		String url1 = null, url2 = null, resp1 = null, resp2 = null;
		BufferedReader data1 = null ,data2 = null;
		
		Connection con = new Connection();
		APIComparator comp = new APIComparator();
		
		try 
		{
			
		 data1 = new BufferedReader(new FileReader(file1));
		 data2 = new BufferedReader(new FileReader(file2));
		
		 
		 /**
		  * Read the respective Urls from the 2 given files,
		  * fetch the response for both the Urls,
		  * call the compare(String, String) method to compare the response and print the result to the console. 
		  * 
		  */
		 while( (url1 = data1.readLine()) != null && (url2 = data2.readLine()) != null)
		 {
			 
			 resp1 = con.readResponseHttp(con.connect(url1)).toString();
			 resp2 = con.readResponseHttp(con.connect(url2)).toString();
			 
			 
			 if(comp.compare(resp1, resp2))
				 System.out.println("Url from Input File 1 : " + url1 + "\n" + "equals" + "\n" + "Url from Input File 2 : " + url2 + "\n");
			 else
				 System.out.println("Url from Input File 1 : " + url1 + "\n" + "not equals" + "\n" + "Url from Input File 2 : " + url2 + "\n");
			 
			 
		 }
		 
		
		}
		catch(IOException ie)
		{
			System.out.println("Input data file read interrupted...!!!");
			System.out.println("Unable to read file." + ie);
		}
		catch(Exception e)
		{
			System.out.println("Unexpected exception occured...!!!" + e);
		}
		finally {
			
			try {
				
				if(data2!=null || data1!=null)
				{
				data1.close();
				data2.close();
				}

			} catch (IOException ie) {

				System.out.println("Unable to close the Input stream." + ie);
			}
			catch (Exception ie) {

			System.out.println("Unexpected Exception occured...." + ie);
		}
		
		}
		
	}
	

}
