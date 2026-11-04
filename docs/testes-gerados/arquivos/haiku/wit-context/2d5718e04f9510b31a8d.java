/*
 * @test
 * @summary Checks Field.getDouble on a volatile long field, which is served by UnsafeQualifiedLongFieldAccessorImpl, including the IllegalArgumentException for an object of the wrong type
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public volatile long value;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedLongFieldAccessorImplWitupTest holder = new UnsafeQualifiedLongFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedLongFieldAccessorImplWitupTest.class.getField("value");

        holder.value = -42L;
        double negative = field.getDouble(holder);
        if (negative != -42.0d) {
            throw new RuntimeException("Expected -42.0 but got " + negative);
        }

        holder.value = 9007199254740993L;
        double rounded = field.getDouble(holder);
        if (rounded != 9007199254740992.0d) {
            throw new RuntimeException("Expected widening rounding to 9007199254740992.0 but got " + rounded);
        }

        boolean thrown = false;
        try {
            field.getDouble("not an instance of the declaring class");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        }
    }
}
