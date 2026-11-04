/*
 * @test
 * @summary Verifies that Field.getChar on a volatile int field throws IllegalArgumentException and leaves getInt intact
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile int value = 7;
    }

    public static void main(String[] args) throws Exception {
        Holder holder = new Holder();
        Field field = Holder.class.getField("value");

        boolean thrown = false;
        try {
            field.getChar(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getChar on int field");
        }

        if (field.getInt(holder) != 7) {
            throw new AssertionError("getInt returned unexpected value after getChar failure");
        }
    }
}
