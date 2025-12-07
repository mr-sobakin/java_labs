package labs.lab4.v6.deposit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public final class CurrencyConverter {
    private static final Map<Currency, BigDecimal> TO_USD = Map.of(
            Currency.USD, BigDecimal.ONE,
            Currency.EUR, new BigDecimal("1.16"),
            Currency.RUB, new BigDecimal("0.013")
    );

    public static BigDecimal convert(BigDecimal amount, Currency from, Currency to) {
        if (amount == null) throw new IllegalArgumentException("amount");
        if (from == to) return amount;
        BigDecimal inUsd = amount.multiply(TO_USD.get(from));
        BigDecimal toRate = TO_USD.get(to);
        return inUsd.divide(toRate, 10, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
    }
}
