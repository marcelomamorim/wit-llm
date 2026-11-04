/*
 * @test
 * @summary Verify Field.getByte on a boolean field reaches UnsafeBooleanFieldAccessorImpl.getByte and throws IllegalArgumentException
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        UnsafeBooleanFieldAccessorImplWitupTest t = new UnsafeBooleanFieldAccessorImplWitupTest();
        if (!f.getBoolean(t)) {
            throw new RuntimeException("getBoolean mismatch");
        }
        try {
            f.getByte(t);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }
}
