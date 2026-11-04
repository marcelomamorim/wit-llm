/*
 * @test
 * @summary Verify that Field.getFloat on a non-primitive (Object) field is rejected with IllegalArgumentException
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public Object value = Integer.valueOf(7);

    public static void main(String[] args) throws Exception {
        Field field = UnsafeObjectFieldAccessorImplWitupTest.class.getField("value");

        UnsafeObjectFieldAccessorImplWitupTest holder = new UnsafeObjectFieldAccessorImplWitupTest();
        expectIllegalArgument(field, holder);

        holder.value = Float.valueOf(1.5f);
        expectIllegalArgument(field, holder);

        Object read = field.get(holder);
        if (!Float.valueOf(1.5f).equals(read)) {
            throw new RuntimeException("Object accessor get returned unexpected value: " + read);
        }
    }

    private static void expectIllegalArgument(Field field, Object obj) throws Exception {
        try {
            float f = field.getFloat(obj);
            throw new RuntimeException("Expected IllegalArgumentException but got float: " + f);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
