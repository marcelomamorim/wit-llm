/* @test    @summary Test getDouble method of UnsafeLongFieldAccessorImpl class    @run main UnsafeLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsLongValue();
        testGetDoubleWithNullObject();
    }

    static void testGetDoubleReturnsLongValue() throws Exception {
        class Dummy {
            public long value = 123456789L;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(field);
        double result = accessor.getDouble(dummy);
        long expected = dummy.value;
        if (Double.doubleToLongBits(result) != expected) {
            throw new AssertionError("Expected double bits: " + Long.toHexString(expected) + ", but got: " + Double.doubleToLongBits(result));
        }
    }

    static void testGetDoubleWithNullObject() {
        try {
            Field field = null;
            UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(field);
            accessor.getDouble(null);
            throw new AssertionError("Expected NullPointerException for null object");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
