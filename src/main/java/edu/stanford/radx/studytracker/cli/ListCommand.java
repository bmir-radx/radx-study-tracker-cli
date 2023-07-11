package edu.stanford.radx.studytracker.cli;

import edu.stanford.radx.studytracker.StudyDatabaseFactory;
import edu.stanford.radx.studytracker.StudyRecord;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Comparator;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-10
 */
@Component
@Command(name = "ls", description = "List all RADx studies that are contained in the RADx study tracker")
public class ListCommand implements CliCommand {


    @Option(names = "--group-by-program")
    protected boolean groupByProgram = false;

    private final StudyDatabaseFactory studyDatabaseFactory;

    private final StudyRecordsPrinter printer;

    public ListCommand(StudyDatabaseFactory studyDatabaseFactory, StudyRecordsPrinter printer) {
        this.studyDatabaseFactory = studyDatabaseFactory;
        this.printer = printer;
    }

    @Override
    public Integer call() throws Exception {
        var studyDatabase = studyDatabaseFactory.getStudyDatabase();
        var allStudyRecords = studyDatabase.getAllStudyRecords();
        System.err.println("-----------------------------");
        System.err.printf("There are %d study records\n", allStudyRecords.size());
        System.err.println("-----------------------------");
        printer.printStudyRecords(allStudyRecords, false);
        return 0;
    }

    private Comparator<StudyRecord> getComparator() {
        if(groupByProgram) {
            return Comparator.comparing(StudyRecord::radxProgram)
                    .thenComparing(StudyRecord::phsNumber);
        }
        else {
            return Comparator.comparing(StudyRecord::phsNumber);
        }
    }

    private String formateRecord(StudyRecord rec) {
        return String.format("%-10s %-10s %s", rec.phsNumber(), rec.radxProgram(), rec.projectTitle());
    }
}
