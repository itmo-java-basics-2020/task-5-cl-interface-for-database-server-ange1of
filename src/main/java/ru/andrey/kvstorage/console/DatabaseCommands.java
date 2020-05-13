package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.console.commands.CreateDatabase;
import ru.andrey.kvstorage.console.commands.CreateTable;
import ru.andrey.kvstorage.console.commands.ReadKey;
import ru.andrey.kvstorage.console.commands.UpdateKey;


public enum DatabaseCommands {
    CREATE_DATABASE() {
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) throws IllegalArgumentException {
            return new CreateDatabase(env, args);
        }
    },

    CREATE_TABLE() {
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) throws IllegalArgumentException {
            return new CreateTable(env, args);
        }
    },

    READ_KEY() {
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) throws IllegalArgumentException {
            return new ReadKey(env, args);
        }
    },

    UPDATE_KEY() {
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) throws IllegalArgumentException {
            return new UpdateKey(env, args);
        }
    };


    public abstract DatabaseCommand getCommand(ExecutionEnvironment env, String... args) throws IllegalArgumentException;

    public static DatabaseCommand parseCommand(ExecutionEnvironment env, String commandString) throws IllegalArgumentException {
        if (commandString == null || commandString.isEmpty() || commandString.isBlank()) {
            throw new IllegalArgumentException("Invalid command format");
        }

        // Получаем термы команды
        String[] args = commandString.split(" ");
        String commandName = args[0];

        for (DatabaseCommands command: DatabaseCommands.values()) {
            if (commandName.equals(command.name())) {
                // Может выкинуть IllegalArgumentException, пробрасываем дальше
                return command.getCommand(env, args);
            }
        }

        throw new IllegalArgumentException(String.format("Unknown command type: %s", commandName));
    }
}
