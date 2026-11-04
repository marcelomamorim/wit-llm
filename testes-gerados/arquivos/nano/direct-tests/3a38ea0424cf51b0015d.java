/*
 * @test
 * @summary Test getDouble method of UnsafeDoubleFieldAccessorImpl
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsCorrectValue();
    }

    private static void testGetDoubleReturnsCorrectValue() throws Exception {
        class TestObject {
            public double value = 123.456;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        UnsafeDoubleFieldAccessorImpl accessor = new UnsafeDoubleFieldAccessorImpl(field);
        double result = accessor.getDouble(obj);
        if (result != 123.456) {
            throw new AssertionError("Expected 123.456 but got " + result);
        }
    }
}
