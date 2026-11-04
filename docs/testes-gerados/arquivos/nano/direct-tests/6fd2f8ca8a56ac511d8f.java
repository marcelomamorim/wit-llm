/* @test    @summary Test ValueRange.of with valid parameters    @run main ValueRangeWitupTest */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testOfMethodWithValidParameters();
        testOfMethodWithInvalidParameters();
    }

    static void testOfMethodWithValidParameters() {
        ValueRange vr = ValueRange.of(1L, 5L, 10L, 20L);
        assert vr != null : "ValueRange should not be null";
    }

    static void testOfMethodWithInvalidParameters() {
        boolean exceptionThrown = false;
        try {
            ValueRange.of(10L, 5L, 20L, 15L);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for invalid parameters";
    }
}
