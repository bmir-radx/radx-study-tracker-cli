package edu.stanford.radx.studytracker;

import java.util.List;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-07
 */
public interface Constants {

    String GOOGLE_SHEET_DOC_ID = "1zBXU_bSPZBJuUFyzmmPgGTqSR0Xdk3IrQOGZ8tv5k-M";

    String RADX_RAD_SHEET_NAME = "RADx-rad";

    String RADX_TECH_SHEET_NAME = "RADx-Tech";

    String RADX_UP_SHEET_NAME = "RADx-UP";

    String RADX_DHT_SHEET_NAME = "RADx-DHT";

    List<String> SHEETS = List.of(RADX_RAD_SHEET_NAME,
                                  RADX_TECH_SHEET_NAME,
                                  RADX_UP_SHEET_NAME,
                                  RADX_DHT_SHEET_NAME);
}
