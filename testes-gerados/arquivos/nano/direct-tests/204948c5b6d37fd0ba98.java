/* @test    @summary Test for getShort method in UnsafeShortFieldAccessorImpl    @run main UnsafeShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortReturnsCorrectValue();
        testGetShortWithNullObjectThrows();
    }

    static void testGetShortReturnsCorrectValue() throws Exception {
        class TestClass {
            public short value = 12345;
        }
        TestClass obj = new TestClass();
        Field field = TestClass.class.getDeclaredField("value");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(field);
        short result = accessor.getShort(obj);
        if (result != 12345) {
            throw new AssertionError("Expected 12345, but got " + result);
        }
    }

    static void testGetShortWithNullObjectThrows() {
        try {
            Field field = String.class.getDeclaredField("value");
            UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(field);
            accessor.getShort(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
