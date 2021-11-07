package com.admin.useradministration.exception;


class UseradministrationException extends  Exception {

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    UseradministrationException(String message) {
        this.message = message;
    }
}

