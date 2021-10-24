// By Iacon1
// Created 04/22/2021
// Just some misc. util functions

package Utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.math.RoundingMode;

public final class MiscUtils
{
	private static <C> void addItemsToFit(ArrayList<C> array, int newSize)
	{
		while (array.size() < newSize)
			array.add(null);
	}
	
	public static <C> ArrayList<C> resizeArrayList(ArrayList<C> array, int newSize) // Makes a resized version of the array
	{
		ArrayList<C> newArray = new ArrayList<C>();
		
		addItemsToFit(newArray, newSize);
		
		for (int i = 0; i < Math.min(newSize, array.size()); ++i)
			newArray.set(i, array.get(i));
		
		newArray.trimToSize(); // Cuts down to min(newSize, array.size()), then...
		addItemsToFit(newArray, newSize); // Makes sure to lengthen back out if array.size() was smaller than newSize
		
		return newArray;
	}
	
	public static ArrayList<Image> getIcons() // Returns all versions of the icon for either client or server
	{
		ArrayList<Image> icons = new ArrayList<Image>();
		String pathPrefix;
		pathPrefix = "Resources/Icons/";
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon16.PNG")));
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon32.PNG")));
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon64.PNG")));
		icons.add(Toolkit.getDefaultToolkit().getImage(MiscUtils.getAbsolute(pathPrefix + "Icon128.PNG")));
		
		return icons;
	}
	public static String getVersion() // Gets version
	{
		return "V0.X";
	}
	public static String getProgramName() // What is this program (inc. version)?
	{
		return "SNES Configuration Tool " + getVersion();
	}
	public static String getAbsolute(String name)
	{
		return new File(name).getAbsolutePath();
	}
	
	public static void saveText(String path, String text) // Saves text to file
	{
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(getAbsolute(path), false);
			writer.write(text);
		}
		catch (Exception e) {Logging.logException(e);}
		finally
		{
			if (writer != null)
			{
				try {writer.close();}
				catch (Exception e) {Logging.logException(e);}
			}
		}
	}
	public static String readText(String path) // Reads text from file
	
	{
		Scanner scanner = null;
		String text = "";
		try
		{
			try
			{
				File fileObj = new File(getAbsolute(path));
				
				scanner = new Scanner(fileObj);

				while (scanner.hasNextLine()) text = text + scanner.nextLine();
			}
			catch (Exception e) {Logging.logException(e); return null;}
			finally {scanner.close();}
			
			return text;
		}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	
	public static <T> String arrayToString(T[] array, String sep)
	{
		String string = new String();
		for (int i = 0; i < array.length; ++i) string = string + array[i].toString() + sep;
		return string;
	}
	
	public static <T> String arrayToString(ArrayList<T> array, String sep)
	{
		return arrayToString((T[]) array.toArray(), sep);
	}
	
	public static <T> String ClassToString(Class<T> sClass)
	{
		return sClass.getName();
	}
	
	public static int multiMax(int x, int... X)
	{
		int y;
		if (X.length > 1)
		{
			int[] sX = Arrays.copyOfRange(X, 1, X.length);
			y = multiMax(X[0], sX);
		}
		else y = X[0];
		
		return Math.max(x, y);
	}
	
	public static String asHex(byte number, int digits)
	{
		return String.format("%0" + digits + "x", number);
	}
	
	public static String asHex(byte number)
	{
		return asHex(number, 2);
	}
	
	public static double logBase(double a, double b)
	{
		return Math.log(a) / Math.log(b);
	}
}
