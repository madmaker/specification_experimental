package sp.spline.attributes;

import java.util.ArrayList;

public class SLKits
{
	private ArrayList<String> ids;
	private ArrayList<String> names;
	private ArrayList<Integer> qtys;
	private double totalQuantity;

	public SLKits()
	{
		ids = new ArrayList<String>(1);
		names = new ArrayList<String>(1);
		qtys = new ArrayList<Integer>(1);
		totalQuantity = 0;
	}

	public void addKit(String id, String name, int qty)
	{
		int pos = ids.indexOf(id);
		if (pos != -1) {
			qtys.set(pos, qtys.get(pos) + qty);
		} else {
			ids.add(id);
			names.add(name);
			qtys.add(qty);
		}
	}

	public void addKits(SLKits kits)
	{
		if (kits != null) {
			for (int i = 0; i < kits.ids.size(); i++) {
				addKit(kits.ids.get(i), kits.names.get(i), kits.qtys.get(i));
			}
		}
	}

	public ArrayList<String> getKits()
	{
		ArrayList<String> result = new ArrayList<String>();
		if (ids.size() == 1 && qtys.get(0) == 1) {
			result.add("из компл. " + names.get(0));
			return result;
		}
		for (int i = 0; i < ids.size(); i++) {
			result.add(qtys.get(i) + " шт. из компл. " + names.get(i));
		}
		return result;
	}

	public void setTotalQuantity(double quantity)
	{
		this.totalQuantity = quantity;
	}
}
