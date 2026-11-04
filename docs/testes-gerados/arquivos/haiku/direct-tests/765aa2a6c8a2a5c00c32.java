/*
 * @test
 * @summary Verify that reading a boolean field as short via Field.getShort is rejected with IllegalArgumentException
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static class Holder {
        public boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("flag");
        boolean threw = false;
        try {
            f.getShort(h);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException from getShort on boolean field");
        }

        Holder h2 = new Holder();
        h2.flag = false;
        threw = false;
        try {
            f.getShort(h2);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for false boolean field too");
        }
    }
}
