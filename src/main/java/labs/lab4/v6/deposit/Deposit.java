package labs.lab4.v6.deposit;

import java.math.BigDecimal;

public interface Deposit {
    BigDecimal getBalance();
    Currency getCurrency();
    boolean isWithdrawable();
    DurationType getDurationType();
    void changeDuration(DurationType newDuration);
    BigDecimal calculateInterestForMonths(int months);
    boolean isActive();
    void close();
    Deposit closeAndOpenNew(Currency newCurrency);
    String info();
}
