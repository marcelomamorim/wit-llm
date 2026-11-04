/*
 * @test
 * @summary Field.getBoolean on a reference-typed field must throw IllegalArgumentException via UnsafeObjectFieldAccessorImpl.getBoolean
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {
    private Object objectValue = Boolean.TRUE;
    private Boolean boxed = Boolean.FALSE;

    public static void main(String[] args) throws Exception {
        UnsafeObjectFieldAccessorImplWitupTest t = new UnsafeObjectFieldAccessorImplWitupTest();
        Field f1 = UnsafeObjectFieldAccessorImplWitupTest.class.getDeclaredField("objectValue");
        expectIllegalArgument(f1, t);
        Field f2 = UnsafeObjectFieldAccessorImplWitupTest.class.getDeclaredField("boxed");
        expectIllegalArgument(f2, t);
    }

    static void expectIllegalArgument(Field f, Object obj) {
        try {
            f.getBoolean(obj);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("No IllegalArgumentException for field " + f.getName());
    }
}
