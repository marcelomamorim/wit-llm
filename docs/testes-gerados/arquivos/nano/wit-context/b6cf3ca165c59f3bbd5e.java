/* @test    @summary Testa o método format de OffsetDateTime com entrada nula para formatter    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testFormatWithNullFormatter();
    }

    public static void testFormatWithNullFormatter() {
        OffsetDateTime odt = OffsetDateTime.now();
        try {
            odt.format(null);
            System.err.println("Falha: Esperado NullPointerException ao passar formatter nulo.");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("Sucesso: NullPointerException capturada como esperado.");
        }
    }
}
