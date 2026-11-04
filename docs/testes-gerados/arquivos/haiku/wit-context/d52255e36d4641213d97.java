/*
 * @test
 * @summary Verify that Field.getBoolean on a static final double field throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static final double VALUE = Math.PI;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getField("VALUE");

        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getBoolean on double field");
        }

        if (f.getDouble(null) != Math.PI) {
            throw new RuntimeException("Unexpected value from getDouble on static final double field");
        }
    }
}
