/*
 * @test
 * @summary Field.getChar on a static volatile boolean field must throw IllegalArgumentException
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static volatile boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        boolean thrown = false;
        try {
            field.getChar(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getChar on boolean field");
        }
    }
}
