package edu.stanford.radx.studytracker.cli;

import edu.stanford.radx.studytracker.StudyDatabaseFactory;
import edu.stanford.radx.studytracker.StudyRecord;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static picocli.CommandLine.*;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-10
 */
@Component
@Command(name = "find", description = "Finds a study by searching the specified fields")
public class FindCommand implements CliCommand {

    @Option(names = "--any-match", description = "A search string that must be contained within either the PHS Number or Study Title fields")
    protected String anyFieldSpec = "";

    @Option(names = "--phs-match", description = "A search string that must be contained within the PHS Number field")
    protected String phsFieldSpec = "";




    @Option(names = "--group-by-program", description = "Group results by RADx program")
    protected boolean groupByProgram = false;

    @Option(names = "--programs", description = "Find by program.  List one or more programs to match.  If this option is not present")
    List<RadXProgram> programs = new ArrayList<>();


    private final StudyRecordsPrinter printer;

    private final StudyDatabaseFactory studyDatabaseFactory;


    public FindCommand(StudyRecordsPrinter printer, StudyDatabaseFactory studyDatabaseFactory) {
        this.printer = printer;
        this.studyDatabaseFactory = studyDatabaseFactory;
    }


    @Override
    public Integer call() throws Exception {
        var studyDatabase = studyDatabaseFactory.getStudyDatabase();
        var allStudyRecords = studyDatabase.getAllStudyRecords();
        var recs = allStudyRecords.stream()
                .filter(this::matches)
                .toList();
        printer.printStudyRecords(recs, groupByProgram);
        return 0;
    }

    private boolean matches(StudyRecord record) {
        if(!programs.isEmpty()) {
            var prog = record.getRadXProgram();
            if(!programs.contains(prog.orElse(null))) {
                return false;
            }
        }
        if (!phsFieldSpec.isEmpty()) {
            return record.phsNumber().toLowerCase().contains(phsFieldSpec.toLowerCase());
        }
        if (!anyFieldSpec.isEmpty()) {
            return record.phsNumber().toLowerCase().contains(anyFieldSpec.toLowerCase())
                    || record.projectTitle().toLowerCase().contains(anyFieldSpec.toLowerCase())
                    || Objects.requireNonNullElse(record.summary(), "").toLowerCase().contains(anyFieldSpec.toLowerCase());
        }
        return true;
    }
}
