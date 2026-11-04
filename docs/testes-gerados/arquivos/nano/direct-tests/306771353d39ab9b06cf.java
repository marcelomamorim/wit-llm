/*
 * @test
 * @summary Test getFloat method of UnsafeShortFieldAccessorImpl class
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsShortValue();
    }

    private static void testGetFloatReturnsShortValue() throws Exception {
        class TestObject {
            public short value = 12345;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(field);
        float result = accessor.getFloat(obj);
        if (result != (float) obj.value) {
            throw new AssertionError("Expected " + (float) obj.value + " but got " + result);
        }
    }
}
