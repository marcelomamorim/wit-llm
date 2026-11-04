/*
 * @test
 * @summary Verifies that Field.getByte on a volatile long field throws IllegalArgumentException, reaching UnsafeQualifiedLongFieldAccessorImpl.getByte
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public volatile long value = 42L;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedLongFieldAccessorImplWitupTest.class.getField("value");
        UnsafeQualifiedLongFieldAccessorImplWitupTest target = new UnsafeQualifiedLongFieldAccessorImplWitupTest();
        boolean thrown = false;
        try {
            field.getByte(target);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte on long field");
        }
    }
}
