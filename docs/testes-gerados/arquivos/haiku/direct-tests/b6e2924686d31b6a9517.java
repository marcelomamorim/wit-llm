/*
 * @test
 * @summary JapaneseEra.of(int) must reject out-of-range era values with DateTimeException and return cached instances for accepted values
 * @run main JapaneseEraWitupTest
 */

import java.time.DateTimeException;
import java.time.chrono.JapaneseEra;

public class JapaneseEraWitupTest {

    public static void main(String[] args) {
        testMinIntegerRejected();
        testMaxIntegerRejected();
        testInvalidMessageContainsValue();
        testAcceptedValuesReturnSameInstance();
    }

    static void testMinIntegerRejected() {
        try {
            JapaneseEra.of(Integer.MIN_VALUE);
            throw new AssertionError("expected DateTimeException for MIN_VALUE");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    static void testMaxIntegerRejected() {
        try {
            JapaneseEra.of(Integer.MAX_VALUE);
            throw new AssertionError("expected DateTimeException for MAX_VALUE");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    static void testInvalidMessageContainsValue() {
        int invalid = -1000;
        try {
            JapaneseEra.of(invalid);
            throw new AssertionError("expected DateTimeException for " + invalid);
        } catch (DateTimeException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.startsWith("Invalid era") || !msg.contains(String.valueOf(invalid))) {
                throw new AssertionError("unexpected message: " + msg);
            }
        }
    }

    static void testAcceptedValuesReturnSameInstance() {
        int accepted = 0;
        for (int v = -10; v <= 100; v++) {
            JapaneseEra first;
            try {
                first = JapaneseEra.of(v);
            } catch (DateTimeException e) {
                continue;
            }
            accepted++;
            if (first == null) {
                throw new AssertionError("null era for value " + v);
            }
            JapaneseEra second = JapaneseEra.of(v);
            if (first != second) {
                throw new AssertionError("different instances for value " + v);
            }
        }
        if (accepted == 0) {
            throw new AssertionError("no valid era found in range [-10, 100]");
        }
    }
}
