package org.freestuffbot.api.structures;

/**
 * Represents a type of currency.
 */
public enum Currency {
    USD("$"),
    EUR("€");

    private final String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }


    @Override
    public String toString() {
        return symbol;
    }
}
