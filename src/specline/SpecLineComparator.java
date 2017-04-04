package specline;

import java.util.Comparator;

public interface SpecLineComparator extends Comparator<SpecLine>
{
	public int compare(SpecLine line1, SpecLine line2);
}
