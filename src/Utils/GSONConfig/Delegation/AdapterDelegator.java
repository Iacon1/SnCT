// By Iacon1
// Created 09/02/2021
//

package Utils.GSONConfig.Delegation;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import Utils.Logging;

public class AdapterDelegator // Simply cleans up some of the code in CustomJAdapter by handling delegation
{
	private Gson gson;
	private TypeAdapterFactory factory;
	
	public AdapterDelegator(Gson gson, TypeAdapterFactory factory)
	{
		this.gson = gson;
		this.factory = factory;
	}
	
	public <J> TypeAdapter<J> getAdapter(TypeToken<J> type)
	{
		return gson.getDelegateAdapter(factory, type);
	}
	
	public <J> TypeAdapter<J> getAdapter(String typeName)
	{
		try
		{
			TypeToken<J> type = (TypeToken<J>) TypeToken.get(Class.forName(typeName));

			return gson.getDelegateAdapter(factory, type);
		}
		catch (Exception e) {Logging.logException(e); return null;}
	}
}