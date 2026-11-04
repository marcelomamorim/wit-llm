/*
 * @test
 * @summary Test getBoolean method throws IllegalArgumentException as expected
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class Dummy {
        public int value = 42;
    }

    public static void main(String[] args) {
        try {
            Field f = Dummy.class.getField("value");
            UnsafeQualifiedIntegerFieldAccessorImpl acc = new UnsafeQualifiedIntegerFieldAccessorImpl(f, false);
            acc.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
