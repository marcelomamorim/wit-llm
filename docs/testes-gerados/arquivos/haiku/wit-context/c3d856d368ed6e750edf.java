/*
 * @test
 * @summary Verify Field.getFloat on a char field widens the value and rejects an object not assignable to the declaring class
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    public static class Holder {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("c");
        Holder h = new Holder();
        check(f.getFloat(h) == 65.0f, "initial value widened to float");
        h.c = 'z';
        check(f.getFloat(h) == 122.0f, "updated value widened to float");
        try {
            f.getFloat(new Object());
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
