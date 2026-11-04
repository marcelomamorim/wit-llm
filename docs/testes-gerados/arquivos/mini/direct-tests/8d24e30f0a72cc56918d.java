/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getLong(Object) returns correct int value as long
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    static class TestObj {
        int value = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getDeclaredField("value");
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(field);

        long longValue = accessor.getLong(obj);

        if (longValue != obj.value) {
            throw new AssertionError("Expected getLong to return " + obj.value + " but got " + longValue);
        }
    }
}
