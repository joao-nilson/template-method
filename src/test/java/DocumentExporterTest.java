import org.example.CsvExporter;
import org.example.DocumentExporter;
import org.example.HtmlExporter;
import org.example.PdfExporter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DocumentExporterTest {

    private final List<String> lines = Arrays.asList("Ana", "Bob", "Carl");

    @Test
    void shouldExportPdf() {
        assertEquals("report.pdf <- %PDF Ana Bob Carl", new PdfExporter().export(lines));
    }

    @Test
    void shouldExportCsv() {
        assertEquals("report.csv <- Ana,Bob,Carl", new CsvExporter().export(lines));
    }

    @Test
    void shouldExportHtml() {
        assertEquals("report.html <- <ul><li>Ana</li><li>Bob</li><li>Carl</li></ul>",
                new HtmlExporter().export(lines));
    }

    @Test
    void shouldTrimAndIgnoreBlankLinesInAllFormats() {
        List<String> messy = Arrays.asList("  Ana ", "", "   ", "Bob");
        assertEquals("report.csv <- Ana,Bob", new CsvExporter().export(messy));
        assertEquals("report.pdf <- %PDF Ana Bob", new PdfExporter().export(messy));
    }

    @Test
    void shouldThrowExceptionWhenThereIsNothingToExport() {
        DocumentExporter exporter = new CsvExporter();
        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class,
                () -> exporter.export(null));
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class,
                () -> exporter.export(Collections.emptyList()));
        assertEquals("Nothing to export", e1.getMessage());
        assertEquals("Nothing to export", e2.getMessage());
    }

    @Test
    void shouldRunStepsInOrderPrepareFormatWrite() {
        List<String> steps = new ArrayList<>();
        DocumentExporter exporter = new DocumentExporter() {
            @Override
            protected List<String> prepare(List<String> lines) {
                steps.add("prepare");
                return super.prepare(lines);
            }

            @Override
            protected String format(List<String> lines) {
                steps.add("format");
                return "formatted";
            }

            @Override
            protected String write(String formatted) {
                steps.add("write");
                return formatted;
            }
        };
        exporter.export(lines);
        assertEquals(Arrays.asList("prepare", "format", "write"), steps);
    }
}
