/*
 * @test
 * @summary Field.getByte on object-typed fields must throw IllegalArgumentException, exercising the object accessor getByte path
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    static final Object STATIC_REF = "value";
    Object instanceRef = "value";

    static void expectIllegalArgument(Field f, Object target) {
        try {
            byte b = f.getByte(target);
            throw new AssertionError("Expected IllegalArgumentException but got " + b);
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (IllegalAccessException e) {
            throw new AssertionError("Unexpected IllegalAccessException", e);
        }
    }

    public static void main(String[] args) throws Exception {
        Field staticField = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class
                .getDeclaredField("STATIC_REF");
        expectIllegalArgument(staticField, null);

        UnsafeQualifiedObjectFieldAccessorImplWitupTest holder =
                new UnsafeQualifiedObjectFieldAccessorImplWitupTest();
        Field instanceField = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class
                .getDeclaredField("instanceRef");
        expectIllegalArgument(instanceField, holder);
    }
}
