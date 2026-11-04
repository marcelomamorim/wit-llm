/*
 * @test
 * @summary Field.getByte on a volatile long field must throw IllegalArgumentException through UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {
    public volatile long value = 42L;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedLongFieldAccessorImplWitupTest t = new UnsafeQualifiedLongFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedLongFieldAccessorImplWitupTest.class.getField("value");
        expectIllegalArgument(f, t);
        expectIllegalArgument(f, null);
        if (f.getLong(t) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }
    }

    static void expectIllegalArgument(Field f, Object o) throws IllegalAccessException {
        try {
            f.getByte(o);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException from getByte");
    }
}
