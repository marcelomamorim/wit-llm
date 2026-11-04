/* @test    @summary Test getDouble method of UnsafeCharacterFieldAccessorImpl class    @run main UnsafeCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsCharValue();
    }

    private static void testGetDoubleReturnsCharValue() throws Exception {
        class Dummy {
            public char value = 'A';
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(field);
        double result = accessor.getDouble(dummy);
        if (result != (double) dummy.value) {
            throw new AssertionError("Expected " + (double) dummy.value + " but got " + result);
        }
    }
}
