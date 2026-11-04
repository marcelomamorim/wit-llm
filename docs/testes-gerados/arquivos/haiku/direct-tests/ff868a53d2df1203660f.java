/*
 * @test
 * @summary Verifies the Field.getLong path backed by UnsafeLongFieldAccessorImpl for instance long fields
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {
    public long pub;
    private long priv;

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        UnsafeLongFieldAccessorImplWitupTest t = new UnsafeLongFieldAccessorImplWitupTest();

        Field pubField = UnsafeLongFieldAccessorImplWitupTest.class.getField("pub");

        t.pub = Long.MIN_VALUE;
        check(pubField.getLong(t) == Long.MIN_VALUE, "getLong should return MIN_VALUE");

        t.pub = Long.MAX_VALUE;
        check(pubField.getLong(t) == Long.MAX_VALUE, "getLong should return MAX_VALUE");

        t.pub = 0L;
        check(pubField.getLong(t) == 0L, "getLong should return zero");

        Field privField = UnsafeLongFieldAccessorImplWitupTest.class.getDeclaredField("priv");
        privField.setAccessible(true);
        privField.setLong(t, 42L);
        check(privField.getLong(t) == 42L, "getLong should observe value written by setLong");
        check(t.priv == 42L, "direct read should observe value written by setLong");

        Object boxed = privField.get(t);
        check(boxed instanceof Long, "get should box long field as Long");
        check(((Long) boxed).longValue() == 42L, "boxed value should equal 42");

        try {
            pubField.getLong("not an instance of the declaring class");
            throw new RuntimeException("getLong with wrong object type should throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
