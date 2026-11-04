/* @test    @summary Test getDouble method of UnsafeIntegerFieldAccessorImpl for correct return value.    @run main UnsafeIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsCorrectValue();
    }

    public static void testGetDoubleReturnsCorrectValue() throws Exception {
        class Dummy {
            public int value = 42;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl(field);
        int fieldValue = accessor.getInt(dummy);
        double result = accessor.getDouble(dummy);
        if (result != (double) fieldValue) {
            throw new AssertionError("Expected " + (double) fieldValue + " but got " + result);
        }
    }
}
