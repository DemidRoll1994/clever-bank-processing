package by.samtsov.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionType {

    TRANSFER("Перевод"),
    INTEREST_ACCRUAL("начисление процентов"),
    REFILL("Пополнение счёта"),
    WITHDRAW("Снятие со счёта");

    private final String description;
}
