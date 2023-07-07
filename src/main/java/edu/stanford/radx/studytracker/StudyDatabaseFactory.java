package edu.stanford.radx.studytracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-07
 *
 * Factory class for creating a StudyDatabase instance by downloading and
 * parsing study records from Google Sheets.
 */
public class StudyDatabaseFactory {

    private static final Logger logger = LoggerFactory.getLogger(StudyDatabaseFactory.class);

    private static final int HEADERS = 2;

    private final String docId;
    private final List<String> sheetNames;
    private final GoogleSheetsCsvDownloader googleSheetCsvDownloader;

    /**
     * Constructs a StudyDatabaseFactory with the specified parameters.
     *
     * @param docId                     the ID of the Google Sheets document containing the study records
     * @param sheetNames                the names of the sheets within the document to download records from
     * @param googleSheetCsvDownloader  the GoogleSheetsCsvDownloader used for downloading the CSV data
     */
    public StudyDatabaseFactory(String docId,
                                List<String> sheetNames,
                                GoogleSheetsCsvDownloader googleSheetCsvDownloader) {
        this.docId = docId;
        this.sheetNames = new ArrayList<>(sheetNames);
        this.googleSheetCsvDownloader = googleSheetCsvDownloader;
    }

    /**
     * Retrieves a StudyDatabase instance by downloading and parsing study records from Google Sheets.
     *
     * @return a StudyDatabase instance containing the downloaded and parsed study records
     */
    public StudyDatabase getStudyDatabase() {
        var allRecords = new ArrayList<StudyRecord>();
        sheetNames.forEach(sheetName -> {
            var recs = getStudyRecordsForDcc(sheetName);
            allRecords.addAll(recs);
        });
        return StudyDatabase.from(allRecords);
    }

    private List<StudyRecord> getStudyRecordsForDcc(String dcc) {
        try {
            var rad = googleSheetCsvDownloader.download(docId, dcc, HEADERS);
            var parser = new StudyCsvParser();
            return parser.parse(rad);
        } catch (IOException e) {
            logger.error("An error occurred when downloading records for " + dcc, e);
            return Collections.emptyList();
        }
    }
}

