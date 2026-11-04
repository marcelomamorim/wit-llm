/*
 * @test
 * @summary Verify that Field.getInt on static Object fields throws IllegalArgumentException, the observable behavior of the getInt accessor path
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    static final Object FINAL_REF = "value";
    static Object plainRef = "value";

    public static void main(String[] args) throws Exception {
        Field finalField = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("FINAL_REF");
        expectIllegalArgument(finalField);

        Field plainField = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("plainRef");
        expectIllegalArgument(plainField);
    }

    static void expectIllegalArgument(Field f) {
        try {
            f.getInt(null);
        } catch (IllegalArgumentException e) {
            return;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected IllegalAccessException for " + f.getName(), e);
        }
        throw new RuntimeException("Expected IllegalArgumentException for " + f.getName());
    }
}
