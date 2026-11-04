/*
 * @test
 * @summary Verify that Field.getShort on a reference-typed field throws IllegalArgumentException, reaching UnsafeObjectFieldAccessorImpl.getShort through the public API
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    static class Holder {
        Object ref = "value";
        short s = 1;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();

        Field refField = Holder.class.getDeclaredField("ref");
        try {
            refField.getShort(h);
            throw new AssertionError("Expected IllegalArgumentException for getShort on Object field");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        Field shortField = Holder.class.getDeclaredField("s");
        if (shortField.getShort(h) != 1) {
            throw new AssertionError("Unexpected value from short field getShort");
        }
    }
}
