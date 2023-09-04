package by.samtsov.view;

import by.samtsov.model.Client;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleUtil {

    private static PrintStream ps =
            new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8);
    private static Scanner in = new Scanner(System.in);
    String menu[] = new String[]
            {"выберите клиента:", """
             выполнить перевод -1
             Пополнить счет-2
             Снять со счета-3""", """
             Выберите способ поиска клиента, которому нужно осуществить перевод:
             1. по номеру счета
             2. по id клиента """,
                    "Введите сумму:",
                    "подтвердите операцию:"
            };

    public static void requestAction() {

        Client cl = Client.builder().build();
        System.out.printf("%d| %s |%s |%s \n",
                cl.getId(), cl.getSurname(), cl.getName(), cl.getPatronymic());
        String puk = String.format("%d| %s |%s |%s \n",
                cl.getId(), cl.getSurname(), cl.getName(),
                cl.getPatronymic());
        System.out.println(puk);
        PrintStream ps = new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8);
        ps.println(puk); // works
    }

    public static void putMessage(String question) {
        ps.println(question);
    }


    public static String getMessage() {
        String answer = in.nextLine();
        return answer;
    }
}
