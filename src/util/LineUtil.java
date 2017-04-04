package util;

import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

public class LineUtil
{
	static final Font		font				= new Font("Arial", Font.PLAIN, 14);
	static final String[]	emptyValues			= {};
	static String[]			nonbreakableWords	= Specification.settings.getNonbreakableWords();	// "ГОСТ>", "ОСТ>", "ISO>"
	static String[]			nonbreakablePlaneArray;

	static
	{
		nonbreakablePlaneArray = nonbreakableWords.clone();
		for (int i = 0; i < nonbreakablePlaneArray.length; i++)
		{
			nonbreakablePlaneArray[i] = nonbreakablePlaneArray[i].replaceAll("[<>]", "");
		}
	}

	public LineUtil()
	{

	}
	
	public void loadNonbreakableWords()
	{
		
	}
	
	static public ArrayList<String> getFittedLines(String input, double maxWidth)
	{
		String result = "";
		while (true)
		{
			int endPositionForFittedLine = getEndPositionForFittedLine(input, maxWidth);
			String fittedLine = input.substring(0, endPositionForFittedLine);
			result = result + fittedLine.trim() + "\n";
			if (fittedLine.equals(input))
				break;
			input = input.substring(endPositionForFittedLine);
		}
		return new ArrayList<String>(Arrays.asList(result.split("\n")));
	}

	private static int getEndPositionForFittedLine(String inLine, double maxWidth)
	{
		int position = 0;

		if (((position = inLine.indexOf("\n")) > 0) && (getWidthOfLine(inLine.substring(0, position)) < maxWidth))
		{
			return position;
		}
		if (getWidthOfLine(inLine) < maxWidth)
		{
			return inLine.length();
		}
		else
		{
			String stringForOut = "";
			int i = 0;
			String[] wordsInLine = inLine.split(" ");
			String[] connectedWords = connectNonBreakableWords(wordsInLine);

			while (getWidthOfLine(stringForOut) < maxWidth)
			{
				stringForOut = stringForOut + " " + connectedWords[i];
				i++;
			}
			stringForOut = "";
			if (i == 1)
			{
				stringForOut = connectedWords[0];
				while (getWidthOfLine(stringForOut) > maxWidth)
				{
					stringForOut = stringForOut.substring(0, stringForOut.length() - 1);
				}
			}
			else
			{
				for (int j = 0; j < i - 1; j++)
				{
					if (j > 0)
						stringForOut = stringForOut + " " + connectedWords[j];
					else
						stringForOut = connectedWords[j];
				}
			}
			return stringForOut.length();
		}
	}

	private static int getWidthOfLine(String measuredLine)
	{
		Rectangle2D areaName = font.getStringBounds(measuredLine, new FontRenderContext(null,
				RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));
		return (((Double) areaName.getWidth()).intValue());
	}

	private static String[] connectNonBreakableWords(String[] inStrArray)
	{
		ArrayList<String> list4Out = new ArrayList<String>();
		String gotSymbols = "";
		String gotPreviousSymbols = "";

		for (String inStr : inStrArray)
		{

			gotSymbols = getNonBreakableSymbols(inStr);
			if (gotSymbols.equals("<") || gotPreviousSymbols.equals(">") || gotSymbols.equals("<>")
					|| gotSymbols.equals("><") || gotPreviousSymbols.equals("<>") || gotPreviousSymbols.equals("><"))
			{

				if (!list4Out.isEmpty())
				{
					list4Out.set(list4Out.size() - 1, list4Out.get(list4Out.size() - 1) + " " + inStr);
				}
				else
				{
					list4Out.add(inStr);
				}

			}
			else
			{
				list4Out.add(inStr);
			}
			gotPreviousSymbols = gotSymbols;
		}
		return list4Out.toArray(new String[list4Out.size()]);
	}

	private static String getNonBreakableSymbols(String inStr)
	{
		int index = -1;
		if ((index = (Arrays.asList(nonbreakablePlaneArray)).indexOf(inStr)) >= 0)
		{
			return nonbreakableWords[index].replaceAll("[^<>]", "");
		}
		else
			return "";
	}
}
