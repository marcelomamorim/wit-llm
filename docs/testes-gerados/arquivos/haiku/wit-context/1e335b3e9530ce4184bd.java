/*
 * @test
 * @summary Verify that Field.getFloat on a static double field throws IllegalArgumentException, reaching UnsafeStaticDoubleFieldAccessorImpl.getFloat through the public reflection API
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    public static double value = 1.5d;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        Object boxed = f.get(null);
        if (!Double.valueOf(1.5d).equals(boxed)) {
            throw new AssertionError("Unexpected Field.get value: " + boxed);
        }

        try {
            float v = f.getFloat(null);
            throw new AssertionError("Expected IllegalArgumentException, got float " + v);
        } catch (IllegalArgumentException expected) {
            // expected: double cannot be narrowed to float by Field.getFloat
        }
    }
}
