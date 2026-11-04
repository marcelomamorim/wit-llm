/*
 * @test
 * @summary Verify that Field.getShort on a static Object field throws IllegalArgumentException through the public reflection API
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static Object objectField = "value";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getField("objectField");

        Object current = f.get(null);
        if (!"value".equals(current)) {
            throw new RuntimeException("Unexpected get() result: " + current);
        }

        try {
            f.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException from getShort(null)");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getShort("ignored");
            throw new RuntimeException("Expected IllegalArgumentException from getShort(non-null)");
        } catch (IllegalArgumentException expected) {
        }
    }
}
