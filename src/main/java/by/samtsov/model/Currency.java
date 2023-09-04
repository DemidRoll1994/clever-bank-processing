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
    public static Currency getByIndex(int index){
        return values()[index];
    }
    public static int getIndex(Currency currency){
        for (int index = 0; index < values().length; index++) {
            if (currency== values()[index])
                return index;
        }
        return -1;
    }
}
