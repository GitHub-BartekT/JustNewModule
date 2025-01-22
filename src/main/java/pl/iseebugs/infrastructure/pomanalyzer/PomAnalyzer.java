package pl.iseebugs.infrastructure.pomanalyzer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pl.iseebugs.infrastructure.pomanalyzer.dto.PomData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

public class PomAnalyzer implements PomAnalyzerImpl {

    private static final String POM_FILE = "pom.xml";
    private static final PomData pomData = new PomData();

    public PomData analyzePom() {
        File pomFile = new File(POM_FILE);
        if (!pomFile.exists()) {
            System.out.println("There is no pom.xml file in this directory.");
            return null;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(pomFile);

            pomData.setGroupId(getTagValueOutsideParent(document, "groupId"));

            NodeList artifactIdNodes = document.getElementsByTagName("artifactId");
            if (artifactIdNodes.getLength() > 0) {
                pomData.setArtifactId(artifactIdNodes.item(0).getTextContent());
            }

            NodeList nameNodes = document.getElementsByTagName("name");
            if (nameNodes.getLength() > 0) {
                pomData.setName(nameNodes.item(0).getTextContent());
            }

            NodeList dependencyNodes = document.getElementsByTagName("dependency");
            for (int i = 0; i < dependencyNodes.getLength(); i++) {
                Element dependencyElement = (Element) dependencyNodes.item(i);
                String groupId = getTagValue(dependencyElement, "groupId");
                String artifactId = getTagValue(dependencyElement, "artifactId");

                if (groupId != null && artifactId != null) {
                    pomData.addDependency(groupId + ":" + artifactId);
                }
            }
            return pomData;

        } catch (Exception e) {
            System.err.println("Exception during pom.xml analyze: " + e.getMessage());
            return null;
        }
    }

    private static String getTagValueOutsideParent(Document document, String tagName) {
        NodeList nodes = document.getElementsByTagName(tagName);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            if (!isInsideParent(element)) {
                return element.getTextContent();
            }
        }
        return null;
    }

    private static boolean isInsideParent(Element element) {
        NodeList parents = element.getParentNode().getParentNode().getChildNodes();
        for (int i = 0; i < parents.getLength(); i++) {
            if (parents.item(i).getNodeName().equals("parent")) {
                return true;
            }
        }
        return false;
    }

    private static String getTagValue(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        return (nodes.getLength() > 0) ? nodes.item(0).getTextContent() : null;
    }

    @Override
    public PomData getDataFromXML(final String fileName) {
        return null;
    }

    @Override
    public List<String> getXMLFileNames() {
        return null;
    }
}
