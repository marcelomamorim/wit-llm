/* @test    @summary Test getByte method of UnsafeByteFieldAccessorImpl    @run main UnsafeByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteReturnsCorrectValue();
        testGetByteWithNullObjectThrows();
    }

    private static void testGetByteReturnsCorrectValue() throws Exception {
        class Dummy {
            public byte value = 42;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);
        byte result = accessor.getByte(dummy);
        if (result != 42) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }

    private static void testGetByteWithNullObjectThrows() {
        try {
            Field field = String.class.getField("value");
            UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);
            accessor.getByte(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
