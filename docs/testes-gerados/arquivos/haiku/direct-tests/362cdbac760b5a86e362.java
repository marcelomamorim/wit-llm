/*
 * @test
 * @summary Verifies that Field.getLong on a reference-typed field throws IllegalArgumentException
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public Object ref = "value";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeObjectFieldAccessorImplWitupTest.class.getField("ref");
        UnsafeObjectFieldAccessorImplWitupTest obj = new UnsafeObjectFieldAccessorImplWitupTest();
        boolean thrown = false;
        try {
            f.getLong(obj);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getLong on Object field");
        }
    }
}
