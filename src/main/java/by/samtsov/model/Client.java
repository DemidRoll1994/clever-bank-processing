package by.samtsov.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Client {
    private int id;
    private String surname;
    private String name;
    private String patronymic;

}
