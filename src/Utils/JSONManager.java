// By Iacon1
// Created 04/22/2021
// Handles turning things into XML files and back for storage & transport

package Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Utils.GSONConfig.GiveBuilder;

public final class JSONManager
{
	private static Gson gson; // GSON system
	private static boolean setup; // Is set up?
	
	private static void setupIfNot() // Sets up if not already
	{
		if (!setup)
		{
			GsonBuilder builder = GiveBuilder.giveBuilder();
			gson = builder.create();
			setup = true;
		}
	}
	
	public static <C> String serializeJSON(C unserialized) // Serializes
	{
		setupIfNot();
		try {return gson.toJson(unserialized);}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	public static <C> String serializeJSON(C unserialized, Class<C> classFrom) // Serializes
	{
		setupIfNot();
		try {return gson.toJson(unserialized, classFrom);}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	public static <C> C deserializeJSON(String serialized, Class<C> classTo) // Unserializes
	{
		setupIfNot();
		//Logging.logNotice(serialized);
		try {return gson.fromJson(serialized, classTo);}
		catch (Exception e) {Logging.logException(e); return null;}
	}

	public static <C> C deserializeCollectionJSON(String serialized, java.lang.reflect.Type typeTo) // Unserializes a collection
	{
		setupIfNot();
		try {return gson.fromJson(serialized, typeTo);}
		catch (Exception e) {Logging.logException(e); return null;}
	}
	
	public static <C> C deserializeCollectionJSONList(String serialized, Class mainClass, Class... classArgs) // Unserializes a parameterized collection
	{
		java.lang.reflect.Type typeTo = TypeToken.getParameterized(mainClass, classArgs).getType();
	
		return deserializeCollectionJSON(serialized, typeTo);
	}
}
