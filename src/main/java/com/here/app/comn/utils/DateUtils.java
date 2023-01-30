package com.here.app.comn.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

public class DateUtils {

	public static String getCurrentTime(){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
    }
	
	public static String stringToFormat(String str) {
		Date date = stringToDatetime(str);
		
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	
	public static Date stringToDatetime(String date) {
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date result = null;
		try {
			if(date.length() == 14) {
				result = transFormat.parse(date);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
				
		return result;
	}
	
	public static Date stringToDate(String date) {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyyMMdd");
		Date result = null;
		try {
			if(date.length() == 10) {
				result = transFormat.parse(date);
			}else if(date.length() == 8) {
				result = transFormat2.parse(date);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
				
		return result;
	}
	
	public static LocalDateTime stringToLocalDateTime(String date) {
		
		DateTimeFormatter transFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime localDateTime = LocalDateTime.parse(date , transFormat);
		
		return localDateTime;
		
	}
	

	
	public static LocalDateTime stringToLocalDate(String date) {
		DateTimeFormatter transFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		DateTimeFormatter DATEFORMATTER = new DateTimeFormatterBuilder().append(transFormat)
			    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
			    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
			    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
			    .toFormatter();
		
				
		LocalDateTime localDateTime = LocalDateTime.parse(date , DATEFORMATTER);
		
		return localDateTime;
	}
	
	
	public static Date nowDate() {
		return new Date(System.currentTimeMillis());
	}
	
	public static long diffSecond(Date firstDate , Date secondDate) {
	
		long diffDate = secondDate.getTime() - firstDate.getTime(); 
		long diffTime = diffDate / (1000);
		
		return diffTime;
		
	}
	
	public static long diffMinute(Date firstDate , Date secondDate) {
		
		long diffDate = secondDate.getTime() - firstDate.getTime(); 
		long diffTime = diffDate / (1000 * 60);
		
		return diffTime;
		
	}
	
	
	
//	public static void main(String[]  args) {
//		
//		Date firstDate = stringToDate("20210630142014");
//		
//		Date secondDate = stringToDate("20210630142914");
//		
//		LocalDateTime  dateTime = stringToLocalDate("2021-07-20");
//		
//		System.out.println(">>>>" + dateTime);
//		
//		
//	    System.out.println("두 날짜의 차이 분: "+diffMinute(firstDate , secondDate));
//	    
//	    System.out.println(">>>" + stringToFormat("20210630142014"));
//
//		
//		
//			
//		
////		List<Map<String, Double>> list = new ArrayList<Map<String, Double>>();
////		
////		
////		for( double i= 0 ; i  < 1000 ; i ++) {
////			Map<String, Double> position = new HashMap<String, Double>();
////			position.put("lat", 11111.0);
////			position.put("lng", 11111.0);
////			position.put("date", i);
////			list.add(position);
////		}
////		
////		int limitSize = 1000;
////		if(list.size() < 1000) {
////			limitSize = list.size();
////		}
////		
////		List<Map<String, Double>> lastThreeThings = list.subList(list.size() - limitSize, list.size());
////		
////		//뒤집기
////		Collections.reverse(lastThreeThings);
////		
////		for(Map<String , Double> data : lastThreeThings) {
////			 
////			 
////			System.out.println(data);
////		}
//	
//	}
}
