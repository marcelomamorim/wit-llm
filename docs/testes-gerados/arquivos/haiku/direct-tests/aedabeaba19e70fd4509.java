/*
 * @test
 * @summary Verify that Field.getLong rejects a static Object-typed field with IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static class Holder {
        public static Object value = "payload";
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("value");

        boolean thrownNull = false;
        try {
            f.getLong(null);
        } catch (IllegalArgumentException e) {
            thrownNull = true;
        }
        if (!thrownNull) {
            throw new RuntimeException("Expected IllegalArgumentException for getLong(null) on Object field");
        }

        boolean thrownObj = false;
        try {
            f.getLong(new Object());
        } catch (IllegalArgumentException e) {
            thrownObj = true;
        }
        if (!thrownObj) {
            throw new RuntimeException("Expected IllegalArgumentException for getLong(obj) on Object field");
        }

        if (!"payload".equals(f.get(null))) {
            throw new RuntimeException("Field value changed unexpectedly");
        }
    }
}
