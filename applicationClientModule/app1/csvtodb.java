package app1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.xml.sax.SAXException;

public class csvtodb {
	public static void make(String dbname) throws ParserConfigurationException, SAXException, IOException, RowsExceededException, WriteException {
		try
		{
			app1.condb  conn1=new app1.condb(dbname);
			String str="";
			String mktable="";
			 File f=new File("./csvdata");
			 BufferedReader br=null;
			 if(f!=null){
		            if(f.isDirectory()){
		                File[] fileArray=f.listFiles();
		                jxl.Workbook readwb = null;  
		                if(fileArray!=null){
		                    for (int k = 0; k < fileArray.length; k++) {
		                        //递归调用
		                    	
		                    	 br = new BufferedReader(new FileReader(fileArray[k]));
		                         String line = ""; 
		                        int m=0;
		                        String col="";
		                        String values="";
		                         while ((line = br.readLine()) != null || (line = br.readLine()) != "") { 
		                        	 if (m==0)
		                        	 {
		                        		 col=line;
		                        		 mktable=line;
		                        		 mktable=mktable.replace(",", " varchar2(255), ");
		                        		 mktable="create table "+fileArray[k].getName().substring(0,fileArray[k].getName().length()-4)+" ( "+mktable+"  varchar2(255))";
		                        		 conn1.update(mktable);
		                        		 System.out.println(mktable);
		                        	 }
		                        	 else {
		                        		 
		                        		values=app1.util.todbchar(line);
		                        		 if (values.equals(""))
		                        		 {
		                        			 break;
		                        		 }
		                        		
		                        		values="'"+values.replace(",", "','")+"'";
		                        		  str="insert into "+fileArray[k].getName().substring(0,fileArray[k].getName().length()-4)+" ("+col+") values (" +values +")"; 
				     		                
				 		                	System.out.println(str);
				 		                	values=""; 
				 		                	conn1.update(str);
		                        	 }
		                             m++;
		                           
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
