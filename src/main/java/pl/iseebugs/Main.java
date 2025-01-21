package pl.iseebugs;

import pl.iseebugs.infrastructure.pomanalyzer.PomAnalyzer;
import pl.iseebugs.infrastructure.pomanalyzer.dto.PomData;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        PomData pomData = new PomAnalyzer().analyzePom();

        Optional.ofNullable(pomData)
                .ifPresent(System.out::println);
    }
}