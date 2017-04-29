package reports;

import java.util.HashMap;

import javax.naming.directory.InvalidAttributesException;

public class BlockContentType
{
	public static final int NONE = 0;
	public static final int DOCUMENTS = 1;
	public static final int COMPLEXES = 2;
	public static final int ASSEMBLIES = 3;
	public static final int DETAILS = 4;
	public static final int STANDARDS = 5;
	public static final int OTHERS = 6;
	public static final int MATERIALS = 7;
	public static final int KITS = 8;
	
	private static HashMap<Integer, String> titles = new HashMap<Integer, String>(10);
	
	static 
	{
		try
		{
			mapTypeToTitle(NONE, "Неопределённый тип раздела");
			mapTypeToTitle(DOCUMENTS, "Документация");
			mapTypeToTitle(COMPLEXES, "Комплексы");
			mapTypeToTitle(ASSEMBLIES, "Сборочные единицы");
			mapTypeToTitle(DETAILS, "Детали");
			mapTypeToTitle(STANDARDS, "Стандартные изделия");
			mapTypeToTitle(OTHERS, "Прочие изделия");
			mapTypeToTitle(MATERIALS, "Материалы");
			mapTypeToTitle(KITS, "Комплекты");
		} catch (InvalidAttributesException ex) {
			throw new RuntimeException("Duplicate block content type: " + ex.getMessage());
		}
	}
	
	public static String titleOf(int type)
	{
		if(titles.containsKey(type))
			return titles.get(type);
		return titles.get(NONE);
	}
	
	public static boolean existsBlockWithTitle(String title)
	{
		if(titles.containsValue(title))
			return true;
		return false;
	}
	
	private static void mapTypeToTitle(int type, String title) throws InvalidAttributesException
	{
		if (titles.put(type, title) != null) 
			throw new InvalidAttributesException(title + " с идентификатором " + type);
	}
}
