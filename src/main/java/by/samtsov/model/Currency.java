package by.samtsov.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Currency {
    BYN("BYN", "Belarusian ruble"),
    USD("USD", "United States dollar");
    private final String code;
    private final String name;
}
