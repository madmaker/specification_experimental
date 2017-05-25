package sp.spline;

import com.teamcenter.rac.kernel.TCComponentBOMLine;

import reports.EnumBlockType;

public abstract class SPLine
{
	public SPLineAttributesPlain attributes;
	public EnumBlockType blockContentType;
	public int blockType;
	public boolean isSubstitute = false;
	public boolean isNameNotApproved = false;
	public int height = 1;
	protected String uid = "";

	protected SPLine(){};

	public abstract SPLine newLine(TCComponentBOMLine bomLine);
	
	public abstract void updateWith(SPLine line);

	public abstract void init();

	public abstract void build();

	public abstract String getUid();
}
