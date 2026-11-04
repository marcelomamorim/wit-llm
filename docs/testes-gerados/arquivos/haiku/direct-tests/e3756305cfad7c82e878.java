/*
 * @test
 * @summary Field.getLong on boolean fields must throw IllegalArgumentException, as UnsafeQualifiedBooleanFieldAccessorImpl.getLong does
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {
    public volatile boolean flag = true;
    public final boolean finalFlag = true;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedBooleanFieldAccessorImplWitupTest t =
            new UnsafeQualifiedBooleanFieldAccessorImplWitupTest();

        Field volatileField = UnsafeQualifiedBooleanFieldAccessorImplWitupTest.class.getField("flag");
        try {
            volatileField.getLong(t);
            throw new RuntimeException("Expected IllegalArgumentException for getLong on volatile boolean");
        } catch (IllegalArgumentException expected) {
        }

        Field finalField = UnsafeQualifiedBooleanFieldAccessorImplWitupTest.class.getField("finalFlag");
        try {
            finalField.getLong(t);
            throw new RuntimeException("Expected IllegalArgumentException for getLong on final boolean");
        } catch (IllegalArgumentException expected) {
        }
    }
}
