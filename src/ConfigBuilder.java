import java.util.LinkedList;

import Segments.Segment;
import Utils.MiscUtils;

// By Iacon1
// Created 10/24/2021
// Builds the config file

public class ConfigBuilder
{
	private int banks = 0; // Number of extra banks
	private LinkedList<Segment> segments; // User-defined segments
	
	public void setBanks(int banks)
	{
		this.banks = banks;
	}
	public void addSegment(Segment segment)
	{
		segments.add(segment);
	}
	public void removeSegment(Segment segment)
	{
		segments.remove(segment);
	}
	
	/** Builds the config file
	 * 
	 * @return The config file, as a string.
	 */
	public String build()
	{
		String text = "# ca65 linker config \n\n";
		
		text = text
				+ "# Physical areas of memory \n"
				+ "MEMORY \n"
				+ "{ \n"
				+ "  ZEROPAGE: start =      0, size =  $100; \n"
				+ "  BSS:      start =   $200, size = $1800; \n"
				+ "  ROM:      start =  $8000, size = $8000, fill = yes; \n"
				;
		for (int i = 1; i <= banks; ++i) // Populates banks
			text = text
				+ "  BANK" + MiscUtils.asHex((byte) i, 1) + ":    start = $" + MiscUtils.asHex((byte) i, 1) + "8000, size = $8000, fill = yes; \n"
				;
		text = text
				+ "} \n\n"
				;
		
		text = text 
				+ "# Logical areas of memory \n"
				+ "SEGMENTS \n"
				+ "{ \n"
				+ "  ZEROPAGE: load = ZEROPAGE, type = zp; \n"
				+ "  BSS:      load = BSS,      type = bss,    align = $100; \n"
				+ "\n";
		
		for (int i = 0; i < segments.size(); ++i)
			text = text + segments.get(i).buildConfig();
		
		text = text
				+ "\n"
				;
		for (int i = 1; i <= banks; ++i) // Populates banks
			text = text
				+ "  BANK" + MiscUtils.asHex((byte) i, 1) + ":    load = BANK" + MiscUtils.asHex((byte) i, 1) + ",    align = $8000, optional = yes; \n"
				;
		text = text
				+ "} \n\n"
				;
		
		return text;
	}
}
