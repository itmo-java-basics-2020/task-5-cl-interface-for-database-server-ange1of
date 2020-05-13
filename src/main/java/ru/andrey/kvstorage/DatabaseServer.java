package ru.andrey.kvstorage;

import ru.andrey.kvstorage.console.DatabaseCommand;
import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.DatabaseCommands;
import ru.andrey.kvstorage.console.ExecutionEnvironment;

public class DatabaseServer {

    private final ExecutionEnvironment environment;

    public DatabaseServer(ExecutionEnvironment env) {
        this.environment = env;
    }

    public static void main(String[] args) {}

    DatabaseCommandResult executeNextCommand(String commandText) {
        try {
            DatabaseCommand command = DatabaseCommands.parseCommand(environment, commandText);
            return command.execute();
        }
        catch (IllegalArgumentException e) {
            return DatabaseCommandResult.error(e.getMessage());
        }
    }
}
