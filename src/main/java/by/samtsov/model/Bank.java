package by.samtsov.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Bank {
    private int id;
    private String name;
    private String IBANCode;
}
