package edu.stanford.radx.studytracker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Represents a RADx study with various properties.
 *
 * @param phsNumber      the PHS number of the study
 * @param radxProgram    the RADx program associated with the study
 * @param projectTitle   the title of the study
 * @param studyStartDate the start date of the study in the format "d-MMM-yy" or "M/d/yyyy"
 * @param studyEndDate   the end date of the study in the format "d-MMM-yy" or "M/d/yyyy"
 *
 * @author Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-06
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StudyRecord(@JsonProperty("PHS Number") String phsNumber,
                          @JsonProperty("RADx Program") String radxProgram,
                          @JsonProperty("Project Title") String projectTitle,
                          @JsonProperty("Study Start Date") String studyStartDate,
                          @JsonProperty("Study End Date") String studyEndDate) {


    /**
     * Gets the start date of the study as a {@link LocalDate} object.
     *
     * @return an {@link Optional} containing the start date if it can be parsed, or an
     * empty {@link Optional} if the start date is null, blank, or cannot be parsed
     */
    public Optional<LocalDate> getLocalStartDate() {
        return parseLocalDate(studyStartDate);
    }

    /**
     * Gets the end date of the study as a {@link LocalDate} object.
     *
     * @return an {@link Optional} containing the end date if it can be parsed, or
     * an empty {@link Optional} if the end date is null, blank, or cannot be parsed
     */
    public Optional<LocalDate> getLocalEndDate() {
        return parseLocalDate(studyEndDate);
    }

    private Optional<LocalDate> parseLocalDate(String s) {
        if (s == null) {
            return Optional.empty();
        }
        if (s.isBlank()) {
            return Optional.empty();
        }
        try {
            return Optional.of(LocalDate.parse(s, DateTimeFormatter.ofPattern("d-MMM-yy")));
        } catch (DateTimeParseException e) {
            try {
                return Optional.of(LocalDate.parse(s, DateTimeFormatter.ofPattern("M/d/yyyy")));
            } catch (DateTimeParseException ex) {
                return Optional.empty();
            }
        }
    }
}
