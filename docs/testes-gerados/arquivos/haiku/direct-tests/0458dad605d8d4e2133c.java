/*
 * @test
 * @summary Field.getByte on a static int field must throw IllegalArgumentException (no narrowing), while getInt still returns the value
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    static final int FINAL_INT = Integer.parseInt("42");
    static int plainInt = 7;

    public static void main(String[] args) throws Exception {
        checkFinalField();
        checkPlainField();
    }

    static void checkFinalField() throws Exception {
        Field f = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("FINAL_INT");
        if (f.getInt(null) != 42) {
            throw new RuntimeException("getInt returned wrong value for FINAL_INT");
        }
        expectIllegalArgument(f);
    }

    static void checkPlainField() throws Exception {
        Field f = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("plainInt");
        if (f.getInt(null) != 7) {
            throw new RuntimeException("getInt returned wrong value for plainInt");
        }
        expectIllegalArgument(f);
    }

    static void expectIllegalArgument(Field f) {
        try {
            byte b = f.getByte(null);
            throw new RuntimeException("getByte did not throw, returned " + b + " for " + f.getName());
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected IllegalAccessException", e);
        }
    }
}
