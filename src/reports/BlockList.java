package reports;

public interface BlockList<T extends Block>
{
	public void add(T block);
	public T get(int i);
	public T getLast();
	public void remove(T block);
	public void remove(int i);
}
