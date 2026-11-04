/*
 * @test
 * @summary Verifies that Field.getFloat on a long field (served by UnsafeLongFieldAccessorImpl.getFloat) widens the stored long value to float
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    private long value;

    public static void main(String[] args) throws Exception {
        UnsafeLongFieldAccessorImplWitupTest holder = new UnsafeLongFieldAccessorImplWitupTest();
        Field field = UnsafeLongFieldAccessorImplWitupTest.class.getDeclaredField("value");
        field.setAccessible(true);

        long[] samples = {0L, 1L, -1L, 123456789L, Long.MAX_VALUE, Long.MIN_VALUE};
        for (long sample : samples) {
            holder.value = sample;
            float expected = (float) sample;
            float actual = field.getFloat(holder);
            if (Float.compare(expected, actual) != 0) {
                throw new RuntimeException("getFloat mismatch for " + sample
                        + ": expected " + expected + " but was " + actual);
            }
        }

        holder.value = 42L;
        float first = field.getFloat(holder);
        float second = field.getFloat(holder);
        if (Float.compare(first, second) != 0 || Float.compare(first, 42.0f) != 0) {
            throw new RuntimeException("getFloat not stable for repeated reads: " + first + " " + second);
        }
    }
}
