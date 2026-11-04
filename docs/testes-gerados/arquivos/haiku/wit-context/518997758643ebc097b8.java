/*
 * @test
 * @summary Verifies Field.getInt behavior that reaches UnsafeIntegerFieldAccessorImpl.getInt, including wrong-receiver and null-receiver handling
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static class Holder {
        public int value = 42;
        public static int S = 7;
    }

    public static class Other {
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("value");
        Holder h = new Holder();

        if (f.getInt(h) != 42) {
            throw new RuntimeException("Expected 42 from getInt on instance field");
        }

        h.value = 5;
        if (f.getInt(h) != 5) {
            throw new RuntimeException("Expected 5 after field update");
        }

        Field s = Holder.class.getDeclaredField("S");
        if (s.getInt(null) != 7) {
            throw new RuntimeException("Expected 7 from getInt on static field");
        }

        boolean iae = false;
        try {
            f.getInt(new Other());
        } catch (IllegalArgumentException e) {
            iae = true;
        }
        if (!iae) {
            throw new RuntimeException("Expected IllegalArgumentException for wrong receiver type");
        }

        boolean npe = false;
        try {
            f.getInt(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for null receiver on instance field");
        }
    }
}
