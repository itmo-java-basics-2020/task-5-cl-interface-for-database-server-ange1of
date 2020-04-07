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
            throw new IllegalArgumentException("Wrong arguments count: " + (ARGS_COUNT - 1) + "expected, but " + (Math.max(args.length-1, 0) + "provided"));
        }
        this.databaseName = args[1];
        this.tableName = args[2];
    }

    public DatabaseCommandResult execute() {
        Optional<Database> databaseObject = environment.getDatabase(databaseName);
        if (databaseObject.isEmpty()) {
            return DatabaseCommandResult.error("Database " + databaseName + " does not exist");
        }

        try {
            databaseObject.get().createTableIfNotExists(tableName);
            return DatabaseCommandResult.success("Table " + tableName + " in database " + databaseName + " created successfully");
        }
        catch (DatabaseException e) {
            return DatabaseCommandResult.error(e.getMessage());
        }
    }
}
