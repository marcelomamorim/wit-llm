/* @test    @summary Test getLong method of UnsafeShortFieldAccessorImpl class    @run main UnsafeShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsShortValue();
    }

    private static void testGetLongReturnsShortValue() throws Exception {
        class TestObject {
            public short value = 12345;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(field);
        long result = accessor.getLong(obj);
        if (result != (long) obj.value) {
            throw new AssertionError("Expected " + (long) obj.value + " but got " + result);
        }
    }
}
