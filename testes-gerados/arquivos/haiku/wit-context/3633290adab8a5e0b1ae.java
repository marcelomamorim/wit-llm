/*
 * @test
 * @summary Verify that Field.getDouble on a reference-typed field throws IllegalArgumentException through UnsafeObjectFieldAccessorImpl.getDouble
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public Object ref = "value";

    public static void main(String[] args) throws Exception {
        UnsafeObjectFieldAccessorImplWitupTest t = new UnsafeObjectFieldAccessorImplWitupTest();
        Field f = UnsafeObjectFieldAccessorImplWitupTest.class.getField("ref");

        boolean thrown = false;
        try {
            f.getDouble(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getDouble on Object field");
        }

        Object value = f.get(t);
        if (!"value".equals(value)) {
            throw new AssertionError("Object field read returned unexpected value: " + value);
        }
    }
}
