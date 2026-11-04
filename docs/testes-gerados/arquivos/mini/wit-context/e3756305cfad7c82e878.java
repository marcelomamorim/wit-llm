/*
 * @test
 * @summary Test UnsafeQualifiedBooleanFieldAccessorImpl.getLong throws IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    static class Dummy {
        public boolean flag = true;
    }

    public static void main(String[] args) {
        try {
            Field f = Dummy.class.getField("flag");
            UnsafeQualifiedBooleanFieldAccessorImpl acc = new UnsafeQualifiedBooleanFieldAccessorImpl(f, false);
            acc.getLong(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
