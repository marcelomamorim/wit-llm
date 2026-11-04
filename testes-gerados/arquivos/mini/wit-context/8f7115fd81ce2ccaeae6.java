/*
 * @test
 * @summary Test getInt(Object) method of UnsafeQualifiedCharacterFieldAccessorImpl
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class TestClass {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("c");
        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        // Test getInt returns the char value as int
        int intValue = accessor.getInt(testObj);
        if (intValue != 'A') {
            throw new RuntimeException("getInt returned " + intValue + ", expected " + (int) 'A');
        }

        // Test getInt throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getInt(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for invalid object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
