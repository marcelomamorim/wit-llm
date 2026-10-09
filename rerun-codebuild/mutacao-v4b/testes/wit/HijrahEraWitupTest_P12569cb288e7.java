/* @test
   @summary Verifica comportamento do método of de HijrahEra com entradas válidas e inválidas.
   @run main HijrahEraWitupTest_P12569cb288e7
 */
import java.time.chrono.HijrahEra;
import java.time.DateTimeException;

public class HijrahEraWitupTest_P12569cb288e7 {
    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
    }

    static void testValidEra() {
        HijrahEra era = HijrahEra.of(1);
        if (era != null && era == HijrahEra.AH) {
            System.out.println("PASS: Valid era 1 returns AH");
        } else {
            throw new AssertionError("FAIL: Valid era 1 did not return AH");
        }
    }

    static void testInvalidEra() {
        boolean exceptionThrown = false;
        try {
            HijrahEra.of(0);
            throw new AssertionError("FAIL: Invalid era 0 did not throw exception");
        } catch (DateTimeException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("FAIL: Exception not thrown for era 0");
        }
        exceptionThrown = false;
        try {
            HijrahEra.of(2);
            throw new AssertionError("FAIL: Invalid era 2 did not throw exception");
        } catch (DateTimeException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("FAIL: Exception not thrown for era 2");
        }
        System.out.println("PASS: Invalid eras 0 and 2 throw DateTimeException");
    }
}
