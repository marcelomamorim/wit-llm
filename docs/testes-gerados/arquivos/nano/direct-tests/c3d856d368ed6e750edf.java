/* @test    @summary Test getFloat method of UnsafeCharacterFieldAccessorImpl class    @run main UnsafeCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsCharValue();
    }

    public static void testGetFloatReturnsCharValue() throws Exception {
        class Dummy {
            public char c = 'A';
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("c");
        jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl(field);
        float result = accessor.getFloat(dummy);
        if (result != (float) dummy.c) {
            throw new AssertionError("Expected " + (float) dummy.c + " but got " + result);
        }
    }
}
