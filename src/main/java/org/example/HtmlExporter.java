package org.example;

import java.util.List;

public class HtmlExporter extends DocumentExporter {

    @Override
    protected String format(List<String> lines) {
        StringBuilder html = new StringBuilder("<ul>");
        for (String line : lines) {
            html.append("<li>").append(line).append("</li>");
        }
        return html.append("</ul>").toString();
    }

    @Override
    protected String write(String formatted) {
        return "report.html <- " + formatted;
    }
}
