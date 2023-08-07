package edu.stanford.radx.studytracker.cli;

import edu.stanford.radx.studytracker.StudyRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.List;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-10
 */
@Component
public class StudyRecordsPrinter {

    protected static final String FORMAT = "%s\t%-10s\t%-10s\t%-10s\t%s\n";

    private static boolean isConsole() {
        return System.console() != null;
    }

    public void printStudyRecords(List<StudyRecord> records,
                                  boolean groupByProgram) {
        if(isConsole()) {
            System.err.printf("There are %d records\n", records.size());
            System.err.printf(FORMAT, "PHS Number", "Program", "Start", "End", "Title");
            records
                    .stream()
                    .sorted(getComparator(groupByProgram))
                    .map(this::formatRecord)
                    .forEach(System.err::println);
        }
        else {
            try {
                var csvPrinter = new CSVPrinter(System.out, CSVFormat.DEFAULT);
                csvPrinter.printRecord("PHS Number", "Program", "Start", "End", "Title");
                records.stream()
                        .sorted(getComparator(groupByProgram))
                        .forEach(rec -> {
                            try {
                                csvPrinter.printRecord(rec.phsNumber(), rec.radxProgram(), rec.studyStartDate(), rec.studyEndDate(), rec.projectTitle());
                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        });
                csvPrinter.flush();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    private Comparator<StudyRecord> getComparator(boolean groupByProgram) {
        if(groupByProgram) {
            return Comparator.comparing(StudyRecord::radxProgram)
                             .thenComparing(StudyRecord::phsNumber);
        }
        else {
            return Comparator.comparing(StudyRecord::phsNumber);
        }
    }

    private String formatRecord(StudyRecord rec) {
        var startDate = rec.getLocalStartDate().map(Object::toString).orElse("");
        var endDate = rec.getLocalEndDate().map(Object::toString).orElse("");
        return String.format(FORMAT, rec.phsNumber(), rec.radxProgram(), startDate, endDate, rec.projectTitle());
    }
}
