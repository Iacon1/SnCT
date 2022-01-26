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

		panel.addTextbox("On COP (Native)", maxLength, (value) -> {nativeCOP = value;});
		panel.addTextbox("On COP (Native)", maxLength, (value) -> {nativeCOP = value;});
		
		panel.addTextbox("On COP (Native)", maxLength, (value) -> {nativeCOP = value;});
		panel.addTextbox("On COP (Native)", maxLength, (value) -> {nativeCOP = value;});
		
		panel.addTextbox("On COP (Native)", maxLength, (value) -> {nativeCOP = value;});
		panel.addTextbox("On COP (Native)", maxLength, (value) -> {nativeCOP = value;});
		
		panel.addTextbox("On COP (Native)", maxLength, (value) -> {nativeCOP = value;});
		panel.addTextbox("On COP (Native)", maxLength, (value) -> {nativeCOP = value;});
		
		return panel;
	}
}
