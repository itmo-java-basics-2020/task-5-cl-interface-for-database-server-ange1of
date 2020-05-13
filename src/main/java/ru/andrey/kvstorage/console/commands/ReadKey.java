package ru.andrey.kvstorage.console.commands;

import ru.andrey.kvstorage.console.DatabaseCommand;
import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

import java.util.Optional;

public class ReadKey implements DatabaseCommand {
    private static final int ARGS_COUNT = 4;

    private final ExecutionEnvironment environment;
    private final String databaseName;
    private final String tableName;
    private final String key;

    public ReadKey(ExecutionEnvironment environment, String... args) {
        if (environment == null) {
            throw new IllegalArgumentException("Execution environment is undefined");
        }
        this.environment = environment;

        if (args.length != ARGS_COUNT) {
            throw new IllegalArgumentException(String.format("Wrong arguments count: %d expected, but %d provided",
                    ARGS_COUNT - 1, Math.max(args.length-1, 0)));
        }
        this.databaseName = args[1];
        this.tableName = args[2];
        this.key = args[3];
    }

    @Override
    public DatabaseCommandResult execute() {
        Optional<Database> databaseObject = environment.getDatabase(databaseName);
        if (databaseObject.isEmpty()) {
            return DatabaseCommandResult.error(String.format("Database %s does not exist", databaseName));
        }

        try {
            String value = databaseObject.get().read(tableName, key);
            return DatabaseCommandResult.success(value);
        }
        catch (DatabaseException e) {
            return DatabaseCommandResult.error(e.getMessage());
        }
    }
}
