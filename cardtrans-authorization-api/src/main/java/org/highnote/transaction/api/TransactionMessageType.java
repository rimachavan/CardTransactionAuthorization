package org.highnote.transaction.api;

/**
 * Enum that represents incoming transaction message type.
 *
 * <p> REQUEST - request message identified with code "0100" </p>
 * <p> RESPONSE - response message identified with code "0110" </p>
 * <p> UNKNOWN - message which was not identified by application </p>
 *
 * @author rimachavan
 */
public enum TransactionMessageType {
    REQUEST("0100"),
    RESPONSE("0110"),
    UNKNOWN("");

    private String value;

    TransactionMessageType(String value) {
        this.value = value;
    }

    /**
     * Returns the underlying value of the message type.
     *
     * @return value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns enum representing for a given value.
     *
     * @param value
     * @return enum
     */
    public static TransactionMessageType findByValue(String value) {
        for (TransactionMessageType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
