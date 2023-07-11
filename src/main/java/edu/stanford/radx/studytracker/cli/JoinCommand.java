package edu.stanford.radx.studytracker.cli;

import edu.stanford.radx.studytracker.StudyDatabaseFactory;
import edu.stanford.radx.studytracker.StudyRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-10
 */
@Component
@CommandLine.Command(name = "join", description = "Joins study data into another CSV file based on a column identifier")
public class JoinCommand implements CliCommand {

    @Option(names = "--csv-file", required = true, description = "The path to the CSV file")
    protected Path csvFile;

    @Option(names = "--out", required = true, description = "The path to where the resulting CSV file should be written to")
    protected Path out;

    @Option(names = "--field-id", required = true)
    String fieldId;

    private final StudyDatabaseFactory studyDatabaseFactory;

    public JoinCommand(StudyDatabaseFactory studyDatabaseFactory) {
        this.studyDatabaseFactory = studyDatabaseFactory;
    }

    @Override
    public Integer call() throws Exception {
        var csv = Files.readString(csvFile);
        System.err.println("Read " + csv.length() + " characters");

        var csvParser = new CSVParser(new StringReader(csv),
                                      CSVFormat.DEFAULT);

        System.out.println(csvParser);

        var studyDatabase = studyDatabaseFactory.getStudyDatabase();
        var parsedRecords = csvParser.getRecords();
        var header = parsedRecords.get(0);
        var headerMap = new LinkedHashMap<String, Integer>();
        for(int i = 0; i < header.size(); i++) {
            headerMap.put(header.get(i), i);
        }

        var fieldIndex = headerMap.get(fieldId);
        if(fieldIndex == null) {
            System.err.println("Could not find field: " + fieldId);
            System.exit(1);
        }
        var buffer = new StringBuffer();
        var csvWriter = new CSVPrinter(buffer, CSVFormat.DEFAULT);

        var records = parsedRecords;
        System.err.println("There are " + records.size() + " records");
        var joinedHeaders = new ArrayList<>(parsedRecords.get(0).stream().toList());
        joinedHeaders.add("Program");
        joinedHeaders.add("Title");
        joinedHeaders.add("Summary");
        csvWriter.printRecord(joinedHeaders);
        records.stream()
                .skip(1)
                .map(CSVRecord::toList)
                .forEach(record -> {
                    var phsId = record.get(fieldIndex);
                    var joined = new ArrayList<>(record);
                    if(phsId != null && !phsId.isBlank()) {
                        var r = studyDatabase.getStudyRecord(phsId);
                        r.ifPresentOrElse(theR -> {
                            joined.add(theR.radxProgram());
                            joined.add(theR.projectTitle());
                            joined.add(theR.summary());
                        }, () -> pad(joined));
                    }
                    else {
                        pad(joined);
                    }
                    try {
                        csvWriter.printRecord(joined);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        csvWriter.flush();
        Files.writeString(out, buffer.toString());
        return 0;
    }

    private void pad(ArrayList<String> joined) {
        for(int i = 0; i < StudyRecord.class.getRecordComponents().length; i++) {
            joined.add("");
        }
    }
}
