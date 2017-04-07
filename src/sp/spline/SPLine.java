package sp.spline;

import com.teamcenter.rac.kernel.TCComponentBOMLine;

import sp.spblock.SPBlock.BlockContentType;
import sp.spblock.SPBlock.BlockType;

public abstract class SPLine
{
	public SPLineAttributes attributes;
	public BlockContentType blockContentType;
	public BlockType blockType;
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
