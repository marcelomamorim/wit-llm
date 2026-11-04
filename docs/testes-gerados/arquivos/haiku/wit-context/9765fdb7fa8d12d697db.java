/*
 * @test
 * @summary Verify that Field.getInt on a double field throws IllegalArgumentException via UnsafeDoubleFieldAccessorImpl.getInt
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public double d = 1.5;

    public static void main(String[] args) throws Exception {
        UnsafeDoubleFieldAccessorImplWitupTest obj = new UnsafeDoubleFieldAccessorImplWitupTest();
        Field f = UnsafeDoubleFieldAccessorImplWitupTest.class.getField("d");

        if (f.getDouble(obj) != 1.5d) {
            throw new RuntimeException("getDouble sanity check failed");
        }

        try {
            int value = f.getInt(obj);
            throw new RuntimeException("Expected IllegalArgumentException but got int: " + value);
        } catch (IllegalArgumentException expected) {
            // expected behavior
        }
    }
}
