/* @test    @summary Test getFloat method of UnsafeLongFieldAccessorImpl class    @run main UnsafeLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsLongValue();
    }

    private static void testGetFloatReturnsLongValue() throws Exception {
        class Dummy {
            public long value = 123456789L;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(field);
        float result = accessor.getFloat(dummy);
        long longValue = accessor.getLong(dummy);
        if (result != longValue) {
            throw new AssertionError("Expected float value to be equal to long value: " + longValue + " but got: " + result);
        }
    }
}
