/*
 * @test
 * @summary Verify that Field.getChar on a static float field reaches the float accessor and throws IllegalArgumentException
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getField("value");

        try {
            char c = f.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException, got char: " + (int) c);
        } catch (IllegalArgumentException expected) {
            // expected: float static field cannot be read as char
        }

        if (f.getFloat(null) != 1.5f) {
            throw new RuntimeException("getFloat returned unexpected value");
        }

        f.setFloat(null, 2.5f);

        try {
            f.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException after set");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
