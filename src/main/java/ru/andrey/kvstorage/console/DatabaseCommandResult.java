package ru.andrey.kvstorage.console;

import java.util.Optional;

public interface DatabaseCommandResult {

    Optional<String> getResult();

    DatabaseCommandStatus getStatus();

    boolean isSuccess();

    String getErrorMessage();

    static DatabaseCommandResult success(String result) {
        return new BaseDatabaseCommandResult(result, null, DatabaseCommandStatus.SUCCESS);
    }

    static DatabaseCommandResult error(String message) {
        return new BaseDatabaseCommandResult(null, message, DatabaseCommandStatus.FAILED);
    }

    enum DatabaseCommandStatus {
        SUCCESS, FAILED
    }

    final class BaseDatabaseCommandResult implements DatabaseCommandResult {
        private final DatabaseCommandStatus status;
        private final String result;
        private final String errorMessage;

        public BaseDatabaseCommandResult(String result, String error, DatabaseCommandStatus status) {
            this.status = status;
            this.result = isSuccess() ? result : null;
            this.errorMessage = isSuccess() ? null : error;
        }

        public Optional<String> getResult() {
            return Optional.ofNullable(result);
        }

        public DatabaseCommandStatus getStatus() {
            return status;
        }

        public boolean isSuccess() {
            return status == DatabaseCommandStatus.SUCCESS;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

    }
}