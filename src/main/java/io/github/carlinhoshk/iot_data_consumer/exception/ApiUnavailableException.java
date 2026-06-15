package io.github.carlinhoshk.iot_data_consumer.exception;

public class ApiUnavailableException extends RuntimeException {

    public ApiUnavailableException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
