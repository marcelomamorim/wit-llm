/* @test    @summary Test getInt method of UnsafeQualifiedIntegerFieldAccessorImpl    @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntReturnsCorrectValue();
    }

    private static void testGetIntReturnsCorrectValue() throws Exception {
        class TestObject {
            public int value = 42;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getDeclaredField("value");
        UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(field, false);
        int result = accessor.getInt(obj);
        if (result != 42) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }
}
