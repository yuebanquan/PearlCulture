package cn.itcast.ssh.domain;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DaysServiceImlp{
	//主函数：用于测试   ${myDate1}
	public static void main(String[] args) throws Exception{
//		System.out.println("当前时间："+ getSystemDate());
//		System.out.println("今天是放养第 "+ daysBetween("2017-12-11 19:46:14","2017-12-12 00:05:00")+" 天");  //开始放养日期，今天日期~=定时器时间
//		System.out.println("今天是放养第 "+ daysBetween("2018-01-14 08:00:00","2018-04-13 08:00:00")+" 天");
		System.out.println(plusDay(39, "2018-7-17 20:46:14"));
//		System.out.println("放养时段："+ getCurrPeriodBreed(1,90));  
//		String cron = DaysServiceImlp.getCron(new Date());
//		System.out.println(cron);
		
		/*Date date = new Date();
		System.out.println(DaysServiceImlp.getCorn_1(date,"00 mm HH * * ?"));
		System.out.println(DaysServiceImlp.getCorn_1(date,"05 mm HH * * ?"));
		System.out.println(DaysServiceImlp.getCorn_1(date,"10 mm HH * * ?"));
		System.out.println(DaysServiceImlp.getCorn_1(date,"15 mm HH * * ?"));
		System.out.println(DaysServiceImlp.getCorn_1(date,"20 mm HH * * ?"));
		
		String time = "08:11";
		System.out.println(DaysServiceImlp.getCorn(time,"00 mm HH * * ?"));
		System.out.println(DaysServiceImlp.getCorn(time,"05 mm HH * * ?"));
		System.out.println(DaysServiceImlp.getCorn(time,"10 mm HH * * ?"));
		System.out.println(DaysServiceImlp.getCorn(time,"15 mm HH * * ?"));
		System.out.println(DaysServiceImlp.getCorn(time,"20 mm HH * * ?"));*/
	}
	
	/**static:静态函数在其他java中可直接用类DaysServiceImlp调用，不用实例化对象new DaysServiceImlp()*/
	//方法：获得当前系统时间，返回的是字符串类型的时间格式------获得开始放养或捕捞的日期
	public static String getSystemDate(){      //获得当前系统时间，返回的是字符串类型的时间格式      
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String SystemDate = df.format(date);  //日期类型：将Date转化为String  
		return SystemDate;
	}
	
	//方法：计算两个字符串类型的日期之间的天数-------获得放养或捕捞的天数（第几天）
	public static int daysBetween(String beginDate,String nowDate) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar1 = Calendar.getInstance(); //得到日历实例
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(df.parse(beginDate));    //日期类型：将String转化为Date
		calendar2.setTime(df.parse(nowDate));      //将这个日历设置成这个Date
		long date1 = calendar1.getTimeInMillis();  //将这个Date转化为长整型的秒级
		long date2 = calendar2.getTimeInMillis();
		int between_days = (int)((date2-date1)/(1000*3600*24));  //算出两时间的毫秒数之差大于一天的天数 
		calendar2.add(Calendar.DAY_OF_MONTH,-between_days);//使endCalendar减去这些天数，将问题转换为两时间的毫秒数之差不足一天的情况
		calendar2.add(Calendar.DAY_OF_MONTH,-1); //再使endCalendar减去1天 
		if(calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)) //比较两日期的DAY_OF_MONTH是否相等
			return between_days + 2;  //相等说明确实跨天了 1  
		else
			return between_days + 1;  //不相等说明确实未跨天 0
	}
		
	//方法：判断此刻属于哪个放养时段                                       放养天数                    (原始)放养期90天
	public static String getCurrPeriodBreed(int daysBreed,int totaldaysBreed) {
		String currPeriodBreed;
		if(1<=daysBreed && daysBreed<= (int)(totaldaysBreed/6)){       						//1-15天
			currPeriodBreed = "幼苗期";
		}else if((int)(totaldaysBreed/6)<daysBreed && daysBreed<=(int)(totaldaysBreed/3)){  //16-30天
			currPeriodBreed = "放养前期";	
		}else if((int)(totaldaysBreed/3)<daysBreed && daysBreed<=(int)(totaldaysBreed*2/3)){ //31-60天
			currPeriodBreed = "放养中期";	
		}else if((int)(totaldaysBreed*2/3)<daysBreed && daysBreed<=(int)(totaldaysBreed)){   //61-90天
			currPeriodBreed = "放养后期";	
		}else{   													 //当放养天数>标准放养期=90天时的情况  91-xx天
			currPeriodBreed = "捕捞期内";
		}
		return currPeriodBreed;
	}
	
	//方法：为指定日期加上天数后，计算其对应的日期-----显示设置完放养期时跳出的日期      
	public static String plusDay(int num, String newDate) throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = df.parse(newDate, new ParsePosition(0));  //日期类型：将String转化为Date，表示从第一个字符开始解析
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);	
		calendar.add(Calendar.DATE, num-1);     //num-1 为了保证养殖0天算第1天
		Date date1 = calendar.getTime();
		String endDate = df.format(date1);                    //日期类型：将Date转化为String
		return endDate;
	}

	//将时间(1.Date类型)用cron表达式输出     Date->String
	public static String getCorn_1(Date date,String dateFormat){
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		String cronStr = null;
		if(date != null){
			cronStr = df.format(date);   //日期类型：将Date转化为String
		}
		return cronStr;
	}
	//将时间(2.String类型)用cron表达式输出     String->Date->String
	public static String getCorn(String time,String dateFormat){  //日期，和cron表达式的格式
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date date = df.parse(time, new ParsePosition(0));   //日期类型：将String转化为Date，表示从第一个字符开始解析
		
		SimpleDateFormat df1 = new SimpleDateFormat(dateFormat);
		String cronStr = null;
		if(date != null){
			cronStr = df1.format(date);   //日期类型：将Date转化为String
		}
		return cronStr;
	}
}

/*	private String beginDateBreed;     //开始放养日期   
	private int daysBreed;             //放养天数(第几天)  
	private int standarddaysBreed;     //标准放养期（原始放养总天数）
	private int totaldaysBreed;        //总放养期（新增后的放养总天数）
	private String currPeriodBreed;    //当前放养时段 (只和原始的放养期比较)
	private String standarddaysFishing;//标准起捕日期
	private String againdaysFishing；      //再次放养后起捕日期
	
	private int breedShrimpTotalNumber;  //放养总数量(虾苗/虾)==未达标的虾总数量
	
	private String beginDateFishing;  //开始起捕日期      ~=放养期的最后一天
	private int daysFishing;          //捕捞天数(第几天)
	private int limiteddaysFishing;   //限定的捕捞期（捕捞总天数）
	private int surplusdaysFishing;   //剩余捕捞天数
	
	private int qualifiedShrimpNumber;   //达标数量
	private int qualifiedShrimpTotalNumber;//达标总产量数量
*/