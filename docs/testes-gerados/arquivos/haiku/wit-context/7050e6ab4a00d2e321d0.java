/*
 * @test
 * @summary Verify that reading a char field as short through the public Field API throws IllegalArgumentException, which exercises UnsafeCharacterFieldAccessorImpl.getShort
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    public static class Holder {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("c");

        boolean thrown = false;
        try {
            short s = f.getShort(h);
            throw new RuntimeException("expected IllegalArgumentException, got " + s);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getShort did not throw IllegalArgumentException");
        }

        if (f.getChar(h) != 'A') {
            throw new RuntimeException("getChar returned unexpected value");
        }
        if (f.getInt(h) != 65) {
            throw new RuntimeException("getInt returned unexpected value");
        }
    }
}
