/*
 * @test
 * @summary Field.getShort on a static char field must throw IllegalArgumentException, while getChar and getInt still read the value
 * @run main UnsafeStaticCharacterFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticCharacterFieldAccessorImplWitupTest {
    public static class Holder {
        public static char C = 'A';
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("C");
        try {
            f.getShort(null);
            throw new AssertionError("expected IllegalArgumentException from getShort");
        } catch (IllegalArgumentException expected) {
        }
        if (f.getChar(null) != 'A') {
            throw new AssertionError("getChar returned unexpected value");
        }
        if (f.getInt(null) != 'A') {
            throw new AssertionError("getInt returned unexpected value");
        }
    }
}
