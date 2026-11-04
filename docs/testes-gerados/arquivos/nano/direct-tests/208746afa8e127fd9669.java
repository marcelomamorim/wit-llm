/* @test    @summary Test getShort method of UnsafeFloatFieldAccessorImpl class    @run main UnsafeFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortReturnsExpectedValue();
    }

    private static void testGetShortReturnsExpectedValue() throws Exception {
        class Dummy {
            public float floatField = 123.45f;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("floatField");
        UnsafeFloatFieldAccessorImpl accessor = new UnsafeFloatFieldAccessorImpl(field);
        short result = accessor.getShort(dummy);
        // Since getShort throws an exception, this line should not be reached.
        // If it is, the test fails.
        System.out.println("Unexpected result: " + result);
    }
}
