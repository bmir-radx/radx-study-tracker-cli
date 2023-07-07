package edu.stanford.radx.studytracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RadxStudyTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadxStudyTrackerApplication.class, args);
	}

	@Bean
	StudyDatabaseFactory studyDatabaseFactory(GoogleSheetsCsvDownloader googleSheetsCsvDownloader) {
		return new StudyDatabaseFactory(Constants.GOOGLE_SHEET_DOC_ID,
										Constants.SHEETS, googleSheetsCsvDownloader);
	}
}
