// By Iacon1
// Created 01/17/2022
// A segment representing an image

package Editables;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import java.util.ArrayList;

import Utils.Logging;
import Utils.MiscUtils;

public class Image implements Editable
{
	private BufferedImage image;
	private int bpp;
	private int transparentColor;

	public void setBPP(int bpp)
	{
		this.bpp = bpp;
	}
	public void setTransparentColor(int transparentColor) {this.transparentColor = transparentColor;}

	public List<Integer> getPalette()
	{
		List<Integer> palette = new ArrayList<Integer>();
		palette.add(transparentColor);
		for (int i = 0; i < image.getWidth(); ++i)
			for (int j = 0; j < image.getHeight(); ++j)
				if (!palette.contains(image.getRGB(i, j)))
						palette.add(image.getRGB(i, j));
		return palette;
	}
	public void loadImage(String path)
	{
		try {image = ImageIO.read(new File(path));}
		catch (IOException e) {Logging.logException(e);}
	}
	public void saveImage(String path)
	{
		MiscUtils.saveText(path, buildAssembly());
	}
	
	private String tileAssembly2bpp(int[] tile, List<Integer> palette, int shift)
	{
		String text = "";
	
		for (int i = 0; i < 8; ++i)
		{
			String byte1 = "%";
			String byte2 = "%";
			
			for (int j = 0; j < 8; ++j)
			{
				String indexString = Integer.toBinaryString(palette.indexOf(tile[8 * i + j] >> shift));
				int missingLength = bpp - indexString.length();
				if (missingLength > 0) indexString = new String("0").repeat(missingLength) + indexString;
				
				byte1 += indexString.substring(0, 1);
				byte2 += indexString.substring(1, 2);
			}
			
			text += "\t.byte\t" + byte1 + ", " + byte2 + "\n";
		}
		return text;
	}
	private String tileAssembly4bpp(int[] tile, List<Integer> palette)
	{
		return tileAssembly2bpp(tile, palette, 0) + "\n" + tileAssembly2bpp(tile, palette, 2);
	}
	private String tileAssembly8bpp(int[] tile, List<Integer> palette)
	{
		String text = "";
	
		for (int i = 0; i < 8; ++i)
		{	
			text += "\t.byte\t";
			
			for (int j = 0; j < 8; ++j)
			{
				String indexString = Integer.toBinaryString(palette.indexOf(tile[8 * i + j]));
				int missingLength = bpp - indexString.length();
				if (missingLength > 0) indexString = new String("0").repeat(missingLength) + indexString;
				
				if (j == 0) text += "%" + indexString;
				else text += ", %" + indexString;
			}
			
			text += "\n";
		}
		return text;
	}
	
	public String tileAssembly(int x, int y, List<Integer> palette)
	{
		int[] tile = image.getRGB(x, y, 8, 8, null, 0, 8);

		switch  (bpp)
		{
		case 2: return tileAssembly2bpp(tile, palette, 0);
		case 4: return tileAssembly4bpp(tile, palette);
		case 8: return tileAssembly8bpp(tile, palette);
		default: return tileAssembly4bpp(tile, palette);
		}
	}
	public String paletteAssembly(List<Integer> palette)
	{
		String text = "\t.db";
		for (int i = 0; i < palette.size(); ++i)
		{
			Color color = new Color(palette.get(i));
			String b = MiscUtils.asBinary(color.getBlue() / 8, 5);
			String g = MiscUtils.asBinary(color.getGreen() / 8, 5);
			String r = MiscUtils.asBinary(color.getBlue() / 8, 5);
			byte byte1 = Integer.valueOf(b + g.substring(0, 2), 2).byteValue();
			byte byte2 = Integer.valueOf(g.substring(2, 5) + r, 2).byteValue();
			text += " $" + MiscUtils.asHex(byte1) + ", $" + MiscUtils.asHex(byte2);
		}
		return text + ";";
	}
	public String buildAssembly() 
	{
		String text = "; Image\n";
		
		List<Integer> palette = getPalette();
		
		for (int i = 0; i < image.getWidth(); i += 8)
			for (int j = 0; j < image.getHeight(); j += 8)
				text += tileAssembly(i, j, palette) + "\n";
//		text += "; Palette\n";
//		text += paletteAssembly(palette);
		return text;
	}
	@Override
	public EditorPanel editorPanel()
	{
		EditorPanel panel = new EditorPanel("Graphics");
		panel.addSpinner("BPP", 2, 2, 8, (value) -> {setBPP(value);});
		panel.addButton("Load image", () -> {String path = MiscUtils.askPath(); loadImage(path);});
		panel.addButton("Save Image", () -> {String path = MiscUtils.askPath(); saveImage(path);});
		return panel;
	}

	@Override
	public String getName() {return "Image";}

}
