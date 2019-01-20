package app1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xlstodb {
	public static void make(String dbname) throws ParserConfigurationException, SAXException, IOException, RowsExceededException, WriteException {
		try
		{
			app1.condb  conn1=new app1.condb(dbname);
			String str="";
			String mktable="";
			 File f=new File("./xlsdata");
			 if(f!=null){
		            if(f.isDirectory()){
		                File[] fileArray=f.listFiles();
		                jxl.Workbook readwb = null;  
		                if(fileArray!=null){
		                    for (int k = 0; k < fileArray.length; k++) {
		                        //递归调用
		     InputStream instream = new FileInputStream(fileArray[k]);   
		     readwb = jxl.Workbook.getWorkbook(instream); 
		     
		     Sheet[] readsheet = readwb.getSheets(); 
		     for (int z = 0; z < readwb.getNumberOfSheets(); z++) 
		     {
		     String col="";
		     String values="";
		    
		     int rsColumns = readsheet[z].getColumns();
		     int rsRows = readsheet[z].getRows();  
			   for (int i = 0; i < rsRows; i++)   
					 
		         {   
		 
		                for (int j = 0; j < rsColumns; j++)   
		  
		                {   
		                       if (i==0)
		                       {
		                    	  col+= readsheet[z].getCell(j, i).getContents()+",";
		                    	   
		                       }
		                       else 
		                       {
		                    	   
		                    	  values+="'"+app1.util.todbchar(readsheet[z].getCell(j, i).getContents())+"',";
		                       }   
		                       
		                       //System.out.println(col);
		                     // System.out.println(values);
		   
		                 }   
		                if (i==0)
	                       {
		                	col=col.substring(0, col.length()-1); 
		                	 mktable=col;
                    		 mktable=mktable.replace(",", " varchar2(255), ");
                    		 mktable="create table "+readsheet[z].getName()+" ( "+mktable+"  varchar2(255))";
                    		 conn1.update(mktable);
	                    	   
	                       }
		                else
		                {
		                	values=values.substring(0, values.length()-1); 
		                	str="insert into "+readsheet[z].getName()+" ("+col+") values (" +values +")"; 
		                
		                	//System.out.println(str);
		                	values=""; 
		                	conn1.update(str);
		                
		                }
                         
		               
		         }
			 
			   
		                    }
		                }
		                    conn1.close();
		            }
		             
		                   
			 }    
		
		}
		}
			
		catch (Exception e) { 
			e.printStackTrace(); 
		}
		}

}
