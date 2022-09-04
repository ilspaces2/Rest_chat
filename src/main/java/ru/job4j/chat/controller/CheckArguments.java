package ru.job4j.chat.controller;

public interface CheckArguments {

    default void checkArgumentId(int id, String msgException) {
        if (id <= 0) {
            throw new NullPointerException(msgException);
        }
    }

    default void checkArgumentIsBlank(String arg, String msgException) {
        if (arg.isBlank()) {
            throw new NullPointerException(msgException);
        }
    }

}
