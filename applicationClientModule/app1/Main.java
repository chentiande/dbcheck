package app1;

import java.net.InetAddress;
import java.util.Date;
import java.text.SimpleDateFormat;





public class Main {

	public static void main(String[] args)  {
		try
		{ 
			


			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			Date date = df.parse( "2025-1-25 19:20:00" );
			
			
      if (date.before(new Date()))
      {
    		System.out.println("程序已过期，请联系开发人员");
    		System.exit(0); 
      }
        if (args[1].equals(""))
        {
        	System.out.println("");
        }
			
			if (args[1].equals("makefile"))
		{
			if (args[0].equals("html"))
				{
				
				makehtml.make();
				
				}
			else if (args[0].equals("xls"))
			{
				makexls.make();
			}
			
			else if (args[0].equals("csv"))
			{
				makecsv.make();
			}
		}
		else if (args[1].equals("filetodb"))
		{
			if (args[0].equals("xls"))
			{
				xlstodb.make(args[2].toString());
			}
			
			else if (args[0].equals("csv"))
			{
				csvtodb.make(args[2].toString());
			}
         }
		else if (args[1].equals("dbmove"))
		{
			if (args[0].equals("infora"))
			{
				infora.make(args[2].toString(),args[3].toString());
			}
			
			else if (args[0].equals("diffora"))
			{
				diffora.make(args[2].toString(),args[3].toString());
			}
         }
        
		}
catch (Exception e) { 
	e.printStackTrace(); 
}
		}
	//创建连接connection
	

}