/* @test    @summary Test getDouble method of UnsafeShortFieldAccessorImpl class    @run main UnsafeShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsShortValue();
    }

    private static void testGetDoubleReturnsShortValue() throws Exception {
        class TestObject {
            public short value = 123;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(field);
        double result = accessor.getDouble(obj);
        if (result != (double) obj.value) {
            throw new AssertionError("Expected " + (double) obj.value + " but got " + result);
        }
    }
}
