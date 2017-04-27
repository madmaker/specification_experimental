package sp;

public class DocumentType
{
	public String			shortName;
	public String			longName;
	public int				type;

	public DocumentType(String shortName, String longName, int type)
	{
		this.shortName = shortName;
		this.longName = longName;
		this.type = type;
	}
}
