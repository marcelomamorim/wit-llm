/*
 * @test
 * @summary Verify that reading a static int field as char via Field.getChar is rejected, and that Field.get returns the boxed int value
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static final int VALUE = 42;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getField("VALUE");

        Object boxed = field.get(null);
        if (!(boxed instanceof Integer) || ((Integer) boxed).intValue() != 42) {
            throw new RuntimeException("Field.get returned unexpected value: " + boxed);
        }

        try {
            char c = field.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException from getChar but got: " + c);
        } catch (IllegalArgumentException expected) {
            // expected: int to char is not a permitted conversion
        }
    }
}
