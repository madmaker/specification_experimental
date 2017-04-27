package sp.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sp.Report;
import sp.SP;
import sp.StampData;
import sp.SP.FormField;
import sp.SPSettings;
import sp.spblock.SPBlockList;
import sp.spblock.SPBlock;
import sp.spline.SPLine;

public class XmlBuilder
{
	private XmlBuilderConfiguration configuration;
	
	private DocumentBuilderFactory documentBuilderFactory;
	private DocumentBuilder builder;
	private Document document;
	private Element node_root;
	private Element node;
	private Element node_block = null;
	private Element node_occ_title;
	private Element node_occ;
	
	private int currentLineNum = 1;
	private int currentPageNum = 1;
	
	private Report report;
	private ArrayList<String> globalRemark = null;

	public XmlBuilder(XmlBuilderConfiguration configuration)
	{
		this.configuration = configuration;
	}

	public void setConfiguration(XmlBuilderConfiguration configuration)
	{
		this.configuration = configuration;
	}
	
	public File buildXml()
	{
		try{
			checkData();
			
			node = document.createElement("FileData");
			node.setAttribute("FileName", "Файл спецификации: " + SP.settings.getStringProperty("OBOZNACH") + ".pdf/0");			
			node_root.appendChild(node);
			
			node = document.createElement("Settings");
			node.setAttribute("ShowAdditionalForm", SPSettings.doShowAdditionalForm==true?"true":"false");
			node_root.appendChild(node);
			
			ListIterator<SPBlock> iterator = report.blockList.iterator();
			SPBlock block;
			if (node_block == null) {
				node_block = document.createElement("Block");
			}
			addEmptyLines(1);
			while(iterator.hasNext()){
				block = iterator.next();
				processBlock(block);
			}
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource(document);
			File xmlFile = File.createTempFile(StampData.id+"_", ".xml");
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);
			return xmlFile;
		} catch (TransformerConfigurationException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void processBlock(SPBlock block){
		if(block.getLines()!=null){
			if (node_block == null) {
				node_block = document.createElement("Block");
				node_root.appendChild(node_block);
				addEmptyLines(1);
			}
			if(getFreeLinesNum() < 3 + block.getLines().get(0).height){
				newPage();
			}
			if(blockList.getLast()==block && block.size()==1 && globalRemark!=null){
				if(getFreeLinesNum() < (globalRemark.size() + block.getLines().get(0).height + 2)){
					newPage();
				}
			}
			if(currentPageNum==1 && getFreeLinesNum()!=configuration.MaxLinesOnFirstPage-1){
				addEmptyLines(1);
			} else if (currentPageNum>1 && getFreeLinesNum()!=configuration.MaxLinesOnOtherPage-1){
				addEmptyLines(1);
			}
			node_root.appendChild(node_block);
			node_occ_title = document.createElement("Occurrence");
			node_occ_title.setAttribute("font", "underline,bold,italic");
			node = document.createElement("Col_" + 5);
			node.setAttribute("align", "center");
			node.setTextContent(block.title);
			node_occ_title.appendChild(node);
			node_block.appendChild(node_occ_title);
			currentLineNum++;
			addEmptyLines(1);
			for(SPLine blockLine : block.getLines()){
				newLine(block, blockLine);
			}
			addEmptyLines(block.attributes.reserveLinesCount);
			node_root.appendChild(node_block);
		}
	}
	
	public int countSublines(SPLine line)
	{
		int result = 0;
		if(line.getAttachedLines() == null) return result;
		for(SPLine attachedLine : line.getAttachedLines()){
			result += attachedLine.lineHeight;
		}
		return result;
	}
	
	public void newPage()
	{
		addEmptyLines(getFreeLinesNum());
		node_block = document.createElement("Block");
		node_root.appendChild(node_block);
		currentLineNum = 1;
		currentPageNum += 1;
		addEmptyLines(1);
	}
	
	public void newLine(SPBlock block, SPLine line){
		boolean isLastLineInBlock = (block.getLines().get(block.getLines().size()-1)==line) && blockList.getLast()==block;
		
		if(isLastLineInBlock && (globalRemark!=null && getFreeLinesNum() < (globalRemark.size() + line.height + 1/*empty line before remark*/))){
			newPage();
		}
		
		if(getFreeLinesNum() < (line.height + countSublines(line))) newPage();
		
		for(int i = 0; i < line.height; i++){
			node_occ = document.createElement("Occurrence");
			if(i==0){
				node = document.createElement("Col_" + 1);
				node.setAttribute("align", "center");
				node.setTextContent(line.attributes.getFormat().toString());
				node_occ.appendChild(node);
				node = document.createElement("Col_" + 2);
				node.setAttribute("align", "center");
				node.setTextContent(line.attributes.getZone().toString());
				node_occ.appendChild(node);
				node = document.createElement("Col_" + 3);
				node.setAttribute("align", "center");
				node.setTextContent(line.attributes.getPosition());
				node_occ.appendChild(node);
				node = document.createElement("Col_" + 4);
				node.setTextContent(line.attributes.getId());
				node_occ.appendChild(node);
				node = document.createElement("Col_" + 6);
				node.setAttribute("align", "center");
				node.setTextContent(line.attributes.getStringValueFromField(FormField.QUANTITY).equals("-1")?" ":line.attributes.getStringValueFromField(FormField.QUANTITY));
				node_occ.appendChild(node);
			}
			node = document.createElement("Col_" + 5);
			node.setTextContent((line.attributes.getName()!=null && (i < line.attributes.getName().size())) ? line.attributes.getName().get(i) : "");
			if(line.blockContentType==BlockContentType.STANDARDS||line.blockContentType==BlockContentType.OTHERS||line.blockContentType==BlockContentType.MATERIALS){
				if(line.isNameNotApproved){
					node.setAttribute("warning", "true");
				}
			}
			node_occ.appendChild(node);
			node = document.createElement("Col_" + 7);
			node.setTextContent((line.attributes.getRemark()!=null && (i < line.attributes.getRemark().size())) ? line.attributes.getRemark().get(i) : "");
			node_occ.appendChild(node);
			
			node_block.appendChild(node_occ);
		}
		
		
		currentLineNum += line.height;

		if(line.getAttachedLines()!=null){
			for(SPLine attachedLine : line.getAttachedLines()){
				newLine(block, attachedLine);
			}
		}
		
		if(isLastLineInBlock && globalRemark!=null){
			addEmptyLines(1);
			for(String string : globalRemark){
				node_occ = document.createElement("Occurrence");
				node_occ.setAttribute("merge", "true");
				node = document.createElement("Col_" + 4);
				node.setAttribute("align", "left");
				node.setTextContent(string);
				node_occ.appendChild(node);
				node_block.appendChild(node_occ);
			}
		}
	}
	
	public void addEmptyLines(int num)
	{
		for(int i = 0; i < num; i++){
			if(getFreeLinesNum() <= 0){
				newPage();
			}
			currentLineNum++;
			node_occ = document.createElement("Occurrence");
			node_block.appendChild(node_occ);
		}
	}
	
	int getFreeLinesNum()
	{
		if(currentPageNum==1) return (configuration.MaxLinesOnFirstPage - currentLineNum + 1);
		return (configuration.MaxLinesOnOtherPage - currentLineNum + 1);
	}
	
	private void checkData()
	{
		if(blockList == null)
			throw new RuntimeException("No data to build xml with.");
	}
}
