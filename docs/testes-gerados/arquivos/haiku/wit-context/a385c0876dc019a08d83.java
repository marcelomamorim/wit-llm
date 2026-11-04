/*
 * @test
 * @summary Verify that Field.getChar rejects a static final Object field, exercising the exception path of UnsafeQualifiedStaticObjectFieldAccessorImpl.getChar
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static final Object VALUE = "witup";

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getField("VALUE");

        if (!"witup".equals(field.get(null))) {
            throw new AssertionError("Control read of static final Object field failed");
        }

        expectIllegalArgument(field, null);
        expectIllegalArgument(field, "ignored");
    }

    private static void expectIllegalArgument(Field field, Object obj) throws Exception {
        try {
            field.getChar(obj);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException from getChar on Object field");
    }
}
