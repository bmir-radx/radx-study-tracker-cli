package edu.stanford.radx.studytracker.cli;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-10
 */
public enum RadXProgram {

    DHT("RADx-DHT"),

    TECH("RADx-Tech"),

    RAD("RADx-rad"),

    UP("RADx-UP");

    RadXProgram(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }
}
