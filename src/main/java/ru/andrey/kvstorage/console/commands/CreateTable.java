package ru.andrey.kvstorage.console.commands;

import ru.andrey.kvstorage.console.DatabaseCommand;
import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

import javax.xml.crypto.Data;
import java.util.Optional;

public class CreateTable implements DatabaseCommand {
    private static final int ARGS_COUNT = 3;

    private final ExecutionEnvironment environment;
    private final String databaseName;
    private final String tableName;

    public CreateTable(ExecutionEnvironment environment, String... args) {
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
    }

    @Override
    public DatabaseCommandResult execute() {
        Optional<Database> databaseObject = environment.getDatabase(databaseName);
        if (databaseObject.isEmpty()) {
            return DatabaseCommandResult.error(String.format("Database %s does not exist", databaseName));
        }

        try {
            databaseObject.get().createTableIfNotExists(tableName);
            return DatabaseCommandResult.success(String.format("Table %s in database %s created successfully", tableName, databaseName));
        }
        catch (DatabaseException e) {
            return DatabaseCommandResult.error(e.getMessage());
        }
    }
}
