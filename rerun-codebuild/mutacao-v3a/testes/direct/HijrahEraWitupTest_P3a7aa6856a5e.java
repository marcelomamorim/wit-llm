/*
 * @test
 * @summary Test for HijrahEra.of(int) method.
 * @run main HijrahEraWitupTest_P3a7aa6856a5e
 */
import java.time.chrono.HijrahEra;
import java.time.DateTimeException;

public class HijrahEraWitupTest_P3a7aa6856a5e {
    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
    }

    static void testValidEra() {
        HijrahEra era = HijrahEra.of(1);
        assert era == HijrahEra.AH : "Expected AH for input 1";
    }

    static void testInvalidEra() {
        try {
            HijrahEra.of(0);
            assert false : "Expected DateTimeException for input 0";
        } catch (DateTimeException e) {
            // expected
        }
        try {
            HijrahEra.of(2);
            assert false : "Expected DateTimeException for input 2";
        } catch (DateTimeException e) {
            // expected
        }
        try {
            HijrahEra.of(-1);
            assert false : "Expected DateTimeException for input -1";
        } catch (DateTimeException e) {
            // expected
        }
    }
}
