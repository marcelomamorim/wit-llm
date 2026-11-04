/*
 * @test
 * @summary Field.getChar on a float field must throw IllegalArgumentException, and Field.getFloat must still return the stored value
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public float f = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedFloatFieldAccessorImplWitupTest t = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getField("f");

        if (field.getFloat(t) != 1.5f) {
            throw new AssertionError("getFloat returned unexpected value");
        }

        boolean thrown = false;
        try {
            field.getChar(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getChar on float field");
        }
    }
}
