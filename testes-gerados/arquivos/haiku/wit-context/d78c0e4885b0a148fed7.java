/*
 * @test
 * @summary Verify Field.getShort on a static final char field throws IllegalArgumentException through the qualified static character accessor
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {
    static final char C = 'x';

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest.class.getDeclaredField("C");

        boolean thrownNull = false;
        try {
            f.getShort(null);
        } catch (IllegalArgumentException e) {
            thrownNull = true;
        }
        if (!thrownNull) {
            throw new RuntimeException("getShort(null) did not throw IllegalArgumentException");
        }

        boolean thrownObj = false;
        try {
            f.getShort(new Object());
        } catch (IllegalArgumentException e) {
            thrownObj = true;
        }
        if (!thrownObj) {
            throw new RuntimeException("getShort(Object) did not throw IllegalArgumentException");
        }

        if (f.getChar(null) != 'x') {
            throw new RuntimeException("getChar(null) returned unexpected value");
        }
    }
}
