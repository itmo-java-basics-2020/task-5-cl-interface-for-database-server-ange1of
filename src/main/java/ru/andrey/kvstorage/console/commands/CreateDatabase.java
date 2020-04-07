package ru.andrey.kvstorage.console.commands;

import ru.andrey.kvstorage.console.DatabaseCommand;
import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

import java.util.Optional;

public class CreateDatabase implements DatabaseCommand {
    private static final int ARGS_COUNT = 2;

    private final ExecutionEnvironment environment;
    private final String databaseName;

    public CreateDatabase(ExecutionEnvironment environment, String... args ) {
        if (environment == null) {
            throw new IllegalArgumentException("Execution environment is undefined");
        }
        this.environment = environment;

        if (args.length != ARGS_COUNT) {
            throw new IllegalArgumentException("Wrong arguments count: " + (ARGS_COUNT - 1) + "expected, but " + (Math.max(args.length-1, 0) + "provided"));
        }
        databaseName = args[1];
    }

    public DatabaseCommandResult execute() {
        Optional<Database> databaseObject = environment.getDatabase(databaseName);
        if (databaseObject.isPresent()) {
            return DatabaseCommandResult.success("Database " + databaseName + " already exists");
        }

        //Заглушка для добавления базы
        environment.addDatabase(null);
        return DatabaseCommandResult.success("Database " + databaseName + " created successfully");
    }
}
