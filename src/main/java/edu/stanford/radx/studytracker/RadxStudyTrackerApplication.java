package edu.stanford.radx.studytracker;

import edu.stanford.radx.studytracker.cli.CliRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RadxStudyTrackerApplication implements CommandLineRunner, ExitCodeGenerator {

	@Autowired
	private ApplicationContext ctx;

	private int exitCode = 0;

	public static void main(String[] args) {
		SpringApplication.run(RadxStudyTrackerApplication.class, args);
	}

	@Bean
	StudyDatabaseFactory studyDatabaseFactory(GoogleSheetsCsvDownloader googleSheetsCsvDownloader) {
		return new StudyDatabaseFactory(Constants.GOOGLE_SHEET_DOC_ID,
										Constants.SHEETS, googleSheetsCsvDownloader);
	}


	@Override
	public void run(String... args) throws Exception {
		var cliRunner = ctx.getBean(CliRunner.class);
		exitCode = cliRunner.execute(args);
	}

	@Override
	public int getExitCode() {
		return exitCode;
	}
}
