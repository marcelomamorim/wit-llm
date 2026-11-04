/*
 * @test
 * @summary Verify that Field.getShort on a static final int field (routed to UnsafeQualifiedStaticIntegerFieldAccessorImpl) throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {
    public static final int VALUE = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getField("VALUE");
        if (f.getInt(null) != 42) {
            throw new AssertionError("getInt mismatch");
        }
        try {
            short s = f.getShort(null);
            throw new AssertionError("expected IllegalArgumentException, got " + s);
        } catch (IllegalArgumentException expected) {
        }
    }
}
