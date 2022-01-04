// By Iacon1
// Created 09/16/2021
// An array list that keeps and fills gaps instead of shifting.
// Good for times when indexes must be preserved.

package Utils;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class GappyArrayList<E> extends ArrayList<E>
{
	/** Removes an element and does *not* shift,
	 *  Instead leaving a gap
	 */
	@Override public E remove(int index)
	{
		if (index >= size() || get(index) == null) return null;
		E e = get(index);
		super.set(index, null);
		return e;
	}
	/** Removes an element and does *not* shift
	 */
	@Override public boolean remove(Object o)
	{
		int index = super.indexOf(o);
		if (index != -1) {remove(index); return true;}
		else return false;
	}
	
	/** Gets the first gap, if one exists, or
	 *  size() if one doesn't.
	 */
	public int getFirstGap()
	{
		int index = super.indexOf(null);
		if (index != -1) return index;
		else return size();
	}
	
	/** Adds an element *into any gap if available,* or to end if not
	 */
	@Override public boolean add(E e)
	{
		int index = getFirstGap();
		if (index != size())
		{
			set(index, e);
			return true;
		}
		else return super.add(e);
	}
}
