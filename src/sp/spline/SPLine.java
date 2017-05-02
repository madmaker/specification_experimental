package sp.spline;

import com.teamcenter.rac.kernel.TCComponentBOMLine;

public abstract class SPLine
{
	public SPLineAttributes attributes;
	public int blockContentType;
	public int blockType;
	public boolean isSubstitute = false;
	public boolean isNameNotApproved = false;
	public int height = 1;
	private String uid = "";

	protected SPLine(){};

	public abstract SPLine newLine(TCComponentBOMLine bomLine);

	public abstract void init();

	public abstract void build();

	public abstract String getUid();
}
