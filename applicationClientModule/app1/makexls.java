package app1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

public class makexls {
	
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


				File f= new File("./data/report"+df.format(new Date())+".xls") ; 
				OutputStream os =null;
				os=new FileOutputStream(f) ;
				   WritableWorkbook workbook = Workbook.createWorkbook(os);
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
				WritableFont bold = new WritableFont(WritableFont.ARIAL,12,WritableFont.BOLD);//设置字体种类和黑体显示,字体为Arial,字号大小为10,采用黑体显示
				WritableFont bold1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD);//设置字体种类和黑体显示,字体为Arial,字号大小为10,采用黑体显示
				WritableFont bold2 = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD);//设置字体种类和黑体显示,字体为Arial,字号大小为10,采用黑体显示
				WritableCellFormat titleFormate = new WritableCellFormat(bold);//生成一个单元格样式控制对象
		        WritableCellFormat titleFormate1 = new WritableCellFormat(bold1);//生成一个单元格样式控制对象
		        WritableCellFormat titleFormate2 = new WritableCellFormat(bold2);//生成一个单元格样式控制对象
		        titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格中的内容水平方向居中
		        titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直方向居中
		        titleFormate1.setAlignment(jxl.format.Alignment.LEFT);//单元格中的内容水平方向居中
		        titleFormate1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直方向居中
		        titleFormate2.setAlignment(jxl.format.Alignment.LEFT);//单元格中的内容水平方向居中
		        titleFormate2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直方向居中
		        titleFormate.setBackground(Colour.GREEN);
		        titleFormate1.setBackground(Colour.GREY_25_PERCENT);
		        //titleFormate2.setBackground(Colour.GREY_40_PERCENT);

			     WritableSheet[] sheet=new WritableSheet[db.getLength()];
			    		 sheet[j]= workbook.createSheet(ss.getAttribute("sqlname"),j);
			    		

			
				for(int i = 1;i<=rsmd.getColumnCount();i++){
					 sheet[j].setRowView(0, 400, false);
					 sheet[j].setColumnView(i-1,50);
			        Label biaotou = new Label(i-1,0,rsmd.getColumnName(i),titleFormate);
			        sheet[j].addCell(biaotou);
			       
			        
				}
			
			
				try { 
					int  m=0;
					while (newrs.next()) { 
						
						
						for(int i = 1;i<=rsmd.getColumnCount();i++){
							if (m%2==1)
							{
								sheet[j].setRowView(m+1, 300, false);
								Label data = new Label(i-1,m+1,newrs.getString(i),titleFormate1);
						        sheet[j].addCell(data);
							}
							else
							{
								sheet[j].setRowView(m+1, 300, false);
								Label data = new Label(i-1,m+1,newrs.getString(i),titleFormate2);
						        sheet[j].addCell(data);
							}
							
							//System.out.print(rsmd.getColumnName(i)+"=" +newrs.getString(i)+"\n");
							
						}
						
						  m++;
						
						}
					
					

				
				
				} catch (Exception e) { 
					e.printStackTrace(); 
					} 
				
			
				conn1.close(); 
					
			   
			   }
			
				workbook.write();
		        workbook.close();
		        os.close();	
			
         }
catch (SQLException e) { 
	e.printStackTrace(); 
}
		}
	//创建连接connection

}
