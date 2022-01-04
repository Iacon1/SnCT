// By Iacon1
// Created 12/06/2021
// Something that has an editor panel

package Utils;

public interface Editable
{
	public String getName();
	
	/** Returns a panel for the editor to use
	 *  to edit this system.
	 * 
	 *  @Return The panel.
	 */
	public EditorPanel editorPanel();
}
