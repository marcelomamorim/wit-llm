/*
 * @test
 * @summary Test that UnsafeQualifiedByteFieldAccessorImpl.getChar always throws IllegalArgumentException
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    static class Dummy {
        public byte b = 1;
    }

    public static void main(String[] args) {
        try {
            Field f = Dummy.class.getField("b");
            UnsafeQualifiedByteFieldAccessorImpl acc = new UnsafeQualifiedByteFieldAccessorImpl(f, false);
            try {
                acc.getChar(new Dummy());
                throw new RuntimeException("Expected IllegalArgumentException not thrown");
            } catch (IllegalArgumentException e) {
                // expected
            }
            try {
                acc.getChar(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown when obj is null");
            } catch (IllegalArgumentException e) {
                // expected
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: field not found", e);
        }
    }
}
