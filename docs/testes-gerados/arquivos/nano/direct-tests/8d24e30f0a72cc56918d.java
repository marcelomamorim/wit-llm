/* @test    @summary Test getLong method of UnsafeIntegerFieldAccessorImpl class    @run main UnsafeIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsCorrectValue();
    }

    private static void testGetLongReturnsCorrectValue() throws Exception {
        class TestObject {
            public int value = 12345;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getDeclaredField("value");
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(field);
        long result = accessor.getLong(obj);
        if (result != 12345L) {
            throw new AssertionError("Expected 12345L but got " + result);
        }
    }
}
