/*
 * @test
 * @summary Verifies that Field.getChar on a static float field throws IllegalArgumentException, and that Field.get returns the float value
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {
    static final float CONST = 1.5f;
    static float nonFinal = 2.5f;

    public static void main(String[] args) throws Exception {
        Field constField = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("CONST");
        constField.setAccessible(true);
        Object value = constField.get(null);
        if (!(value instanceof Float) || ((Float) value).floatValue() != 1.5f) {
            throw new RuntimeException("Unexpected value for CONST: " + value);
        }
        try {
            constField.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException from getChar on CONST");
        } catch (IllegalArgumentException expected) {
        }

        Field plainField = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("nonFinal");
        plainField.setAccessible(true);
        try {
            plainField.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException from getChar on nonFinal");
        } catch (IllegalArgumentException expected) {
        }
    }
}
