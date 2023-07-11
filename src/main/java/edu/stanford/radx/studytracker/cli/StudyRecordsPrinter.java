package edu.stanford.radx.studytracker.cli;

import edu.stanford.radx.studytracker.StudyRecord;
import org.springframework.stereotype.Component;

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

    public void printStudyRecords(List<StudyRecord> records,
                                  boolean groupByProgram) {
        System.err.printf("There are %d records\n", records.size());
        System.err.printf(FORMAT, "PHS Number", "Program", "Start", "End", "Title");
        records
                .stream()
                .sorted(getComparator(groupByProgram))
                .map(this::formateRecord)
                .forEach(System.err::println);
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

    private String formateRecord(StudyRecord rec) {
        var startDate = rec.getLocalStartDate().map(Object::toString).orElse("");
        var endDate = rec.getLocalEndDate().map(Object::toString).orElse("");
        return String.format(FORMAT, rec.phsNumber(), rec.radxProgram(), startDate, endDate, rec.projectTitle());
    }
}
