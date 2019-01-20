package app1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class makehtml {
	static String temp;
	
	public static void make() throws ParserConfigurationException, SAXException, IOException, ParseException {
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

				PrintStream printStream = new PrintStream(new FileOutputStream("./data/report"+df.format(new Date())+".html"));
				sb.append("<html>\n"); 
				sb.append("<head>\n"); 
				sb.append("<title>亿阳信通总部服务</title>\n"); 
				sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"); 
				sb.append("<style type=\"text/css\">\n");
				sb.append("body.awr {font:bold 10pt Arial,Helvetica,Geneva,sans-serif;color:black; background:White;}\n");
				sb.append("pre.awr  {font:8pt Courier;color:black; background:White;}\n");
				sb.append("h1.awr   {font:bold 20pt Arial,Helvetica,Geneva,sans-serif;color:#336699;background-color:White;border-bottom:1px solid #cccc99;margin-top:0pt; margin-bottom:0pt;padding:0px 0px 0px 0px;}\n");
				sb.append("h2.awr   {font:bold 16pt Arial,Helvetica,Geneva,sans-serif;color:#336699;background-color:White;margin-top:4pt; margin-bottom:0pt;}\n");
				sb.append("h3.awr {font:bold 12pt Arial,Helvetica,Geneva,sans-serif;color:#336699;background-color:White;margin-top:4pt; margin-bottom:0pt;}\n");
				sb.append("li.awr {font: 8pt Arial,Helvetica,Geneva,sans-serif; color:black; background:White;}\n");
				sb.append("th.awrnobg {font:bold 8pt Arial,Helvetica,Geneva,sans-serif; color:black; background:White;padding-left:4px; padding-right:4px;padding-bottom:2px}\n");
				sb.append("th.awrbg {font:bold 8pt Arial,Helvetica,Geneva,sans-serif; color:White; background:#0066CC;padding-left:4px; padding-right:4px;padding-bottom:2px}\n");
				sb.append("td.awrnc {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:White;vertical-align:top;}\n");
				sb.append("td.awrc    {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#FFFFCC; vertical-align:top;}\n");
				sb.append("td.awrc1    {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#FF0000; vertical-align:top;}\n");
				sb.append("td.awrc2    {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#FF9900; vertical-align:top;}\n");
				sb.append("td.awrc3    {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#FFFF00; vertical-align:top;}\n");
				sb.append("td.awrc4    {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#0000FF; vertical-align:top;}\n");
				
				sb.append("td.awrnclb {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:White;vertical-align:top;border-left: thin solid black;}\n");
				sb.append("td.awrncbb {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:White;vertical-align:top;border-left: thin solid black;border-right: thin solid black;}\n");
				sb.append("td.awrncrb {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:White;vertical-align:top;border-right: thin solid black;}\n");
				sb.append("td.awrcrb    {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#FFFFCC; vertical-align:top;border-right: thin solid black;}\n");
				sb.append("td.awrclb    {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#FFFFCC; vertical-align:top;border-left: thin solid black;}\n");
				sb.append("td.awrcbb    {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#FFFFCC; vertical-align:top;border-left: thin solid black;border-right: thin solid black;}\n");
				sb.append("a.awr {font:bold 8pt Arial,Helvetica,sans-serif;color:#663300; vertical-align:top;margin-top:0pt; margin-bottom:0pt;}\n");
				sb.append("td.awrnct {font:8pt Arial,Helvetica,Geneva,sans-serif;border-top: thin solid black;color:black;background:White;vertical-align:top;}\n");
				sb.append("td.awrct   {font:8pt Arial,Helvetica,Geneva,sans-serif;border-top: thin solid black;color:black;background:#FFFFCC; vertical-align:top;}");
				sb.append("td.awrnclbt  {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:White;vertical-align:top;border-top: thin solid black;border-left: thin solid black;}\n");
				sb.append("td.awrncbbt  {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:White;vertical-align:top;border-left: thin solid black;border-right: thin solid black;border-top: thin solid black;}\n");
				sb.append("td.awrncrbt {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:White;vertical-align:top;border-top: thin solid black;border-right: thin solid black;}\n");
				sb.append("td.awrcrbt     {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#FFFFCC; vertical-align:top;border-top: thin solid black;border-right: thin solid black;}\n");
				sb.append("td.awrclbt     {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#FFFFCC; vertical-align:top;border-top: thin solid black;border-left: thin solid black;}\n");
				sb.append("td.awrcbbt   {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#FFFFCC; vertical-align:top;border-top: thin solid black;border-left: thin solid black;border-right: thin solid black;}\n");
				sb.append("table.tdiff {  border_collapse: collapse; }\n");
				sb.append(".hidden   {position:absolute;left:-10000px;top:auto;width:1px;height:1px;overflow:hidden;}\n");
				sb.append(".pad   {margin-left:17px;}\n");
				sb.append(".doublepad {margin-left:34px;}\n");
				sb.append("</style>\n");
				sb.append("<script type=\"text/javascript\" src=\"sorttable.js\"></script>\n");
				

			  // 下面开始读取
			  
			   Element root = doc1.getDocumentElement(); // 获取根元素
			   NodeList db = root.getElementsByTagName("sqlcfg");
			   NodeList colum=null;
			   NodeList text=null;
			   Element sss=null;
				sb.append("<h1 class=\"awr\">"+ root.getElementsByTagName("title").item(0).getTextContent()+"</h1>");
				sb.append("<ul>");
				sb.append("<ul>");
				sb.append("</ul>");
				sb.append("</ul>");
			   for(int j=0;j<db.getLength();j++)
			   {
				 
				
			    Element ss=(Element) db.item(j);
			    colum=ss.getElementsByTagName("colum");
			    text=ss.getElementsByTagName("text");
			    String cla="";
			    	System.out.print(ss.getAttribute("dbname"));
			    	 
			    	app1.condb  conn1=new app1.condb(ss.getAttribute("dbname"));
			    				  
			    	
			    	ResultSet newrs; 
			    	System.out.print(ss.getElementsByTagName("sql").item(0).getTextContent()); 
				newrs = conn1.executeQuery(ss.getElementsByTagName("sql").item(0).getTextContent()); 
				ResultSetMetaData rsmd = newrs.getMetaData() ;    
				int columnCount = rsmd.getColumnCount(); 
				sb.append("<p>");
				sb.append("<p>");
				sb.append("</p>");
				sb.append("</p>");
				sb.append("<h3 class=\"awr\">"+ss.getAttribute("sqlname")+"</h3>");
				sb.append("<ul>");

				for(int x=0;x<text.getLength();x++)
				   {
					 
				     sb.append("<li class=\"awr\"> "+text.item(x).getTextContent()+"</li>");
     
				   }
				sb.append("</ul>");
				sb.append("<table class=\"sortable\" border=\"1\" width=\"600\">");  
				sb.append("<tr>"); 
				for(int i = 1;i<=rsmd.getColumnCount();i++){
					sb.append("<th class=\"awrbg\" scope=\"col\">");
					sb.append(rsmd.getColumnName(i)); 
					sb.append("</th>"); 
				}
				sb.append("</tr>"); 
			
				try { 
					int  m=0;
					while (newrs.next()) { 
						sb.append("<tr>"); 
						
						for(int i = 1;i<=rsmd.getColumnCount();i++){
							
							String v=newrs.getString(i);
							

							if (m%2==1)
							{
								cla="awrnc";
							}
							else
							{
								cla="awrc";
							}
							
							    
							
							 for(int z=0;z<colum.getLength();z++)
							   {
								 
								
							     sss=(Element) colum.item(z);
							    
							    if (sss.getAttribute("num").equals(String.valueOf(i)))
							    {
							    	if ((sss.getAttribute("min").equals("#") && Float.parseFloat(v)>Float.parseFloat(sss.getAttribute("max")))  || (sss.getAttribute("max").equals("#") && Float.parseFloat(v)<Float.parseFloat(sss.getAttribute("min"))) || (!sss.getAttribute("min").equals("%") && !sss.getAttribute("min").equals("#") && !sss.getAttribute("max").equals("#") && Float.parseFloat(v)<Float.parseFloat(sss.getAttribute("min")) && Float.parseFloat(v)>Float.parseFloat(sss.getAttribute("max"))) ||(sss.getAttribute("min").equals("%") && !v.contains(sss.getAttribute("max"))) )
							    	{
							    	
							    		cla="awrc"+sss.getTextContent();
							    		
							    	}
							    	
							    }
							    
							    
							 
							
							   }
							 sb.append("<td scope=\"row\" class=\""+cla+"\">");	
							 cla="";
							 sb.append(newrs.getString(i)); 
							sb.append("</td>"); 
							//System.out.print(rsmd.getColumnName(i)+"=" +newrs.getString(i)+"\n");
							
						}
						  sb.append("</tr>");  
						  m++;
						}
					
					sb.append("</table>");  

				
				
				} catch (Exception e) { 
					temp=e.getMessage(); 
					return;
					
					} 
				System.out.println(temp);
				conn1.close(); 
					
			   
			   }
				sb.append("</div></body></html>"); 
				printStream.println(sb.toString()); 
			
         }
catch (SQLException e) { 
	temp=e.getMessage(); 
	
}
finally{
	System.out.println(temp);
}
		}
	//创建连接connection

}
