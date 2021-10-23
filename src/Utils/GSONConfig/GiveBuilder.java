// By Iacon1
// Created 05/06/2021
//

package Utils.GSONConfig;

import com.google.gson.GsonBuilder;

public final class GiveBuilder
{
	public static GsonBuilder giveBuilder()
	{
		GsonBuilder builder = new GsonBuilder();
		
		builder.enableComplexMapKeySerialization();
		
		return builder;
	}
}
