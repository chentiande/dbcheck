package app1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class infora {
	public static void make(String dbname1,String dbname2) throws ParserConfigurationException, SAXException, IOException, RowsExceededException, WriteException {
		try
		{
			app1.condb  conn1=new app1.condb(dbname1);
			app1.condb  conn2=new app1.condb(dbname2);
			ResultSet rs1; 
			ResultSet rs2; 
			String str1="";
			String str2="";
			String coltype="";
			String mktable="";
			List myList = new ArrayList();
			BufferedReader br=null;
			StringBuilder infunload = new StringBuilder(); 
			StringBuilder oractr = new StringBuilder(); 
			StringBuilder oraload = new StringBuilder(); 
			String infunloadstr="";
			String oractrstr="";
			String oraloadstr="";
		                 
		                        //递归调用
		                    	
		                    	 br = new BufferedReader(new FileReader("./data/all_table.txt"));
		                         String line = ""; 
		                         
		                        String values="";
		                         while ((line = br.readLine()) != null) { 
		                        	 infunloadstr="";
		                        	 oractrstr="";
		                        	 myList.clear();
		                        str1="select tabname,colname,coltype,collength   from syscolumns a,systables b where a.tabid=b.tabid and tabname=\""+line.trim().toLowerCase()+"\"";
		                        str2="select TABLE_NAME,COLUMN_NAME,DATA_TYPE,DATA_LENGTH  from  user_tab_cols where table_name='"+line.trim().toUpperCase()+"'";
		                        
		                        rs1=conn1.executeQuery(str1);
		                        rs2=conn2.executeQuery(str2);
		                        str2="";
		                        while (rs2.next()) {
		                        	myList.add(rs2.getString(2).toUpperCase());
		                        }
		                        while (rs1.next()) {
		                        	
		                        	infunloadstr=infunloadstr+rs1.getString(2)+",";
		                        	int type=rs1.getInt(3);
		                        	  if (type>256)
                            		  {type=type-256;}
                            		  switch (type)
                            		  {
                            		  case 7:oractrstr=oractrstr+rs1.getString(2)+" date \"yyyy-mm-dd\",";break;
                            		  case 10:oractrstr=oractrstr+rs1.getString(2)+" date \"yyyy-mm-dd hh24:mi:ss\",";break;
                            		  default:oractrstr=oractrstr+rs1.getString(2)+",";;
		                        	
                            		  }
                                  if (!myList.isEmpty())
                                  {
                                	  if (myList.indexOf(rs1.getString(2).toUpperCase())==-1)
                                	  {
                                		                                 		  
                                		  if (type>256)
                                		  {type=type-256;}
                                		  switch (type)
                                		  {
                                		  case 0:coltype="varchar2(255)";break;
                                		  case 1:coltype="INTEGER";break;
                                		  case 2:coltype="INTEGER";break;
                                		  case 3:coltype="FLOAT";break;
                                		  case 4:coltype="FLOAT";break;
                                		  case 5:coltype="FLOAT";break;
                                		  case 6:coltype="INTEGER";break;
                                		  case 7:coltype="DATE";break;
                                		  case 8:coltype="FLOAT";break;
                                		  case 9:coltype="varchar2(255)";break;
                                		  case 10:coltype="DATE";break;
                                		  default:coltype="varchar2("+rs1.getInt(4)+")";
                                		  }
                                		  str2="alter table "+line.trim() +" add "+rs1.getString(2)+" "+coltype;
                                		conn2.update(str2);
                                		System.out.println(str2);
                                	  }
                                  }
                                  else
                                  {
                                	     if (type>256)
                            		  {type=type-256;}
                            		  switch (type)
                            		  {
                            		  case 0:coltype="varchar2(255)";break;
                            		  case 1:coltype="INTEGER";break;
                            		  case 2:coltype="INTEGER";break;
                            		  case 3:coltype="FLOAT";break;
                            		  case 4:coltype="FLOAT";break;
                            		  case 5:coltype="FLOAT";break;
                            		  case 6:coltype="INTEGER";break;
                            		  case 7:coltype="DATE";break;
                            		  case 8:coltype="FLOAT";break;
                            		  case 9:coltype="varchar2(255)";break;
                            		  case 10:coltype="DATE";break;
                            		  default:coltype="varchar2("+rs1.getInt(4)+")";
                            		  }
                            		   
                                	  str2=str2+rs1.getString(2)+" "+coltype+",";
                                	  
                                	  
                                	  
                                  }
                                 
		                        }
                                infunload.append("unload to "+line.trim()+".unl select "+infunloadstr.substring(0,infunloadstr.length()-1)+" from "+line.trim()+";\n");
                                oractr.append("load   data   infile   '"+line.trim()+".unl'  append   into   table "+line.trim()+" fields terminated by '|' ("+oractrstr.substring(0,oractrstr.length()-1)+")");
                                oraload.append("sqlldr username/password control="+line.trim()+".ctl;\n");
                                
                                PrintStream printStream = new PrintStream(new FileOutputStream("./data/"+line.trim()+".ctl"));
                                printStream.println(oractr.toString());
                                oractr.delete(0, oractr.length());
                                printStream.close();
		                        if (myList.isEmpty())
                                {
                                str2="create table "+line.trim() +" ( "+str2.substring(0,str2.length()-1)+")"; 
                                System.out.println(str2);
                                conn2.update(str2);
                                	
                                }
		                       
		                         }
		    
		                         PrintStream printStream1 = new PrintStream(new FileOutputStream("./data/inf_unload.sql"));
		                         PrintStream printStream2 = new PrintStream(new FileOutputStream("./data/ora_load.sh"));
		                         printStream1.println(infunload.toString()); 
		                         printStream2.println(oraload.toString()); 
		                         
		                         printStream1.close();
		                         printStream2.close();
		                    conn1.close();
		                    conn2.close();
		  
		}
			
		catch (Exception e) { 
			e.printStackTrace(); 
		}
		}

}
