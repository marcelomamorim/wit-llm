/*
 * @test
 * @summary Test UnsafeBooleanFieldAccessorImpl.getInt throws expected IllegalArgumentException
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    static class Dummy {
        public boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("flag");
        UnsafeBooleanFieldAccessorImpl accessor = new UnsafeBooleanFieldAccessorImpl(field);

        // Test that getInt always throws IllegalArgumentException
        try {
            accessor.getInt(dummy);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getInt");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
