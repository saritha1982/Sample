package activiti.agent.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author sarbr
 *
 */
public class UpdateXML {

	// Save the updated DOM into the XML back
	private void saveToXML(File file, Document doc) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			String strTemp = writer.toString();
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(strTemp);
			bufferedWriter.flush();
			bufferedWriter.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	// Read into the relevant node and update the text value
	private void updateTextValue(Document doc, String oldValue, String newValue) {
		try {
			Element root = doc.getDocumentElement();
			// Return the NodeList on a given tag name
			NodeList childNodes = root.getElementsByTagName("hudson.plugins.git.UserRemoteConfig");
			for(int index = 0; index < childNodes.getLength(); index++) {
				NodeList subChildNodes = childNodes.item(index).getChildNodes();
				//if(subChildNodes.item(index).getTextContent().equals(oldValue)) {
				// Update the relevant node text content. item() position the NodeList.
				subChildNodes.item(1).setTextContent(newValue);
				//                    }
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateXML(File file, String strOldValue, String strNewValue) {
		System.out.println("Updating "+strOldValue +" nevalue "+strNewValue);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try {
			docFactory.setCoalescing(true);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			updateTextValue(doc, strOldValue, strNewValue);
			saveToXML(file, doc);
		}
		catch(Exception ex) {
		}
	}

	/* public static void main(String[] args) {
          File file = new File("C:\\Users\\sarbr\\activiti_workspace\\test\\config.xml");
          new UpdateXML().updateXML(file, "RootPomPath", "sample_apps/Java/test/pom1.xml");
     }*/
}