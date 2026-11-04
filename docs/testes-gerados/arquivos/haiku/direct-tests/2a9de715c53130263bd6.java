/*
 * @test
 * @summary Verify that Field.getByte on a reference-typed field reaches UnsafeObjectFieldAccessorImpl.getByte, which always throws IllegalArgumentException
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public Object ref = Integer.valueOf(42);

    public static void main(String[] args) throws Exception {
        UnsafeObjectFieldAccessorImplWitupTest holder = new UnsafeObjectFieldAccessorImplWitupTest();
        Field field = UnsafeObjectFieldAccessorImplWitupTest.class.getField("ref");

        boolean thrown = false;
        try {
            field.getByte(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getByte on Object field");
        }

        thrown = false;
        try {
            field.getByte(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException on repeated getByte call");
        }

        if (holder.ref.equals(Integer.valueOf(42)) == false) {
            throw new AssertionError("Field value must remain unchanged after failed getByte");
        }
    }
}
