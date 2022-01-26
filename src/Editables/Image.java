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
	
	public String tileAssembly(int x, int y, List<Integer> palette)
	{
		int[] tile = image.getRGB(x, y, 8, 8, null, 0, 8);
	
		String[] planes = new String[bpp];
		
		for (int i = 0; i < bpp; ++i) planes[i] = "";
		
		if (bpp == 1 || bpp == 2 || bpp == 4)
		{
			for (int i = 0; i < 8; ++i)
			{
				for (int j = 0; j < bpp; ++j)
				{
					planes[j] += "\t.byte %";
				}
			
				for (int j = 0; j < 8; ++j)
				{
					String indexString = Integer.toBinaryString(palette.indexOf(tile[8 * i + j]));
					int missingLength = bpp - indexString.length();
					indexString = new String("0").repeat(missingLength) + indexString;
					for (int k = 0; k < bpp; ++k)
						planes[k] += indexString.substring(bpp - k - 1, bpp - k);
				}
				
				for (int j = 0; j < bpp; ++j)
				{
					planes[j] += "\n";
				}
			}
			
			String text = planes[0];
			for (int i = 1; i < bpp; ++i) text += planes[i];
			return text;
		}
		else if (bpp == 8)
		{
			String text = "";
			for (int i = 0; i < 8 * 8; ++i)
				text += "\t.byte %" + Integer.toBinaryString(palette.indexOf(tile[i])) + "\n";
			return text;
		}
		else return null;
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
		text += "; Palette\n";
		text += paletteAssembly(palette);
		return text;
	}
	@Override
	public EditorPanel editorPanel()
	{
		EditorPanel panel = new EditorPanel("Graphics");
		panel.addSpinner("BPP", 1, 1, 8, (value) -> {setBPP(value);});
		panel.addButton("Load image", () -> {String path = MiscUtils.askPath(); loadImage(path);});
		panel.addButton("Save Image", () -> {String path = MiscUtils.askPath(); saveImage(path);});
		return panel;
	}

	@Override
	public String getName() {return "Image";}

}
