// By Iacon1
// Created 31/22/2021
// SNES ROM Header
// https://sneslab.net/wiki/SNES_ROM_Header
// Cartridge types and such not exclusive, just common ones right now

package Segments;

import java.awt.Desktop;
import java.io.File;
import java.util.List;

import Utils.Editable;
import Utils.EditorPanel;
import Utils.MiscUtils;

public class Header implements Segment, Editable
{
	public enum CartridgeType
	{
		ROMonly(0x00, "ROM Only"),
		ROMRAM(0x01, "ROM & RAM"),
		RAMRAMBattery(0x02, "ROM, RAM, & Battery"),
		ROMSA1(0x33, "ROM & SA-1"),
		ROMRAMSA1(0x34, "ROM, RAM, & SA-1"),
		ROMRAMBatterySA1(0x35, "ROM, RAM, SA-1, & Battery"),
		; // TODO find more?
		
		private byte code;
		private String name;
		
		public byte getCode()
		{
			return code;
		}		
		public String getName()
		{
			return name;
		}
		
		private CartridgeType(byte code, String name)
		{
			this.code = code;
			this.name = name;
		}
		private CartridgeType(int code, String name)
		{
			this.code = (byte) code;
			this.name = name;
		}
	}
	public enum MapMode
	{
		loRom(0x20, "LoROM"),
		hiRom(0x21, "HiROM"),
		SA1Rom(0x23, "SA-1 ROM"),
		loFastRom(0x30, "LoROM + FastROM"),
		hiFastRom(0x31, "HiROM + FastROM"),
		SDDRom(0x32, "SDD-1 ROM"),
		exHiRom(0x35, "ExHiROM"),
		; // TODO find more?
		
		private byte code;
		private String name;
		
		public byte getCode()
		{
			return code;
		}		
		public String getName()
		{
			return name;
		}
		
		private MapMode(byte code, String name)
		{
			this.code = code;
			this.name = name;
		}
		private MapMode(int code, String name)
		{
			this.code = (byte) code;
			this.name = name;
		}
	}
	public enum Destination
	{
		japan(0x00, "Japan"),
		USA(0x01, "USA"),
		europe(0x02, "Europe"),
		;
		private byte code;
		private String name;
		
		public byte getCode()
		{
			return code;
		}		
		public String getName()
		{
			return name;
		}
		
		private Destination(byte code, String name)
		{
			this.code = code;
			this.name = name;
		}
		private Destination(int code, String name)
		{
			this.code = (byte) code;
			this.name = name;
		}
	}

	private char[] makerCode = new char[2];
	private char[] gameCode = new char[4];
	private byte specialVersion = 0x00;
	private CartridgeType cartridgeType = CartridgeType.ROMonly;
	private char[] gameTitle = new char[21];
	private MapMode mapMode = MapMode.loRom;
	private byte ROMSize = 0; // log2(ROM size in kilobytes)
	private byte RAMSize = 0; // log2(RAM size in kilobytes)
	private Destination destination = Destination.japan;
	private byte revision = 0;
	private byte[] checksum = new byte[2];
	
	public void setMakerCode(String makerCode)
	{
		makerCode = makerCode + "  "; // Ensures always at least 2 bytes
		makerCode.getChars(0, 2, this.makerCode, 0);
	}
	public void setGameCode(String gameCode)
	{
		gameCode = gameCode + "    "; // Ensures always at least 4 bytes
		gameCode.getChars(0, 4, this.gameCode, 0);
	}
	public void setSpecialVersion(byte specialVersion)
	{
		this.specialVersion = specialVersion;
	}
	public void setMapMode(MapMode mapMode)
	{
		this.mapMode = mapMode;
	}
	public void setGameTitle(String gameTitle)
	{
		int delta = 21 - gameTitle.length();
		if (delta > 0) while (delta != 0)
		{
			gameTitle = gameTitle + " ";
			delta -= 1;
		}

		gameTitle.getChars(0, 21, this.gameTitle, 0);
	}
	public void setCartridgeType(CartridgeType cartridgeType)
	{
		this.cartridgeType = cartridgeType;
	}
	public void setROMSize(int ROMSize) // In bytes
	{
		this.ROMSize = (byte) (int) Math.ceil(MiscUtils.logBase(ROMSize / 1024, 2));
	}
	public void setRAMSize(int RAMSize) // In bytes
	{
		this.RAMSize = (byte) (int) Math.ceil(MiscUtils.logBase(RAMSize / 1024, 2));
	}
	public void setDestination(Destination destination)
	{
		this.destination = destination;
	}
	public void setRevision(byte revision)
	{
		this.revision = revision;
	}
	public void calcChecksum(List<Byte> bytes)
	{
		int sum = 0;
		
		for (int i = 0; i < bytes.size(); ++i) sum += bytes.get(i);
		
		checksum[0] = (byte) ((sum >> 8) & 0xFF);
		checksum[1] = (byte) (sum & 0xFF);
	}
	
	@Override
	public int getOffset() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setOffset(int offset) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int size()
	{
		return 47;
	}

	@Override
	public String getTitle()
	{
		return "HEADER";
	}
	
	@Override
	public String getSummary()
	{
		return "ROM Header";
	}

	public String buildAssembly() 
	{
		byte expansionRAMSize = 0x00; // TODO this changes when some expansion chips are involved
		
		String text = null;
		
		
		text = ""
				+ ".segment \"" + getTitle() + "\" ; " + getSummary() + "\n"
				+ ".ascii \"" + new String(makerCode) + "\" ; Maker Code \n" // $00:FFB0-$00:FFB1 Maker Code
				+ ".ascii \"" + new String(gameCode) +  "\" ; Game Code \n" // $00:FFB2-$00:FFB5 Game Code
				+ ".byte $00, $00, $00, $00, $00, $00, $00 ; Fixed Value \n" // $00:FFB6-$00:FFBC Fixed Value (0x00)
				+ ".byte $" + MiscUtils.asHex(expansionRAMSize) + " ; Expansion RAM Size \n" // $00:FFBD-$00:FFBD Expansion RAM Size
				+ ".byte $" + MiscUtils.asHex(specialVersion) + " ; Special Version \n" // $00:FFBE-$00:FFBE Special Version
				+ ".byte $" + MiscUtils.asHex(cartridgeType.getCode()) + " ; Cartridge Type subnumber \n" // $00:FFBF-$00:FFBF Cartridge Type (Sub-number) TODO not documented???
				+ ".ascii \"" + new String(gameTitle) + "\" ; Game Title \n" // $00:FFC0-$00:FFD4 Game Title Registration TODO JIS X 0201 support
				+ ".byte $" + MiscUtils.asHex(mapMode.getCode()) + " ; Map Mode \n" // $00:FFD5-$00:FFD5 Map Mode
				+ ".byte $" + MiscUtils.asHex(cartridgeType.getCode()) + " ; Cartridge Type \n" // $00:FFD6-$00:FFD6 Cartridge Type
				+ ".byte $" + MiscUtils.asHex(ROMSize) + " ; ROM Size \n" // $00:FFD7-$00:FFD7 ROM Size
				+ ".byte $" + MiscUtils.asHex(RAMSize) + " ; RAM Size \n" // $00:FFD8-$00:FFD8 RAM Size
				+ ".byte $" + MiscUtils.asHex(destination.getCode()) + " ; Destination Code \n" // $00:FFD9-$00:FFD9 Destination Code
				+ ".byte $33 ; Fixed Value \n" // $00:FFDA-$00:FFDA Fixed Value (0x33)
				+ ".byte $" + MiscUtils.asHex(revision) + " ; Revision \n" // $00:FFDB-$00:FFDB Mask ROM Version
				+ ".byte $" + MiscUtils.asHex((byte) ~checksum[0]) + ", $" + MiscUtils.asHex((byte) ~checksum[1]) + " ; Complement Check \n" // $00:FFDC-$00:FFDD Complement Check
				+ ".byte $" + MiscUtils.asHex((byte) checksum[0]) + ", $" + MiscUtils.asHex((byte) checksum[1]) + " ; Checksum \n" // $00:FFDE-$00:FFDF Checksum
				;
		
		return text;
	}
	
	@Override
	public String buildConfig()
	{
		String text = null;
		
		text = ""
				+ "  " + getTitle() + ": \n"
				+ "    load = ROM \n"
				+ "    start = $FFB0; \n";
		
		return text;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public EditorPanel editorPanel()
	{
		EditorPanel panel = new EditorPanel("Header Editor");
		// Maker code
		panel.addTextbox("Maker code", 2, (value) -> {setMakerCode(value);});
		// Game code
		panel.addTextbox("Game code", 4, (value) -> {setGameCode(value);});
		// Special version
		panel.addSpinner("Special version", 0, 0, 256, (value) -> {setSpecialVersion(value.byteValue());});
		// Map mode
		panel.addOptionList("Map mode", MapMode.values(), MapMode.loRom, (value) -> {setMapMode(value);});
		// Game title
		panel.addTextbox("Game title", 21, (value) -> {setGameTitle(value);});
		// Cartridge type
		panel.addOptionList("Cartridge Type", CartridgeType.values(), CartridgeType.ROMonly, (value) -> {setCartridgeType(value);});
		// ROM size
		panel.addSpinner("ROM size", 0, 0, Integer.MAX_VALUE - 1, (value) -> {setROMSize(value);});
		// RAM size
		panel.addSpinner("RAM size", 0, 0, Integer.MAX_VALUE - 1, (value) -> {setRAMSize(value);});
		// Destination
		panel.addOptionList("Destination", Destination.values(), Destination.USA, (value) -> {setDestination(value);});
		// Revision
		panel.addSpinner("Revision", 0, 0, 256, (value) -> {setRevision(value.byteValue());});

		panel.addButton("Save", () ->
		{
			Desktop desktop = Desktop.getDesktop();
			desktop.browseFileDirectory(new File(MiscUtils.getAbsolute("")));
		});
		return panel;
	}
	
	
}
