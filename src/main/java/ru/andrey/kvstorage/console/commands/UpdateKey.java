package ru.andrey.kvstorage.console.commands;

import ru.andrey.kvstorage.console.DatabaseCommand;
import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

import java.util.Optional;

public class UpdateKey implements DatabaseCommand {
    private static final int ARGS_COUNT = 5;

    private final ExecutionEnvironment environment;
    private final String databaseName;
    private final String tableName;
    private final String key;
    private final String value;

    public UpdateKey(ExecutionEnvironment environment, String... args) {
        if (environment == null) {
            throw new IllegalArgumentException("Execution environment is undefined");
        }
        this.environment = environment;

        if (args.length != ARGS_COUNT) {
            throw new IllegalArgumentException("Wrong arguments count: " + (ARGS_COUNT - 1) + "expected, but " + (Math.max(args.length-1, 0) + "provided"));
        }
        this.databaseName = args[1];
        this.tableName = args[2];
        this.key = args[3];
        this.value = args[4];
    }

    public DatabaseCommandResult execute() {
        Optional<Database> databaseObject = environment.getDatabase(databaseName);
        if (databaseObject.isEmpty()) {
            return DatabaseCommandResult.error("Database " + databaseName + " does not exist");
        }

        try {
            databaseObject.get().write(tableName, key, value);
            return DatabaseCommandResult.success(null);
        }
        catch (DatabaseException e) {
            return DatabaseCommandResult.error(e.getMessage());
        }
    }
}
