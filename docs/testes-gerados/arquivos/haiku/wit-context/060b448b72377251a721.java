/*
 * @test
 * @summary Verify that getBoolean on an int field throws IllegalArgumentException through the public Field API
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {
    public int value = 42;

    public static void main(String[] args) throws Exception {
        UnsafeIntegerFieldAccessorImplWitupTest obj = new UnsafeIntegerFieldAccessorImplWitupTest();
        Field f = UnsafeIntegerFieldAccessorImplWitupTest.class.getField("value");
        try {
            f.getBoolean(obj);
            throw new RuntimeException("Expected IllegalArgumentException from getBoolean on int field");
        } catch (IllegalArgumentException expected) {
        }
        if (f.getInt(obj) != 42) {
            throw new RuntimeException("getInt returned unexpected value");
        }
    }
}
