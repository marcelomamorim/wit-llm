/*
 * @test
 * @summary Test for getFloat method in UnsafeFloatFieldAccessorImpl
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsCorrectValue();
    }

    public static void testGetFloatReturnsCorrectValue() throws Exception {
        class TestClass {
            public float floatField = 3.14f;
        }
        TestClass obj = new TestClass();
        Field field = TestClass.class.getDeclaredField("floatField");
        UnsafeFloatFieldAccessorImpl accessor = new UnsafeFloatFieldAccessorImpl(field);
        float result = accessor.getFloat(obj);
        if (result != 3.14f) {
            throw new AssertionError("Expected 3.14f, but got " + result);
        }
    }
}
