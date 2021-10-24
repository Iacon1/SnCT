package Frames;

import Segments.Header;
import Utils.DebugLogger;
import Utils.Logging;

public class test {

	public static void main(String[] args)
	{
		Logging.setLogger(new DebugLogger());
		// TODO Auto-generated method stub
		Header header = new Header();
		header.setMakerCode("TE");
		header.setGameCode("TEST");
		header.setGameTitle("Test Test");
		header.setROMSize(1024);
		header.setRAMSize(2050);
		Logging.logRaw("Test", "\n" + header.buildAssembly());
		Logging.logRaw("Test", "\n" + header.buildConfig());
	}

}
