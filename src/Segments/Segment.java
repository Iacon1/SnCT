// By Iacon1
// Created 10/22/2021
// A chunk of bytes that can be moved around by the user.

package Segments;

import Utils.Editable;

public interface Segment extends Editable
{
	// Gets offset
	public int getOffset();
	// Sets offset
	public void setOffset(int offset);
	/** Returns the estimated size of the chunk 
	 * in bytes once compiled.
	 * 
	 * @return estimated size in bytes once compiled.
	 */
	public int size();
	
	/** Returns the title of the segment
	 * 
	 * @return title as a string.
	 */
	public String getTitle();
	/** Returns a short summary of the segment
	 * 
	 * @return summary as a string.
	 */
	public String getSummary();
	/** Returns the config for this
	 * 
	 */
	public String buildConfig();
	
	public boolean editable();
	public boolean removable();
}
