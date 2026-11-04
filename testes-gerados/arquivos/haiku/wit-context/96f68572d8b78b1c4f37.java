/*
 * @test
 * @summary Verifies that Field.getShort on a long field is rejected through the public reflection API
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static class Holder {
        public long value = 42L;
    }

    public static void main(String[] args) throws Exception {
        Holder holder = new Holder();
        Field field = Holder.class.getDeclaredField("value");

        if (field.getLong(holder) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }

        boolean thrown = false;
        try {
            field.getShort(holder);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getShort on long field");
        }
    }
}
