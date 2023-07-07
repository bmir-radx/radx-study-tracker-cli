package edu.stanford.radx.studytracker;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-06
 *
 * Component for parsing CSV data into a list of StudyRecord objects.
 */
@Component
public class StudyCsvParser {

    /**
     * Parses the provided CSV data into a list of StudyRecord objects.
     *
     * @param csv the CSV data to parse
     * @return a list of StudyRecord objects parsed from the CSV data
     * @throws IOException if an I/O error occurs while reading the CSV data
     */
    @Nonnull
    public List<StudyRecord> parse(@Nonnull String csv) throws IOException {
        var csvMapper = new CsvMapper();
        var headerSchema = CsvSchema.emptySchema().withHeader();
        MappingIterator<StudyRecord> it = csvMapper
                .readerFor(StudyRecord.class)
                .with(headerSchema)
                .readValues(csv);
        return it.readAll();
    }
}

