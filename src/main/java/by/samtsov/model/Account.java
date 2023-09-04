package by.samtsov.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
public class Account {

    private int id;
    private String ibanCode;
    private Client client;
    private Bank bank;
    private BigDecimal amount;
    private Date openengDate;
    private Currency currency;

}
