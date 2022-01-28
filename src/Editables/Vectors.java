// By Iacon1
// Created 01/26/2022
// Vectors panel.

package Editables;

public class Vectors implements Editable
{
	private static final int maxLength = 32;
	
	private String nativeCOP;
	private String nativeBRK;
	private String nativeABORT;
	private String nativeNMI;
	private String nativeRST;
	
	private String emulationCOP;
	private String emulationBRK;
	private String emulationABORT;
	private String emulationNMI;
	private String emulationRST;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EditorPanel editorPanel()
	{
		EditorPanel panel = new EditorPanel("Vectors");
		panel.addTextbox("On COP (Native)", maxLength, (value) -> {nativeCOP = value;});
		panel.addTextbox("On COP (Emulation)", maxLength, (value) -> {emulationCOP = value;});

		panel.addTextbox("On BRK (Native)", maxLength, (value) -> {nativeBRK = value;});
		panel.addTextbox("On BRK (Emulation)", maxLength, (value) -> {emulationBRK = value;});
		
		panel.addTextbox("On ABORT (Native)", maxLength, (value) -> {nativeABORT = value;});
		panel.addTextbox("On ABORT (Emulation)", maxLength, (value) -> {emulationABORT = value;});
		
		panel.addTextbox("On NMI (Native)", maxLength, (value) -> {nativeNMI = value;});
		panel.addTextbox("On NMI (Emulation)", maxLength, (value) -> {emulationNMI = value;});
		
		panel.addTextbox("On RST (Native)", maxLength, (value) -> {nativeRST = value;});
		panel.addTextbox("On RST (Emulation)", maxLength, (value) -> {emulationRST = value;});
		
		return panel;
	}
}
