/* @test    @summary Test getLong method of UnsafeCharacterFieldAccessorImpl class    @run main UnsafeCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsCharAsLong();
    }

    private static void testGetLongReturnsCharAsLong() throws Exception {
        class Dummy {
            public char value = 'A';
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl(field);
        Object obj = dummy;
        long result = accessor.getLong(obj);
        if (result != (long) dummy.value) {
            throw new AssertionError("Expected " + (long) dummy.value + " but got " + result);
        }
    }
}
