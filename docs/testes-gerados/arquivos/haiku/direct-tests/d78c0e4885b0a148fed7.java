/*
 * @test
 * @summary Field.getShort on a static final char field must reject the conversion, while getChar still returns the value
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {
    public static final char C = 'A';

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest.class.getDeclaredField("C");
        boolean thrown = false;
        try {
            f.getShort(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException from getShort");
        }
        if (f.getChar(null) != 'A') {
            throw new RuntimeException("getChar returned unexpected value");
        }
    }
}
