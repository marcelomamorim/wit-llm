/*
 * @test
 * @summary Testa o método atDate de java.time.LocalTime para entradas nulas.
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.LocalDate;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testAtDateWithNull();
        testAtDateWithValidDate();
    }

    public static void testAtDateWithNull() {
        LocalTime time = LocalTime.of(12, 30);
        try {
            time.atDate(null);
            System.err.println("Erro: Esperado NullPointerException ao passar null para atDate.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Esperado
        }
    }

    public static void testAtDateWithValidDate() {
        LocalTime time = LocalTime.of(15, 45, 30);
        LocalDate date = LocalDate.of(2023, 3, 15);
        LocalTimeWitupTest testInstance = new LocalTimeWitupTest();
        LocalTime result = time.atDate(date);
        if (result == null) {
            System.err.println("Erro: Resultado nulo ao chamar atDate com data válida.");
            System.exit(1);
        }
        // Verifica se o resultado contém a data correta e o tempo original
        if (!result.equals(LocalTime.of(15, 45, 30))) {
            System.err.println("Erro: Resultado inesperado ao chamar atDate com data válida.");
            System.exit(1);
        }
    }
}
