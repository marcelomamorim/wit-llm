/*
 * @test
 * @summary Field.getChar on a final float field must throw IllegalArgumentException via UnsafeQualifiedFloatFieldAccessorImpl.getChar
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public final float f = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedFloatFieldAccessorImplWitupTest t = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getField("f");
        boolean thrown = false;
        try {
            field.getChar(t);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getChar on float field");
        }
    }
}
