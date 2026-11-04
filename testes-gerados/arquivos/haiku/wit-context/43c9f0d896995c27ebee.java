/*
 * @test
 * @summary Verifies that getChar on a final long field, which uses the UnsafeQualifiedLongFieldAccessorImpl accessor, throws IllegalArgumentException
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    static class Holder {
        final long value;

        Holder() {
            value = 42L;
        }
    }

    public static void main(String[] args) throws Exception {
        Holder holder = new Holder();
        Field field = Holder.class.getDeclaredField("value");
        field.setAccessible(true);

        try {
            field.getChar(holder);
            throw new RuntimeException("Expected IllegalArgumentException from getChar");
        } catch (IllegalArgumentException expected) {
        }

        if (field.getLong(holder) != 42L) {
            throw new RuntimeException("Unexpected long value after failed getChar");
        }
    }
}
