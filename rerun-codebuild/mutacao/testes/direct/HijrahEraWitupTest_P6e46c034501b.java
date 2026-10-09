/* @test    @summary Test for HijrahEra.of(int) method to ensure correct behavior and exception handling.    @run main HijrahEraWitupTest_P6e46c034501b */

import java.time.chrono.HijrahEra;
import java.time.DateTimeException;

public class HijrahEraWitupTest_P6e46c034501b {

    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
    }

    private static void testValidEra() {
        HijrahEra era = HijrahEra.of(1);
        assert era == HijrahEra.AH : "Expected AH for input 1, but got " + era;
    }

    private static void testInvalidEra() {
        boolean exceptionThrown = false;
        try {
            HijrahEra.of(0);
        } catch (DateTimeException e) {
            exceptionThrown = true;
            assert e.getMessage().contains("Invalid era") : "Unexpected message: " + e.getMessage();
        }
        assert exceptionThrown : "Expected DateTimeException for input 0";

        exceptionThrown = false;
        try {
            HijrahEra.of(2);
        } catch (DateTimeException e) {
            exceptionThrown = true;
            assert e.getMessage().contains("Invalid era") : "Unexpected message: " + e.getMessage();
        }
        assert exceptionThrown : "Expected DateTimeException for input 2";
    }
}
