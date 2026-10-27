package org.example;

import java.util.List;

public class PdfExporter extends DocumentExporter {

    @Override
    protected String format(List<String> lines) {
        return "%PDF " + String.join(" ", lines);
    }

    @Override
    protected String write(String formatted) {
        return "report.pdf <- " + formatted;
    }
}
