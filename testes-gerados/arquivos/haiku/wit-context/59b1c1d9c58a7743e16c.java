/*
 * @test
 * @summary Checks that Field.getLong on a char field returns the widened char value and rejects objects of an unrelated class with IllegalArgumentException
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    static class Holder {
        char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("c");

        long v = f.getLong(h);
        if (v != 65L) {
            throw new RuntimeException("Expected 65 but got " + v);
        }

        f.setChar(h, 'Z');
        v = f.getLong(h);
        if (v != 90L) {
            throw new RuntimeException("Expected 90 after setChar but got " + v);
        }

        boolean thrown = false;
        try {
            f.getLong("not a Holder");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        }
    }
}
