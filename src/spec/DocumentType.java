package spec;

import specblock.BlockList.BlockContentType;

public class DocumentType
{
	public String			shortName;
	public String			longName;
	public BlockContentType	type;

	public DocumentType(String shortName, String longName, BlockContentType type)
	{
		this.shortName = shortName;
		this.longName = longName;
		this.type = type;
	}
}
