/*
 * @test
 * @summary Field.getBoolean on a reference-typed field must throw IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {
    public Object ref = "value";
    public final Object finalRef = "final";

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedObjectFieldAccessorImplWitupTest target = new UnsafeQualifiedObjectFieldAccessorImplWitupTest();
        checkGetBooleanThrowsIAE(UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getField("ref"), target);
        checkGetBooleanThrowsIAE(UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getField("finalRef"), target);
    }

    private static void checkGetBooleanThrowsIAE(Field f, Object target) throws Exception {
        try {
            f.getBoolean(target);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for getBoolean on field " + f.getName());
    }
}
