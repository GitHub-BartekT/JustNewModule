package pl.iseebugs.infrastructure.pomanalyzer;

import pl.iseebugs.infrastructure.pomanalyzer.dto.PomData;

import java.util.List;

interface PomAnalyzerImpl {
    PomData analyzePom();
    PomData getDataFromXML(String fileName);
    List<String> getXMLFileNames();
}
