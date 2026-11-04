/*
 * @test
 * @summary Verifies that Field.getChar on a volatile double field throws IllegalArgumentException, while getDouble still returns the stored value
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public volatile double value = 1.5;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedDoubleFieldAccessorImplWitupTest t = new UnsafeQualifiedDoubleFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getField("value");

        try {
            f.getChar(t);
            throw new AssertionError("expected IllegalArgumentException from getChar");
        } catch (IllegalArgumentException expected) {
            // expected: getChar on a double field is not allowed
        }

        double d = f.getDouble(t);
        if (d != 1.5) {
            throw new AssertionError("unexpected getDouble value: " + d);
        }
    }
}
