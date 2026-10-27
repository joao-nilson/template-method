package org.example;

import java.util.List;

public class CsvExporter extends DocumentExporter {

    @Override
    protected String format(List<String> lines) {
        return String.join(",", lines);
    }

    @Override
    protected String write(String formatted) {
        return "report.csv <- " + formatted;
    }
}
