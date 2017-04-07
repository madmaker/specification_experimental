package vp.vpline;

import java.util.ArrayList;
import java.util.HashMap;

public class VPDataOcc
{
	String position = "";
	String name = "";
	String idDocForDelivery = "";
	String upperItem = "";
	String allWhereUsedInOneLine = "";
	String allQtyInOneLine = "";
	String id = "";
	boolean isVpSection = false;
	Double qty = 0.0;
	String demension = "";
	String remark = "";
	HashMap<String,Double> mapWhereUsed;
	ArrayList<String> arrayListWhereUsed;
	
	
	public static VPDataOcc copyVpOcc(VPDataOcc inVpDataLine)
	{
		VPDataOcc out = new VPDataOcc();
		out.position = inVpDataLine.position;
		out.name = inVpDataLine.name;
		out.idDocForDelivery = inVpDataLine.idDocForDelivery;
		out.upperItem = inVpDataLine.upperItem;
		out.qty = inVpDataLine.qty;
		out.demension = inVpDataLine.demension;
		out.remark = inVpDataLine.remark;
		out.mapWhereUsed = new HashMap<String, Double>();
		out.isVpSection = inVpDataLine.isVpSection;
		
		return out;
	}
	
	public void makeOneStringFromWhereUsedMapAndQty()
	{
		for (String currWhereUsed : arrayListWhereUsed) {
			allWhereUsedInOneLine += (currWhereUsed + "\n");
			allQtyInOneLine += (mapWhereUsed.get(currWhereUsed) + "\n");
		}
		
		allWhereUsedInOneLine = allWhereUsedInOneLine.substring(0, allWhereUsedInOneLine.length()-1);
		allQtyInOneLine = allQtyInOneLine.substring(0, allQtyInOneLine.length()-1);
	}
}
