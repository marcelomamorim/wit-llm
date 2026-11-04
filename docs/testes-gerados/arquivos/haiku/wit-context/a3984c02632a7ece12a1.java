/*
 * @test
 * @summary Verifies Field.get on a volatile Object field, which dispatches to UnsafeQualifiedObjectFieldAccessorImpl.get, returns the stored value and rejects objects of an incompatible declaring type
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    static class Holder {
        volatile Object value;
    }

    public static void main(String[] args) throws Exception {
        Field field = Holder.class.getDeclaredField("value");

        Holder holder = new Holder();
        String expected = "stored";
        holder.value = expected;
        Object actual = field.get(holder);
        if (actual != expected) {
            throw new AssertionError("Expected stored value but got: " + actual);
        }

        Holder emptyHolder = new Holder();
        Object nullValue = field.get(emptyHolder);
        if (nullValue != null) {
            throw new AssertionError("Expected null but got: " + nullValue);
        }

        boolean thrown = false;
        try {
            field.get("not a Holder");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for incompatible object");
        }
    }
}
