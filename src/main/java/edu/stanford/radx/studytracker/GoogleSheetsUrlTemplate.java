package edu.stanford.radx.studytracker;


import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-07
 */
@Component
public class GoogleSheetsUrlTemplate {

    private static final String URL_TEMPLATE = "https://docs.google.com/spreadsheets/d/${docId}/gviz/tq?tqx=out:csv&headers=${headers}&sheet=${sheetId}";

    public String get(@Nonnull String docId,
                             @Nonnull String sheetId,
                             int headers) {
        Objects.requireNonNull(docId);
        Objects.requireNonNull(sheetId);
        return  URL_TEMPLATE.replace("${docId}", docId)
                                           .replace("${sheetId}", sheetId)
                                            .replace("${headers}", Integer.toString(headers));

    }

}
