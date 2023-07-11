package edu.stanford.radx.studytracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;


import static org.assertj.core.api.Assertions.*;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-07
 */

public class StudyRecordTest {

    @Test
    void testGetLocalStartDate_ValidFormat() {
        var record = new StudyRecord("123456", "RADx", "Test Project", "15-Jan-23", "30-Jun-23", "Test Summary");
        var localStartDate = record.getLocalStartDate();
        assertThat(localStartDate).isPresent();
        assertThat(localStartDate).contains(LocalDate.of(2023, 1, 15));
    }

    @Test
    void testGetLocalStartDate_ValidFormat2() {
        var record = new StudyRecord("123456", "RADx", "Test Project", "01/15/2023", "30-Jun-23", "Test Summary");
        var localStartDate = record.getLocalStartDate();
        assertThat(localStartDate).isPresent();
        assertThat(localStartDate).contains(LocalDate.of(2023, 1, 15));
    }

    @Test
    void testGetLocalStartDate_InvalidFormat() {
        var record = new StudyRecord("123456", "RADx", "Test Project", "InvalidDate", "30-Jun-23", "Test Summary");
        var localStartDate = record.getLocalStartDate();
        assertThat(localStartDate).isNotPresent();
    }

    @Test
    void testGetLocalStartDate_NullValue() {
        var record = new StudyRecord("123456", "RADx", "Test Project", null, "30-Jun-23", "Test Summary");
        var localStartDate = record.getLocalStartDate();
        assertThat(localStartDate).isNotPresent();
    }

    @Test
    void testGetLocalStartDate_BlankValue() {
        var record = new StudyRecord("123456", "RADx", "Test Project", "", "30-Jun-23", "Test Summary");
        var localStartDate = record.getLocalStartDate();
        assertThat(localStartDate).isNotPresent();
    }

    @Test
    void testGetLocalEndDate_ValidFormat() {
        var record = new StudyRecord("123456", "RADx", "Test Project", "15-Jan-23", "30-Jun-23", "Test Summary");
        var localEndDate = record.getLocalEndDate();
        assertThat(localEndDate).isPresent();
        assertThat(localEndDate).contains(LocalDate.of(2023, 6, 30));
    }


    @Test
    void testGetLocalEndDate_ValidFormat2() {
        var record = new StudyRecord("123456", "RADx", "Test Project", "15-Jan-23", "06/30/2023", "Test Summary");
        var localEndDate = record.getLocalEndDate();
        assertThat(localEndDate).isPresent();
        assertThat(localEndDate).contains(LocalDate.of(2023, 6, 30));
    }

    @Test
    void testGetLocalEndDate_InvalidFormat() {
        var record = new StudyRecord("123456", "RADx", "Test Project", "15-Jan-23", "InvalidDate", "Test Summary");
        var localEndDate = record.getLocalEndDate();
        assertThat(localEndDate).isNotPresent();
    }

    @Test
    void testGetLocalEndDate_NullValue() {
        var record = new StudyRecord("123456", "RADx", "Test Project", "15-Jan-23", null, "Test Summary");
        var localEndDate = record.getLocalEndDate();
        assertThat(localEndDate).isNotPresent();
    }

    @Test
    void testGetLocalEndDate_BlankValue() {
        var record = new StudyRecord("123456", "RADx", "Test Project", "15-Jan-23", "", "Test Summary");
        var localEndDate = record.getLocalEndDate();
        assertThat(localEndDate).isNotPresent();
    }
}
