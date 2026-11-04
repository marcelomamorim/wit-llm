/*
 * @test
 * @summary Field.getByte on a double field must reject the conversion with IllegalArgumentException
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {
    public double value = 1.5d;

    public static void main(String[] args) throws Exception {
        UnsafeDoubleFieldAccessorImplWitupTest holder = new UnsafeDoubleFieldAccessorImplWitupTest();
        Field field = UnsafeDoubleFieldAccessorImplWitupTest.class.getField("value");
        if (field.getDouble(holder) != 1.5d) {
            throw new RuntimeException("getDouble sanity check failed");
        }
        try {
            byte b = field.getByte(holder);
            throw new RuntimeException("expected IllegalArgumentException, got " + b);
        } catch (IllegalArgumentException expected) {
        }
    }
}
