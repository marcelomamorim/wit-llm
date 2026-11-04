/*
 * @test
 * @summary Verify Field.getShort on Object-typed fields throws IllegalArgumentException via UnsafeObjectFieldAccessorImpl
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    Object ref = "value";
    String text = "x";

    public static void main(String[] args) throws Exception {
        UnsafeObjectFieldAccessorImplWitupTest t = new UnsafeObjectFieldAccessorImplWitupTest();

        Field refField = UnsafeObjectFieldAccessorImplWitupTest.class.getDeclaredField("ref");
        expectIllegalArgument(refField, t);

        Field textField = UnsafeObjectFieldAccessorImplWitupTest.class.getDeclaredField("text");
        expectIllegalArgument(textField, t);

        expectIllegalArgument(refField, null);
    }

    private static void expectIllegalArgument(Field f, Object obj) {
        try {
            short s = f.getShort(obj);
            throw new RuntimeException("Expected IllegalArgumentException for field "
                    + f.getName() + " but got value " + s);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
