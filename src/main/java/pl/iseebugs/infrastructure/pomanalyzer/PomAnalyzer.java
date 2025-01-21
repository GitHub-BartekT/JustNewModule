package pl.iseebugs.infrastructure.pomanalyzer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pl.iseebugs.infrastructure.pomanalyzer.dto.PomData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

public class PomAnalyzer implements PomAnalyzerImpl{

    private static final String POM_FILE = "pom.xml";
    private static final PomData pomData = new PomData();

    public PomData analyzePom() {
        File pomFile = new File(POM_FILE);
        if (!pomFile.exists()) {
            System.out.println("There is not pom.xml file in this directory.");
            return null;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(pomFile);

            NodeList groupIdNodes = document.getElementsByTagName("groupId");
            if (groupIdNodes.getLength() > 0) {
                pomData.setGroupId(groupIdNodes.item(0).getTextContent());
            }

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

    //TODO:
    @Override
    public PomData getDataFromXML(final String fileName) {
        return null;
    }

    //TODO:
    @Override
    public List<String> getXMLFileNames() {
        return null;
    }

    private static String getTagValue(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        return (nodes.getLength() > 0) ? nodes.item(0).getTextContent() : null;
    }
}
