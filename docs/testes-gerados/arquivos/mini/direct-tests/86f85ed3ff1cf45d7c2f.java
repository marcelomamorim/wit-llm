/*
 * @test
 * @summary Test sun.security.util.DerValue.getBoolean() method
 * @run main sun.security.util.DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.DerInputBuffer;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetBooleanValidTrue();
        testGetBooleanValidFalse();
        testGetBooleanInvalidTag();
        testGetBooleanInvalidLength();
    }

    private static void testGetBooleanValidTrue() throws Exception {
        byte[] data = new byte[] {1}; // non-zero means true
        DerInputBuffer dib = new DerInputBuffer(data);
        DerValue dv = new DerValue(DerValue.tag_Boolean, data);
        // forcibly set length and buffer to simulate internal state
        setLength(dv, 1);
        setBuffer(dv, dib);
        boolean val = dv.getBoolean();
        if (!val) {
            throw new RuntimeException("Expected true boolean value");
        }
    }

    private static void testGetBooleanValidFalse() throws Exception {
        byte[] data = new byte[] {0}; // zero means false
        DerInputBuffer dib = new DerInputBuffer(data);
        DerValue dv = new DerValue(DerValue.tag_Boolean, data);
        setLength(dv, 1);
        setBuffer(dv, dib);
        boolean val = dv.getBoolean();
        if (val) {
            throw new RuntimeException("Expected false boolean value");
        }
    }

    private static void testGetBooleanInvalidTag() {
        byte[] data = new byte[] {1};
        DerValue dv = new DerValue((byte)0x10, data); // invalid tag
        setLength(dv, 1);
        setBuffer(dv, new DerInputBuffer(data));
        try {
            dv.getBoolean();
            throw new RuntimeException("Expected IOException for invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not a BOOLEAN")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testGetBooleanInvalidLength() {
        byte[] data = new byte[] {1, 2};
        DerValue dv = new DerValue(DerValue.tag_Boolean, data);
        setLength(dv, 2); // invalid length
        setBuffer(dv, new DerInputBuffer(data));
        try {
            dv.getBoolean();
            throw new RuntimeException("Expected IOException for invalid length");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("invalid length")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    // Reflection helpers to set private fields length and buffer
    private static void setLength(DerValue dv, int length) throws Exception {
        java.lang.reflect.Field f = DerValue.class.getDeclaredField("length");
        f.setAccessible(true);
        f.setInt(dv, length);
    }

    private static void setBuffer(DerValue dv, DerInputBuffer buffer) throws Exception {
        java.lang.reflect.Field f = DerValue.class.getDeclaredField("buffer");
        f.setAccessible(true);
        f.set(dv, buffer);
    }
}
