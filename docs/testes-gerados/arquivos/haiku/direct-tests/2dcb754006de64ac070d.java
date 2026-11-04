/*
 * @test
 * @summary Verify Field.getChar, which dispatches to UnsafeCharacterFieldAccessorImpl.getChar for instance char fields
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    private char value = 'A';
    private final char finalValue = 'Z';

    public static void main(String[] args) throws Exception {
        UnsafeCharacterFieldAccessorImplWitupTest t = new UnsafeCharacterFieldAccessorImplWitupTest();

        Field f = UnsafeCharacterFieldAccessorImplWitupTest.class.getDeclaredField("value");
        if (f.getChar(t) != 'A') {
            throw new RuntimeException("getChar returned unexpected initial value");
        }

        f.setChar(t, 'C');
        if (f.getChar(t) != 'C') {
            throw new RuntimeException("getChar did not observe value set by setChar");
        }

        Object boxed = f.get(t);
        if (!(boxed instanceof Character) || ((Character) boxed).charValue() != 'C') {
            throw new RuntimeException("get did not return Character 'C'");
        }

        Field ff = UnsafeCharacterFieldAccessorImplWitupTest.class.getDeclaredField("finalValue");
        if (ff.getChar(t) != 'Z') {
            throw new RuntimeException("getChar on final field returned unexpected value");
        }

        try {
            f.getChar("not an instance of the declaring class");
            throw new RuntimeException("expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
