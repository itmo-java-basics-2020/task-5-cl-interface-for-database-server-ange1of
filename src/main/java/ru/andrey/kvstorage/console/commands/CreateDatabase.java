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
            throw new IllegalArgumentException(String.format("Wrong arguments count: %d expected, but %d provided",
                    ARGS_COUNT - 1, Math.max(args.length-1, 0)));
        }
        databaseName = args[1];
    }

    @Override
    public DatabaseCommandResult execute() {
        Optional<Database> databaseObject = environment.getDatabase(databaseName);
        if (databaseObject.isPresent()) {
            return DatabaseCommandResult.success(String.format("Database %s already exists", databaseName));
        }

        // Заглушка для добавления базы
        environment.addDatabase(null);
        return DatabaseCommandResult.success(String.format("Database %s created successfully", databaseName));
    }
}
