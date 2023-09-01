package by.samtsov.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
public class Transaction {
    private int id;
    private Account accountFrom;
    private Account accountTo;
    private BigDecimal amount;
    private Date time;
    private TransactionType transactionType;
}
