package labs.lab4.v6.deposit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class BankDeposit implements Deposit {
    private final String id;
    private BigDecimal balance;
    private Currency currency;
    private boolean withdrawable;
    private DurationType durationType;
    private BigDecimal annualRate;
    private boolean active;

    public BankDeposit(BigDecimal balance, Currency currency, boolean withdrawable, DurationType durationType) {
        this.id = UUID.randomUUID().toString();
        this.balance = balance.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
        this.withdrawable = withdrawable;
        this.durationType = durationType;
        this.annualRate = defaultRateFor(durationType);
        this.active = true;
    }

    private static BigDecimal defaultRateFor(DurationType d) {
        return switch (d) {
            case PERPETUAL -> new BigDecimal("0.015");
            case LONG_TERM -> new BigDecimal("0.06");
            case SHORT_TERM -> new BigDecimal("0.03");
        };
    }

    @Override
    public BigDecimal getBalance() { return balance; }

    @Override
    public Currency getCurrency() { return currency; }

    @Override
    public boolean isWithdrawable() { return withdrawable; }

    @Override
    public DurationType getDurationType() { return durationType; }

    @Override
    public void changeDuration(DurationType newDuration) {
        if (!active) throw new IllegalStateException("deposit is closed");
        this.durationType = newDuration;
        this.annualRate = defaultRateFor(newDuration);
    }

    @Override
    public BigDecimal calculateInterestForMonths(int months) {
        if (months < 0) throw new IllegalArgumentException("months must be >= 0");
        BigDecimal period = new BigDecimal(months).divide(new BigDecimal(12), 10, RoundingMode.HALF_UP);
        BigDecimal interest = balance.multiply(annualRate).multiply(period);
        return interest.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean isActive() { return active; }

    @Override
    public void close() { this.active = false; }

    @Override
    public Deposit closeAndOpenNew(Currency newCurrency) {
        if (!active) throw new IllegalStateException("already closed");
        BigDecimal newBalance = CurrencyConverter.convert(this.balance, this.currency, newCurrency);
        this.close();
        BankDeposit opened = new BankDeposit(newBalance, newCurrency, this.withdrawable, this.durationType);
        return opened;
    }

    @Override
    public String info() {
        return String.format("Deposit[id=%s, balance=%s %s, withdrawable=%s, duration=%s, rate=%s%%, active=%s]",
                id, balance.toPlainString(), currency, withdrawable, durationType, annualRate.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP), active);
    }
}
