/* @test    @summary Test for HijrahEra.of(int) method to verify correct behavior and exception handling.    @run main HijrahEraWitupTest_Pced8fbbdf385 */

import java.time.chrono.HijrahEra;
import java.time.DateTimeException;

public class HijrahEraWitupTest_Pced8fbbdf385 {

    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
    }

    private static void testValidEra() {
        HijrahEra era = HijrahEra.of(1);
        if (era != null && era == HijrahEra.AH) {
            System.out.println("PASS: Valid era returned correctly.");
        } else {
            throw new AssertionError("FAIL: Valid era did not return expected value.");
        }
    }

    private static void testInvalidEra() {
        try {
            HijrahEra.of(0);
            throw new AssertionError("FAIL: Expected DateTimeException for input 0.");
        } catch (DateTimeException e) {
            String message = e.getMessage();
            if (message != null && message.contains("Invalid era")) {
                System.out.println("PASS: Exception thrown correctly for input 0.");
            } else {
                throw new AssertionError("FAIL: Exception message unexpected for input 0.");
            }
        } catch (Exception e) {
            throw new AssertionError("FAIL: Unexpected exception type for input 0: " + e);
        }

        try {
            HijrahEra.of(2);
            throw new AssertionError("FAIL: Expected DateTimeException for input 2.");
        } catch (DateTimeException e) {
            String message = e.getMessage();
            if (message != null && message.contains("Invalid era")) {
                System.out.println("PASS: Exception thrown correctly for input 2.");
            } else {
                throw new AssertionError("FAIL: Exception message unexpected for input 2.");
            }
        } catch (Exception e) {
            throw new AssertionError("FAIL: Unexpected exception type for input 2: " + e);
        }
    }
}
