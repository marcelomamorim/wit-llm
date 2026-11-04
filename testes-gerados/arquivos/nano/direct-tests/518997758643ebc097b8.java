/* @test    @summary Test for getInt method in UnsafeIntegerFieldAccessorImpl    @run main UnsafeIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntReturnsCorrectValue();
    }

    private static void testGetIntReturnsCorrectValue() throws Exception {
        class TestObject {
            public int value = 42;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getDeclaredField("value");
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(field);
        int result = accessor.getInt(obj);
        if (result != 42) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }
}
