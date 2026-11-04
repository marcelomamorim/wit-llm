/* @test    @summary Test getFloat method of UnsafeQualifiedFloatFieldAccessorImpl    @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsExpectedValue();
    }

    private static void testGetFloatReturnsExpectedValue() throws Exception {
        class Dummy {
            public float value = 42.42f;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl(field, false);
        float result = accessor.getFloat(dummy);
        if (result != dummy.value) {
            throw new AssertionError("Expected: " + dummy.value + ", but got: " + result);
        }
    }
}
