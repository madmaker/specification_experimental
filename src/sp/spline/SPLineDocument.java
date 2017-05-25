package sp.spline;

import com.teamcenter.rac.kernel.TCComponentBOMLine;

import sp.DocumentType;
import sp.DocumentTypes;

public class SPLineDocument extends SPLine
{
	public DocumentType documentType;
	public boolean isBaseDocument;
	
	public SPLineDocument()
	{
		documentType = DocumentTypes.dummyType;
		isBaseDocument = false;
	}

	@Override
	public SPLine newLine(TCComponentBOMLine bomLine)
	{
		return null;
	}

	@Override
	public void updateWith(SPLine line)
	{
	}

	@Override
	public void init()
	{
	}

	@Override
	public void build()
	{
	}

	@Override
	public String getUid()
	{
		return this.uid;
	}
}
