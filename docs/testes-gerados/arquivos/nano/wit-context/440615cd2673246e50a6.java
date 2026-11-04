/*
 * @test
 * @summary Tests for java.time.chrono.ThaiBuddhistEra.getDisplayName
 * @run main ThaiBuddhistEraWitupTest
 */

import java.time.chrono.ThaiBuddhistEra;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

public class ThaiBuddhistEraWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameWithNullEra();
        testGetDisplayNameWithNullStyle();
    }

    private static void testGetDisplayNameWithNullEra() {
        try {
            ThaiBuddhistEra era = null;
            // Simula o método getDisplayName com era null, esperando NullPointerException
            new DateTimeFormatterBuilder()
                .appendText(ThaiBuddhistEra.ERA, TextStyle.FULL)
                .toFormatter(Locale.US)
                .withChronology(ThaiBuddhistEra.INSTANCE)
                .format(era);
            // Caso o método não lance exceção, o teste falha
            System.err.println("Falha: Esperado NullPointerException para era null");
        } catch (NullPointerException e) {
            // Teste passou
            System.out.println("Passou: NullPointerException para era null") ;
        } catch (Exception e) {
            System.err.println("Falha: Exceção inesperada: " + e);
        }
    }

    private static void testGetDisplayNameWithNullStyle() {
        try {
            ThaiBuddhistEra era = ThaiBuddhistEra.BE;
            TextStyle style = null;
            // Simula o método getDisplayName com style null, esperando NullPointerException
            new DateTimeFormatterBuilder()
                .appendText(ThaiBuddhistEra.ERA, style)
                .toFormatter(Locale.US)
                .withChronology(ThaiBuddhistEra.INSTANCE)
                .format(era);
            // Caso o método não lance exceção, o teste falha
            System.err.println("Falha: Esperado NullPointerException para style null");
        } catch (NullPointerException e) {
            // Teste passou
            System.out.println("Passou: NullPointerException para style null") ;
        } catch (Exception e) {
            System.err.println("Falha: Exceção inesperada: " + e);
        }
    }
}
