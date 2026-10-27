package org.example;

import java.util.List;
import java.util.stream.Collectors;

public abstract class DocumentExporter {

    // Template method: fixes the order of the steps, subclasses cannot change it
    public final String export(List<String> lines) {
        List<String> prepared = prepare(lines);
        String formatted = format(prepared);
        return write(formatted);
    }

    // Common step: same for every format
    protected List<String> prepare(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Nothing to export");
        }
        return lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());
    }

    // Steps each format must provide
    protected abstract String format(List<String> lines);

    protected abstract String write(String formatted);
}
