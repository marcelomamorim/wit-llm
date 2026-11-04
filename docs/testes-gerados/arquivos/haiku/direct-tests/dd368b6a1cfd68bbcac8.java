/*
 * @test
 * @summary Field.getInt on a reference-typed field must throw IllegalArgumentException via UnsafeObjectFieldAccessorImpl.getInt
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public Object obj = "value";
    public Integer boxed = 42;
    public String str = "text";

    public static void main(String[] args) throws Exception {
        UnsafeObjectFieldAccessorImplWitupTest t = new UnsafeObjectFieldAccessorImplWitupTest();

        expectIllegalArgument(UnsafeObjectFieldAccessorImplWitupTest.class.getField("obj"), t);
        expectIllegalArgument(UnsafeObjectFieldAccessorImplWitupTest.class.getField("boxed"), t);
        expectIllegalArgument(UnsafeObjectFieldAccessorImplWitupTest.class.getField("str"), t);
    }

    static void expectIllegalArgument(Field f, Object target) {
        try {
            int value = f.getInt(target);
            throw new RuntimeException("No exception for field " + f.getName() + ", returned " + value);
        } catch (IllegalArgumentException expected) {
            // expected: reference-typed fields are not readable as int
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected IllegalAccessException for " + f.getName(), e);
        }
    }
}
