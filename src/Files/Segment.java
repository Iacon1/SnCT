// By Iacon1
// Created 31/22/2021
// A chunk of bytes that can be moved around by the user.

package Files;

public interface Segment
{
	/** Returns the estimated size of the chunk 
	 * in bytes once compiled.
	 * 
	 * @return estimated size in bytes once compiled.
	 */
	public int size(); // Size in bytes
	
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
	
	/** Returns the assembly code associated with the chunk.
	 * 
	 * @return code
	 */
	public String asText();
}
