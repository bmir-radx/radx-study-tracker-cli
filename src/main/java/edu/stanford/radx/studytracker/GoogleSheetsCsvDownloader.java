package edu.stanford.radx.studytracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Component for downloading CSV data from Google Sheets.
 */
@Component
public class GoogleSheetsCsvDownloader {

    private static final Logger logger = LoggerFactory.getLogger(GoogleSheetsCsvDownloader.class);

    private final GoogleSheetsUrlTemplate urlTemplate;

    /**
     * Constructs a GoogleSheetsCsvDownloader with the specified GoogleSheetsUrlTemplate.
     *
     * @param urlTemplate the GoogleSheetsUrlTemplate used for generating the download URL
     * @throws NullPointerException if the urlTemplate parameter is null
     */
    public GoogleSheetsCsvDownloader(GoogleSheetsUrlTemplate urlTemplate) {
        this.urlTemplate = Objects.requireNonNull(urlTemplate);
    }

    /**
     * Downloads the CSV data from the specified Google Sheets document and sheet.
     *
     * @param docId   the ID of the Google Sheets document
     * @param sheetId the ID of the sheet within the document
     * @param headers the number of header rows in the sheet
     * @return the downloaded CSV data as a string
     */
    public String download(String docId, String sheetId, int headers) {
        try (var inputStream = getUrl(docId, sheetId, headers).openStream()) {
            var bytes = inputStream.readAllBytes();
            return new String(bytes);
        } catch (IOException ioException) {
            logger.error("An error occurred when downloading the sheet", ioException);
            return "";
        }
    }

    private URL getUrl(String docId, String sheetId, int headers) {
        try {
            var filledTemplate = urlTemplate.get(docId, sheetId, headers);
            return new URL(filledTemplate);
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        }
    }
}

