package edu.stanford.radx.studytracker.cli;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-07-10
 */
@Component
public class CliRunner {

    private final CommandLine.IFactory factory;

    private final List<CliCommand> commandList;

    public CliRunner(CommandLine.IFactory factory, List<CliCommand> commandList) {
        this.factory = factory;
        this.commandList = commandList;
    }

    public Integer execute(String [] args) throws Exception {
        var cli = new CommandLine(new StudyTrackerParentCommand(), factory);
        commandList.forEach(cli::addSubcommand);
        return cli.execute(args);
    }
}
