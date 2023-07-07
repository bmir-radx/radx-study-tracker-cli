package edu.stanford.radx.studytracker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;


/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-07
 */
public class GoogleSheetsUrlTemplateTest {

    private GoogleSheetsUrlTemplate template = new GoogleSheetsUrlTemplate();

    @Test
    public void testGet_withValidParameters_returnsExpectedUrl() {
        var docId = "123456789";
        var sheetId = "Sheet1";
        var headers = 1;
        var expectedUrl = "https://docs.google.com/spreadsheets/d/123456789/gviz/tq?tqx=out:csv&headers=1&sheet=Sheet1";
        var actualUrl = template.get(docId, sheetId, headers);
        assertThat(actualUrl).isEqualTo(expectedUrl);
    }

    @Test
    public void testGet_withEmptyDocId_returnsExpectedUrl() {
        var docId = "";
        var sheetId = "Sheet1";
        var headers = 1;
        var expectedUrl = "https://docs.google.com/spreadsheets/d//gviz/tq?tqx=out:csv&headers=1&sheet=Sheet1";
        var actualUrl = template.get(docId, sheetId, headers);
        assertThat(actualUrl).isEqualTo(expectedUrl);
    }

    @Test
    public void testGet_withNegativeHeaders_returnsExpectedUrl() {
        var docId = "123456789";
        var sheetId = "Sheet1";
        var headers = -1;
        var expectedUrl = "https://docs.google.com/spreadsheets/d/123456789/gviz/tq?tqx=out:csv&headers=-1&sheet=Sheet1";
        var actualUrl = template.get(docId, sheetId, headers);
        assertThat(actualUrl).isEqualTo(expectedUrl);
    }

    @Test
    public void testGet_withNullDocId_throwsNullPointerException() {
        String docId = null;
        var sheetId = "Sheet1";
        var headers = 1;
        assertThatNullPointerException().isThrownBy(() -> template.get(docId, sheetId, headers));
    }

    @Test
    public void testGet_withNullSheetId_throwsNullPointerException() {
        var docId = "123456789";
        String sheetId = null;
        var headers = 1;
        assertThatNullPointerException().isThrownBy(() -> template.get(docId, sheetId, headers));
    }

    @Test
    public void testGet_withNullBothDocIdAndSheetId_throwsNullPointerException() {
        String docId = null;
        String sheetId = null;
        var headers = 1;

        assertThatNullPointerException().isThrownBy(() -> template.get(docId, sheetId, headers));
    }


}
