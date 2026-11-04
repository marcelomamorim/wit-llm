/*
 * @test
 * @summary Verify that Field.getLong on a double field reaches UnsafeDoubleFieldAccessorImpl.getLong and throws IllegalArgumentException
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {
    public double d = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeDoubleFieldAccessorImplWitupTest.class.getDeclaredField("d");
        UnsafeDoubleFieldAccessorImplWitupTest t = new UnsafeDoubleFieldAccessorImplWitupTest();

        double dv = f.getDouble(t);
        if (dv != 1.5) {
            throw new RuntimeException("unexpected getDouble value: " + dv);
        }

        boolean thrown = false;
        try {
            long v = f.getLong(t);
            throw new RuntimeException("getLong did not throw, returned: " + v);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException not observed");
        }
    }
}
