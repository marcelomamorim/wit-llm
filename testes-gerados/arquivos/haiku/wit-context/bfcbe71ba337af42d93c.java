/*
 * @test
 * @summary Field.getByte on a char field must throw IllegalArgumentException, and Field.getChar must still read the value
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {
    public static final char C = 'x';

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest.class.getDeclaredField("C");

        if (f.getChar(null) != 'x') {
            throw new RuntimeException("getChar returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException from getByte on char field");
        }
    }
}
