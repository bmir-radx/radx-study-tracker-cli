package edu.stanford.radx.studytracker.cli;

import edu.stanford.radx.studytracker.StudyDatabaseFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static picocli.CommandLine.*;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-02
 */
@Component
@Command(name = "dir", description = "Lists the subdirectories in a given directory and prints study information for directories that match the pattern phs000000")
public class DirectoryCommand implements CliCommand {

    private final StudyDatabaseFactory studyDatabaseFactory;

    private final StudyRecordsPrinter studyRecordsPrinter;

    @Option(names = "--directory", required = true)
    protected Path directory;

    public DirectoryCommand(StudyDatabaseFactory studyDatabaseFactory, StudyRecordsPrinter studyRecordsPrinter) {
        this.studyDatabaseFactory = studyDatabaseFactory;
        this.studyRecordsPrinter = studyRecordsPrinter;
    }

    @Override
    public Integer call() throws Exception {
        var studyDb = studyDatabaseFactory.getStudyDatabase();
        var recs = Files.list(directory)
                .filter(Files::isDirectory)
                .map(Path::getFileName)
                .map(Path::toString)
                .filter(directoryName -> directoryName.matches("phs\\d+"))
                .map(studyDb::getStudyRecord)
                .flatMap(Optional::stream)
                .toList();
        studyRecordsPrinter.printStudyRecords(recs, false);
        return null;
    }
}
