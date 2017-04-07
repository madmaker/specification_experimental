package sp.spline;

import java.util.Comparator;

public interface SPLineComparator extends Comparator<SPLine>
{
	public int compare(SPLine line1, SPLine line2);
}
