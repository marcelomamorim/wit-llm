/*
 * @test
 * @summary Verifies that Field.getChar on an int field is rejected with IllegalArgumentException, which is the observable behavior of UnsafeIntegerFieldAccessorImpl.getChar
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public int value = 42;

    public static void main(String[] args) throws Exception {
        UnsafeIntegerFieldAccessorImplWitupTest obj = new UnsafeIntegerFieldAccessorImplWitupTest();
        Field field = UnsafeIntegerFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            field.getChar(obj);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getChar on int field");
        }

        // The field value must remain unchanged after the rejected call.
        if (obj.value != 42) {
            throw new RuntimeException("Field value was modified: " + obj.value);
        }

        // Repeated calls must consistently reject the conversion.
        thrown = false;
        try {
            field.getChar(obj);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Second getChar call did not throw IllegalArgumentException");
        }
    }
}
