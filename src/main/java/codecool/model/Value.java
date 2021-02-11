package codecool.model;

import java.math.BigDecimal;

public enum Value {
    NICKEL("0.05"),
    DIME("0.10"),
    QUARTER("0.25"),
    NOT_WORTHY("0");

    private final BigDecimal value;

    Value(String value) {

        this.value = new BigDecimal(value);
    }

    public BigDecimal getValue() {
        return value;
    }
}
