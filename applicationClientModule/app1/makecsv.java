package app1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class makecsv {
	
	public static void make() throws ParserConfigurationException, SAXException, IOException, RowsExceededException, WriteException {
		try
		{
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			   DocumentBuilder builder = dbf.newDocumentBuilder();
			   Document doc = builder.parse("./cfg/conf.xml"); // 获取到xml文件
				DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
				   DocumentBuilder builder1 = dbf1.newDocumentBuilder();
				   Document doc1 = builder1.parse("./cfg/sqlconf.xml"); // 获取到xml文件
				StringBuilder sb = new StringBuilder();  

SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式


			
			        //创建新的一页
			   
			     
			
			
			   

			  // 下面开始读取
			  
			   Element root = doc1.getDocumentElement(); // 获取根元素
			   NodeList db = root.getElementsByTagName("sqlcfg");
			   for(int j=0;j<db.getLength();j++)
			   {
				 
				
			    Element ss=(Element) db.item(j);
			    	System.out.print(ss.getAttribute("dbname"));
			    	app1.condb  conn1=new app1.condb(ss.getAttribute("dbname"));
			    	
			    	ResultSet newrs; 
			    	  System.out.print(ss.getElementsByTagName("sql").item(0).getTextContent()); 
				newrs = conn1.executeQuery(ss.getElementsByTagName("sql").item(0).getTextContent()); 
				ResultSetMetaData rsmd = newrs.getMetaData() ;    
				int columnCount = rsmd.getColumnCount(); 
				
				PrintStream printStream = new PrintStream(new FileOutputStream("./data/"+ss.getAttribute("sqlname")+df.format(new Date())+".csv"));
	
				for(int i = 1;i<=rsmd.getColumnCount();i++){
					sb.append(rsmd.getColumnName(i)+","); 
			      
			        
				}
			      sb.replace(sb.length()-1, sb.length(), "\n");
			
				try { 
					int  m=0;
					while (newrs.next()) { 
						
						
						for(int i = 1;i<=rsmd.getColumnCount();i++){
							
								sb.append(newrs.getString(i)+","); 
								
							
							//System.out.print(rsmd.getColumnName(i)+"=" +newrs.getString(i)+"\n");
							
						}
						 sb.replace(sb.length()-1, sb.length(), "\n");
						  m++;
						
						}
					
					

				
				
				} catch (Exception e) { 
					e.printStackTrace(); 
					} 
				
			
				conn1.close(); 
				printStream.println(sb.toString()); 
				sb.delete(0, sb.length());
					
			   
			   }
				
			
         }
catch (SQLException e) { 
	e.printStackTrace(); 
}
		}
	//创建连接connection

}
