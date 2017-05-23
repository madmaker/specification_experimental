package sp;

import reports.EnumBlockType;

public class DocumentType
{
	public String			shortName;
	public String			longName;
	public EnumBlockType	type;

	public DocumentType(String shortName, String longName, EnumBlockType type)
	{
		this.shortName = shortName;
		this.longName = longName;
		this.type = type;
	}
}
