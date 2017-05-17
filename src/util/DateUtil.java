package util;

import java.util.Date;

public class DateUtil
{
	static public enum Month {
		JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC, ���, ���, ���, ���, ���, ���, ���, ���, ���, ���, ���, ���
	}

	public static String parseDateFromTC(String tcDate)
	{
		String out = ""; // was " ", not ""
		if (tcDate.trim().length() > 0) {
			String stDate = tcDate.substring(0, 2);
			out = stDate;

			String stMo = tcDate.substring(3, 6).toUpperCase();

			Month mo = Month.valueOf(stMo);
			switch (mo) {
			case ���:
			case JAN:
				out += ".01.";
				break;
			case ���:
			case FEB:
				out += ".02.";
				break;
			case ���:
			case MAR:
				out += ".03.";
				break;
			case ���:
			case APR:
				out += ".04.";
				break;
			case ���:
			case MAY:
				out += ".05.";
				break;
			case ���:
			case JUN:
				out += ".06.";
				break;
			case ���:
			case JUL:
				out += ".07.";
				break;
			case ���:
			case AUG:
				out += ".08.";
				break;
			case ���:
			case SEP:
				out += ".09.";
				break;
			case ���:
			case OCT:
				out += ".10.";
				break;
			case ���:
			case NOV:
				out += ".11.";
				break;
			case ���:
			case DEC:
				out += ".12.";
				break;
			}
			String year = tcDate.substring(9, 11);
			out += year;
		}
		return out;
	}

	public static String parseDateToTC(String date)
	{
		//Date:DateTime {7/14/2016}
		String result = "";
		String day = date.substring(date.indexOf("{"), date.indexOf("/"));
		result+=day;
		int month = Integer.parseInt(date.substring(date.indexOf("/"),date.lastIndexOf("/")));
		result+=month;
		String year = date.substring(date.lastIndexOf("/"));
		result+=year;

		return result;
	}

	public static Date getDateFormSimpleString(String s_date)
	{
		int year = Integer.parseInt(s_date.substring(s_date.lastIndexOf(".")+1, s_date.length()))+100;
		int month = Integer.parseInt(s_date.substring(s_date.indexOf(".")+1, s_date.lastIndexOf(".")));
		int date = Integer.parseInt(s_date.substring(0, s_date.indexOf(".")));
		return new Date(year, month, date);
	}
}
