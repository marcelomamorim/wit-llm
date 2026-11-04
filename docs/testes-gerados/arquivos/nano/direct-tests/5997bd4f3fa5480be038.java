/* @test    @summary Test for getShort method in UnsafeQualifiedShortFieldAccessorImpl    @run main UnsafeQualifiedShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortReturnsCorrectValue();
        testGetShortWithNullObjectThrows();
    }

    private static void testGetShortReturnsCorrectValue() throws Exception {
        class TestClass {
            public short value = 12345;
        }
        TestClass obj = new TestClass();
        Field field = TestClass.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(field, false);
        short result = accessor.getShort(obj);
        if (result != 12345) {
            throw new AssertionError("Expected 12345, but got " + result);
        }
    }

    private static void testGetShortWithNullObjectThrows() {
        try {
            Field field = null; // no actual field needed for this test
            jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(field, false);
            accessor.getShort(null);
            throw new AssertionError("Expected NullPointerException for null object");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
