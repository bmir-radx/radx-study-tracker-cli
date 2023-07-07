package edu.stanford.radx.studytracker;

import jakarta.annotation.Nonnull;

import java.util.*;

/**
 * Represents a study database containing study records indexed by PHS number.
 */
public class StudyDatabase {

    private final Map<String, StudyRecord> recordsByPhsNumber;

    private StudyDatabase(Map<String, StudyRecord> recordsByPhsNumber) {
        this.recordsByPhsNumber = recordsByPhsNumber;
    }

    /**
     * Creates a new instance of StudyDatabase from a list of study records.
     *
     * @param studyRecords the list of study records to populate the database with
     * @return a new StudyDatabase instance containing the study records
     * @throws NullPointerException if the studyRecords parameter is null
     */
    public static StudyDatabase from(@Nonnull List<StudyRecord> studyRecords) {
        Objects.requireNonNull(studyRecords);
        var recs = new HashMap<String, StudyRecord>();
        studyRecords.forEach(r -> recs.put(r.phsNumber(), r));
        return new StudyDatabase(recs);
    }

    /**
     * Retrieves the study record associated with the specified PHS number from the database.
     *
     * @param phsNumber the PHS number of the study record to retrieve
     * @return an Optional containing the study record if found, or an empty Optional if not found
     */
    public Optional<StudyRecord> getStudyRecord(String phsNumber) {
        return Optional.ofNullable(recordsByPhsNumber.get(phsNumber));
    }

    /**
     * Retrieves the DCC (Data Coordinating Center) associated with the specified PHS number from
     * the database.
     *
     * @param phsNumber the PHS number of the study record for which to retrieve the DCC
     * @return an Optional containing the DCC if found, or an empty Optional if not found
     */
    public Optional<String> getDcc(String phsNumber) {
        return getStudyRecord(phsNumber).map(StudyRecord::radxProgram);
    }
}