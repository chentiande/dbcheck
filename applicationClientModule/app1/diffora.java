package app1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class diffora {
	public static void make(String dbname1,String dbname2) throws ParserConfigurationException, SAXException, IOException, RowsExceededException, WriteException {
		try
		{
			System.out.println("diffora");
			app1.condb  conn1=new app1.condb(dbname1);
			app1.condb  conn2=new app1.condb(dbname2);
			app1.condb  conn3=new app1.condb(dbname1);
			ResultSet rs1; 
			ResultSet rs2; 
			ResultSet rs3; 
			String str1="";
			String str2="";
			String str3="";
			String coltype="";
			String mktable="";
			List myList = new ArrayList();
			List myList1 = new ArrayList();
			BufferedReader br=null;
			StringBuilder infunload = new StringBuilder(); 
			StringBuilder oractr = new StringBuilder(); 
			StringBuilder oraload = new StringBuilder(); 
			String infunloadstr="";
			String oractrstr="";
			String oraloadstr="";
			String[] aArray=new String[3];
		                 
		                        //递归调用
		                    	
		                    	 br = new BufferedReader(new FileReader("./data/all_user.txt"));
		                         String line = ""; 
		                         
		                        String values="";
		                        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		                        File f= new File("./data/oraclediff"+df.format(new Date())+".xls") ; 
		        				OutputStream os =null;
		        				os=new FileOutputStream(f) ;
		        				   WritableWorkbook workbook = Workbook.createWorkbook(os);
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

		        				     WritableSheet[] sheet=new WritableSheet[50];
		        				    		
		                        
		                       
			                      int sheetnum=0;
			                      int sheetrow=0;
		                         while ((line = br.readLine()) != null) { 
		                        	 sheetrow=0;
		                        	 sheet[sheetnum]= workbook.createSheet(line.trim(),sheetnum);
		                        	 
		                        	 sheet[sheetnum].setRowView(0, 400, false);
		        					 sheet[sheetnum].setColumnView(0,50);
		        					 sheet[sheetnum].setColumnView(1,50);
		        					 sheet[sheetnum].setColumnView(2,50);
		        			        Label biaotou1 = new Label(0,0,"表名",titleFormate);
		        			        Label biaotou2 = new Label(1,0,"字段名",titleFormate);
		        			        Label biaotou3 = new Label(2,0,"修改脚本",titleFormate);
		        			        sheet[sheetnum].addCell(biaotou1);
		        			        sheet[sheetnum].addCell(biaotou2);
		        			        sheet[sheetnum].addCell(biaotou3);
		                        	 PrintStream printStream = new PrintStream(new FileOutputStream("./data/"+line.trim()+".sql"));
		                               
		                        	 infunloadstr="";
		                        	 oractrstr="";
		                        	 myList.clear();
		                        	 str3="select TABLE_NAME from dba_tables b where  owner='"+line.trim().toUpperCase()+"'";
		                        	 rs3=conn3.executeQuery(str3);
		                        	 while (rs3.next())
		                        	 {
		                        str1="select TABLE_NAME,COLUMN_NAME,DATA_TYPE,DATA_LENGTH,CHAR_LENGTH  from  user_tab_cols where table_name='"+rs3.getString(1).toUpperCase()+"'";
		                        str2="select TABLE_NAME,COLUMN_NAME,DATA_TYPE,DATA_LENGTH,CHAR_LENGTH  from  user_tab_cols where  table_name='"+rs3.getString(1).toUpperCase()+"'";
		                        
		                        rs1=conn1.executeQuery(str1);
		                        rs2=conn2.executeQuery(str2);
		                        str2="";
		                        myList.clear();
		                        myList1.clear();
		                        while (rs2.next()) {
		                        	myList.add(rs2.getString(2).toUpperCase());
		                        	myList1.add(rs2.getString(2).toUpperCase()+rs2.getString(3).toUpperCase()+rs2.getString(5));
		                        	
		                        }
		                        while (rs1.next()) {
		                        	
		                        	
		                        	String type=rs1.getString(3).toUpperCase();
		                        	 
                                  if (!myList.isEmpty())
                                  {
                                	  if (myList.indexOf(rs1.getString(2).toUpperCase())==-1)
                                	  {
                                		                                 		  
                                		 
                                		  switch (type)
                                		  {
                                		  case "DATE":coltype="DATE";break;
                                		  case "INTEGER":coltype="INTEGER";break;
                                		  case "FLOAT":coltype="FLOAT";break;
                                		  case "NUMBER":coltype="NUMBER(22,8)";break;
                                		  case "VARCHAR2":coltype="VARCHAR2("+rs1.getInt(5)+")";break;
                                		  default:coltype=rs1.getString(3)+"("+rs1.getInt(5)+")";
                                		  }
                                		 
                                			  str2="alter table "+rs3.getString(1) +" add "+rs1.getString(2)+" "+coltype+";";
                                			
                                			  sheetrow++;
                                			  sheet[sheetnum].setRowView(sheetrow, 300, false);
                								Label data1 = new Label(0,sheetrow,rs1.getString(1),titleFormate1);
                								Label data2 = new Label(1,sheetrow,rs1.getString(2),titleFormate1);
                								Label data3= new Label(2,sheetrow,str2,titleFormate1);
                						        sheet[sheetnum].addCell(data1);
                						        sheet[sheetnum].addCell(data2);
                						        sheet[sheetnum].addCell(data3);
                						        
                                		//conn2.update(str2);
                                		  printStream.println(str2);
                                		System.out.println(str2);
                                	  } 
                                	  else if (myList1.indexOf(rs1.getString(2).toUpperCase()+rs1.getString(3).toUpperCase()+rs1.getString(5))==-1)
                                   		 
                            		  {
                                		  switch (type)
                                		  {
                                		  case "DATE":coltype="DATE";break;
                                		  case "INTEGER":coltype="INTEGER";break;
                                		  case "FLOAT":coltype="FLOAT";break;
                                		  case "NUMBER":coltype="NUMBER(22,8)";break;
                                		  case "VARCHAR2":coltype="VARCHAR2("+rs1.getInt(5)+")";break;
                                		  default:coltype=rs1.getString(3)+"("+rs1.getInt(5)+")";
                                		  }
                                		 
                            			  str2="alter table "+rs3.getString(1) +" modify ("+rs1.getString(2)+" "+coltype+");"; 
                            		  sheetrow++;
                        			  sheet[sheetnum].setRowView(sheetrow, 300, false);
        								Label data1 = new Label(0,sheetrow,rs1.getString(1),titleFormate1);
        								Label data2 = new Label(1,sheetrow,rs1.getString(2),titleFormate1);
        								Label data3= new Label(2,sheetrow,str2,titleFormate1);
        						        sheet[sheetnum].addCell(data1);
        						        sheet[sheetnum].addCell(data2);
        						        sheet[sheetnum].addCell(data3);
        						        printStream.println(str2);
                                		System.out.println(str2);
                            		  }
                                  }
                                
                                  else
                                  {
                                	  switch (type)
                            		  {
                            		  case "DATE":coltype="DATE";break;
                            		  case "INTEGER":coltype="INTEGER";break;
                            		  case "FLOAT":coltype="FLOAT";break;
                            		  case "NUMBER":coltype="NUMBER(22,8)";break;
                            		  case "VARCHAR2":coltype="VARCHAR2("+rs1.getInt(5)+")";break;
                            		  default:coltype=rs1.getString(3)+"("+rs1.getInt(5)+")";
                            		  }
                            	  str2=str2+rs1.getString(2)+" "+coltype+",";
                                  }
                                	  
                                	  
                                  }
                                   if (myList.isEmpty())
                                {
                                str2="create table "+rs3.getString(1) +" ( "+str2.substring(0,str2.length()-1)+");"; 
                                sheetrow++;
                  			  sheet[sheetnum].setRowView(sheetrow, 300, false);
  								Label data1 = new Label(0,sheetrow,rs3.getString(1),titleFormate1);
  								Label data2 = new Label(1,sheetrow,"表不存在",titleFormate1);
  								Label data3= new Label(2,sheetrow,str2,titleFormate1);
  						        sheet[sheetnum].addCell(data1);
  						        sheet[sheetnum].addCell(data2);
  						        sheet[sheetnum].addCell(data3);
                                printStream.println(str2);
                                System.out.println(str2);
                                //conn2.update(str2);
                                	
                                }
                              
		                         }
		                        	 sheetnum++;
		                        	  printStream.close();
		                         }
		                        
		                         workbook.write();
		         		        workbook.close();
		         		        os.close();
		                    conn1.close();
		                    conn2.close();
		                    conn3.close();
		}
			
		catch (Exception e) { 
			e.printStackTrace(); 
		}
		}

}
