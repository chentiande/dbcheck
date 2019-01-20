package app1;

	import java.sql.*; 

	import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;





	import org.w3c.dom.Document;
import org.w3c.dom.Element;
	
import org.w3c.dom.NodeList;
	 

	public class condb { 
		
		
	
		String dbip = ""; 
		String dbport = "";
		String dbname = "";
		String dbuser = ""; 
		String dbpasswd = ""; 
		String dbserver = ""; 
		String temp ="";

		//������ݿ���� 
		Connection c = null; 
		Statement conn; 
		ResultSet rs = null; 
		//��ʼ������ 
		public condb(String dbnm) { 
		try { 
		
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		   DocumentBuilder builder = dbf.newDocumentBuilder();
		   Document doc = builder.parse("./cfg/conf.xml"); // ��ȡ��xml�ļ�

		  // ���濪ʼ��ȡ
		  
		   Element root = doc.getDocumentElement(); // ��ȡ��Ԫ��
		   NodeList db = root.getElementsByTagName("dbcfg");
		   Element ss;
		   for(int i=0;i<db.getLength();i++)
		   {
			    ss = (Element) db.item(i);
			   
			 if (ss.getAttribute("dbname").equals(dbnm))
			 {
		      

		
		     dbip = ss.getElementsByTagName("dbip").item(0).getFirstChild().getNodeValue();
		     dbserver =ss.getElementsByTagName("dbserver").item(0).getFirstChild().getNodeValue();
			 dbport =ss.getElementsByTagName("dbport").item(0).getFirstChild().getNodeValue();
			 dbname =ss.getElementsByTagName("dbname").item(0).getFirstChild().getNodeValue();
			 dbuser =ss.getElementsByTagName("dbuser").item(0).getFirstChild().getNodeValue(); 
			 dbpasswd =ss.getElementsByTagName("dbpasswd").item(0).getFirstChild().getNodeValue();

		  

		//��urlָ�������Դ�������� 
			 if(ss.getAttribute("dbtype").equals("1"))
			 {
				 Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		c = DriverManager.getConnection("jdbc:oracle:thin:@"+dbip+":"+dbport+":"+dbname, dbuser, dbpasswd); 
			 }
			 else if(ss.getAttribute("dbtype").equals("0"))
			 {
				 Class.forName("com.informix.jdbc.IfxDriver").newInstance();
		c = DriverManager.getConnection( "jdbc:informix-sqli://"+dbip+":"+dbport+"/"+dbname+":INFORMIXSERVER="+dbserver+";NEWCODESET=utf8,8859-1,819;CLIENT_LOCALE=en_US.utf8;DB_LOCALE=en_US.8859-1;user="+dbuser+";password="+dbpasswd); 
			 }
			 else if(ss.getAttribute("dbtype").equals("2"))
			 {
				 Class.forName("com.mysql.jdbc.Driver").newInstance();
		c = DriverManager.getConnection( "jdbc:mysql://"+dbip+":"+dbport+"/"+dbname+"?user="+dbuser+"&password="+dbpasswd+"&useUnicode=true&characterEncoding=UTF8"); 
			 }
			 else if(ss.getAttribute("dbtype").equals("3"))
			 {
				 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		c = DriverManager.getConnection( "jdbc:sqlserver://"+dbip+":"+dbport+";DatabaseName="+dbname, dbuser, dbpasswd); 
			 }
			 else if(ss.getAttribute("dbtype").equals("4"))
			 {
				 Class.forName("org.postgresql.Driver").newInstance();
				
		c = DriverManager.getConnection( "jdbc:postgresql://"+dbip+":"+dbport+"/"+dbname, dbuser, dbpasswd); 
			 }
			 else if(ss.getAttribute("dbtype").equals("5"))
			 {
				 Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
				
		c = DriverManager.getConnection( "jdbc:db2://"+dbip+":"+dbport+"/"+dbname, dbuser, dbpasswd); 
			 }
			 
		//����Statement���в�ѯ 
		conn = c.createStatement(); 
		}
		   }
		} catch (Exception e) { 
		e.printStackTrace(); 
		} 
		} 
		//ִ�в�ѯ 
		public ResultSet executeQuery(String sql) throws SQLException { 
			rs = null; 
			try { 
				rs = conn.executeQuery(sql); 
			} catch (Exception e) { 
				temp=e.getMessage(); 
				
			} finally{
				 System.out.println(temp);
					} 
			return rs; 
		}
		//ִ���޸�
		public void update(String sql) throws SQLException{
			int len = 0; 
			try { 
			 len = conn.executeUpdate(sql); 
			 if(len>=0){
				 System.out.println("执行成功");
			 }else{
				 System.out.println("执行失败");
			 }
			} catch (SQLException e) { 
				System.out.println(sql);
				e.printStackTrace();
				
			}finally{
			   
			} 
			
			} 
		
		//ִ�����
		public void Add(String sql) throws SQLException{
			boolean bool = false; 
			try { 
			 bool = conn.execute(sql); 
			 if(bool){
				 System.out.println("执行成功");
			 }else{
				 System.out.println("执行失败");
			 }
			} catch (SQLException e) { 
				temp=e.getMessage(); 
			}finally{
				
			} 
			
			} 
			
		//ִ��ɾ��
		public void Delet(String sql) throws SQLException{
			boolean bool = false; 
			try { 
			 bool = conn.execute(sql); 
			 if(bool){
				 System.out.println("执行成功");
			 }else{
				 System.out.println("执行失败");
			 }
			} catch (SQLException e) { 
				e.printStackTrace(); 
			}finally{
			
			} 
			
			} 

		public void close() { 
			try { 
				conn.close(); 
				c.close(); 
			} catch (Exception e) { 
				e.printStackTrace(); 
			} 
		} 
		public static void main(String[] args) throws SQLException { 
			ResultSet newrs; 
			condb newjdbc = new condb("xxx"); 
		newrs = newjdbc.executeQuery("select * from users"); 
		try { 
		while (newrs.next()) { 
		System.out.print(newrs.getString("USERID")); 
		System.out.print(":"+newrs.getString("USERNAME")); 
		System.out.print(":"+newrs.getString("PASSWB"));
		System.out.print(":"+newrs.getString("EMAIL"));
		System.out.println(":"+newrs.getString("GRADE"));
		} 
		} catch (Exception e) { 
		e.printStackTrace(); 
		} 
		newjdbc.close(); 
		} 
		}
