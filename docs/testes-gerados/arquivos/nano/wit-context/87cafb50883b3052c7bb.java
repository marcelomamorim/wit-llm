/*
 * @test
 * @summary Testa o método format de LocalDateTime para entrada nula de formatter.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testFormatWithNullFormatter();
        testFormatWithValidFormatter();
    }

    public static void testFormatWithNullFormatter() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30);
        try {
            dateTime.format(null);
            System.err.println("Erro: Esperado NullPointerException ao passar formatter null.");
        } catch (NullPointerException e) {
            System.out.println("Passou: NullPointerException lançado como esperado ao passar formatter null.");
        }
    }

    public static void testFormatWithValidFormatter() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String result = dateTime.format(formatter);
        String expected = "2023-03-15 10:30";
        if (result.equals(expected)) {
            System.out.println("Passou: Formatação com formatter válido retornou o resultado esperado.");
        } else {
            System.err.println("Erro: Resultado da formatação não corresponde ao esperado.");
        }
    }
}
