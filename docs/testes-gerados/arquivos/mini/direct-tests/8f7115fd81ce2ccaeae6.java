/*
 * @test
 * @summary Test UnsafeQualifiedCharacterFieldAccessorImpl.getInt(Object) returns correct char as int
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class TestObj {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getField("c");
        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        int intValue = accessor.getInt(obj);
        if (intValue != 'A') {
            throw new AssertionError("Expected int value 65 but got " + intValue);
        }

        // Test with null object to check IllegalArgumentException thrown
        boolean threw = false;
        try {
            accessor.getInt(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException when passing null to getInt");
        }
    }
}
