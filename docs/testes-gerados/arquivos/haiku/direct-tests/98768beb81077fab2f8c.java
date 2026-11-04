/*
 * @test
 * @summary Verifies boolean instance field reads via Field.getBoolean and Field.get, which dispatch to UnsafeBooleanFieldAccessorImpl.getBoolean
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public boolean flag;

    public static void main(String[] args) throws Exception {
        UnsafeBooleanFieldAccessorImplWitupTest t = new UnsafeBooleanFieldAccessorImplWitupTest();
        Field f = UnsafeBooleanFieldAccessorImplWitupTest.class.getField("flag");

        t.flag = true;
        if (f.getBoolean(t) != true) {
            throw new RuntimeException("expected true from getBoolean");
        }

        t.flag = false;
        if (f.getBoolean(t) != false) {
            throw new RuntimeException("expected false from getBoolean");
        }

        Object boxed = f.get(t);
        if (!Boolean.FALSE.equals(boxed)) {
            throw new RuntimeException("expected Boolean.FALSE from get, got " + boxed);
        }

        t.flag = true;
        boxed = f.get(t);
        if (!Boolean.TRUE.equals(boxed)) {
            throw new RuntimeException("expected Boolean.TRUE from get, got " + boxed);
        }
    }
}
