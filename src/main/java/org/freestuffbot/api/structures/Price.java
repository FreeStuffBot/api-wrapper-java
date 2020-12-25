package org.freestuffbot.api.structures;

import static org.freestuffbot.api.structures.Currency.USD;

public class Price {
    /**
     * The price estimated in euros.
     */
    public double euro;
    /**
     * The price estimated in dollars.
     */
    public double dollar;

    /**
     * Gets the price of the game in the requested currency.
     *
     * @param currency The currency to use.
     * @return The price of the game in the requested currency.
     */
    public double inCurrency(Currency currency) {
        return currency == USD ? dollar : euro;
    }

    /**
     * Converts the price into a string with a specific currency.
     *
     * @param currency The currency to use.
     * @return The formatted string.
     */
    public String toString(Currency currency) {
        return currency.getSymbol() + inCurrency(currency);
    }

}
