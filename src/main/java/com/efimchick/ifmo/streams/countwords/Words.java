package com.efimchick.ifmo.streams.countwords;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Words {


    public String countWords(List<String> lines) {

        String punctuation = "[«»,.()\\[\\]!?…;“„':\\-–[0-9]\\s]++";

        lines = Arrays.asList(lines.stream().map(e -> (e + "\n")
            .toLowerCase().replaceAll(punctuation, " "))
            .collect(Collectors.joining()).split("\\s"));

        Map<String, Long> firstResult = lines.stream()
            .collect(Collectors.groupingBy(
                Function.identity(),
                HashMap::new,
                Collectors.counting()
            ));

        Map<String, Long> secondResult = firstResult.entrySet().stream()
            .filter(e -> e.getKey().length() > 3)
            .filter(e -> e.getValue() > 9)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return secondResult.entrySet().stream()
            .sorted(Words::compare)
            .collect(Collectors.toList()).toString()
            .replaceAll("[\\[\\]\\s]", "")
            .replaceAll("=", " - ")
            .replaceAll(",", "\n");
    }

    private static int compare(Map.Entry<String, Long> item1, Map.Entry<String, Long> item2) {

        return !item1.getValue().equals(item2.getValue()) ?
            -item1.getValue().compareTo(item2.getValue()) :
            item1.getKey().compareTo(item2.getKey());
    }
}