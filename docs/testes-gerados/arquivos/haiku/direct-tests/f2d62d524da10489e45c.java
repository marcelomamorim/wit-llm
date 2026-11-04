/*
 * @test
 * @summary Verify that Field.getDouble on a long field returns the widened long value via the unsafe long accessor
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    private long longValue;
    private int intValue;

    public static void main(String[] args) throws Exception {
        UnsafeLongFieldAccessorImplWitupTest holder = new UnsafeLongFieldAccessorImplWitupTest();
        Field longField = UnsafeLongFieldAccessorImplWitupTest.class.getDeclaredField("longValue");
        longField.setAccessible(true);

        long[] samples = {0L, 1L, -1L, 42L, Long.MIN_VALUE, Long.MAX_VALUE};
        for (long sample : samples) {
            longField.setLong(holder, sample);
            double actual = longField.getDouble(holder);
            double expected = (double) sample;
            if (Double.compare(actual, expected) != 0) {
                throw new RuntimeException("getDouble mismatch for " + sample
                        + ": expected " + expected + " but was " + actual);
            }
        }

        longField.setLong(holder, 123456789012345L);
        double first = longField.getDouble(holder);
        double second = longField.getDouble(holder);
        if (Double.compare(first, second) != 0) {
            throw new RuntimeException("getDouble is not repeatable: " + first + " vs " + second);
        }
        if (Double.compare(first, 123456789012345.0d) != 0) {
            throw new RuntimeException("unexpected getDouble result: " + first);
        }

        Field intField = UnsafeLongFieldAccessorImplWitupTest.class.getDeclaredField("intValue");
        intField.setAccessible(true);
        intField.setInt(holder, 7);
        if (intField.getDouble(holder) != 7.0d) {
            throw new RuntimeException("int field widening to double failed");
        }

        boolean rejected = false;
        try {
            longField.getDouble("not an instance of the declaring class");
        } catch (IllegalArgumentException expectedException) {
            rejected = true;
        }
        if (!rejected) {
            throw new RuntimeException("expected IllegalArgumentException for wrong receiver type");
        }
    }
}
