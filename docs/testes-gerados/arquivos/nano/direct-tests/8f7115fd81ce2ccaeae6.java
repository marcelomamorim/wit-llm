/*
 * @test
 * @summary Test getInt method of UnsafeQualifiedCharacterFieldAccessorImpl
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntReturnsCorrectValue();
        testGetIntWithNullObjectThrows();
    }

    static class Dummy {
        public char value = 'A';
    }

    static void testGetIntReturnsCorrectValue() throws Exception {
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl(field, false);
        int result = accessor.getInt(dummy);
        assert result == (int) dummy.value : "Expected " + (int) dummy.value + " but got " + result;
    }

    static void testGetIntWithNullObjectThrows() {
        try {
            Field field = Dummy.class.getField("value");
            jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl(field, false);
            accessor.getInt(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
