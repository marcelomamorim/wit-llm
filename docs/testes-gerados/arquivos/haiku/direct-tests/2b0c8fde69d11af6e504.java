/*
 * @test
 * @summary Verify that Field.getByte on a boolean static field throws IllegalArgumentException, which reaches the boolean accessor getByte path
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static final boolean FINAL_FLAG = true;
    public static volatile boolean volatileFlag = false;

    public static void main(String[] args) throws Exception {
        checkGetByteThrows("FINAL_FLAG");
        checkGetByteThrows("volatileFlag");
    }

    private static void checkGetByteThrows(String fieldName) throws Exception {
        Field f = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField(fieldName);
        boolean thrown = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte on boolean field " + fieldName);
        }
    }
}
