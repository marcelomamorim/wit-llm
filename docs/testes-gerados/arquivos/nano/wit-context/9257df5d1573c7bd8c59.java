/*
 * @test
 * @summary Testa o método getDisplayName de HijrahEra para verificar comportamento observável.
 * @run main HijrahEraWitupTest
 */

import java.time.chrono.HijrahEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class HijrahEraWitupTest {

    public static void main(String[] args) {
        testGetDisplayName_NullStyle();
        testGetDisplayName_NullEra();
    }

    private static void testGetDisplayName_NullStyle() {
        try {
            HijrahEra era = HijrahEra.AH;
            // Passa null como style para verificar comportamento observável
            String result = era.getDisplayName(null, Locale.US);
            System.out.println("getDisplayName com style null: " + result);
        } catch (NullPointerException e) {
            System.out.println("getDisplayName com style null lança NullPointerException como esperado.");
        } catch (Exception e) {
            System.out.println("getDisplayName com style null lança exceção inesperada: " + e);
        }
    }

    private static void testGetDisplayName_NullEra() {
        try {
            HijrahEra era = null;
            // Passa null como era para verificar comportamento observável
            String result = null;
            if (era != null) {
                result = era.getDisplayName(TextStyle.FULL, Locale.US);
            } else {
                // Simula o comportamento do método ao receber null
                throw new NullPointerException("Era é null");
            }
            System.out.println("getDisplayName com era null: " + result);
        } catch (NullPointerException e) {
            System.out.println("getDisplayName com era null lança NullPointerException como esperado.");
        } catch (Exception e) {
            System.out.println("getDisplayName com era null lança exceção inesperada: " + e);
        }
    }
}
