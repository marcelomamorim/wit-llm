/*
 * @test
 * @summary Field.getByte on a final char field must throw IllegalArgumentException, exercising the unsafe qualified char accessor path
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    private final char finalChar = 'x';

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getDeclaredField("finalChar");
        f.setAccessible(true);
        UnsafeQualifiedCharacterFieldAccessorImplWitupTest target = new UnsafeQualifiedCharacterFieldAccessorImplWitupTest();
        try {
            byte b = f.getByte(target);
            throw new RuntimeException("expected IllegalArgumentException, got " + b);
        } catch (IllegalArgumentException expected) {
            // expected: getByte on a char field is not a widening-compatible read
        }
    }
}
