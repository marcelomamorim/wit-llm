/*
 * @test
 * @summary Verifies that Field.getLong on a boolean field reaches UnsafeBooleanFieldAccessorImpl.getLong and throws IllegalArgumentException, while boolean access keeps working
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeBooleanFieldAccessorImplWitupTest.class.getField("flag");
        UnsafeBooleanFieldAccessorImplWitupTest t = new UnsafeBooleanFieldAccessorImplWitupTest();

        boolean lancou = false;
        try {
            f.getLong(t);
        } catch (IllegalArgumentException expected) {
            lancou = true;
        }
        if (!lancou) {
            throw new RuntimeException("getLong sobre campo boolean deveria lançar IllegalArgumentException");
        }

        if (!f.getBoolean(t)) {
            throw new RuntimeException("getBoolean deveria retornar true");
        }
        Object v = f.get(t);
        if (!Boolean.TRUE.equals(v)) {
            throw new RuntimeException("get deveria retornar Boolean.TRUE");
        }
    }
}
