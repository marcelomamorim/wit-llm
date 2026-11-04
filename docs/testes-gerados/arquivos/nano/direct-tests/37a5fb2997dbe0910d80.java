/* @test    @summary Test DerValue.toDerInputStream() method for sequence and set tags    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testToDerInputStreamWithSequenceTag();
        testToDerInputStreamWithSetTag();
        testToDerInputStreamWithInvalidTag();
    }

    private static void testToDerInputStreamWithSequenceTag() {
        try {
            byte sequenceTag = 0x30; // DER tag for sequence
            byte[] bufferData = {sequenceTag, 0x00}; // minimal sequence with length 0
            DerValue dv = new DerValue(bufferData);
            DerValue result = dv.toDerInputStream();
            if (result == null) {
                throw new AssertionError("Expected non-null DerInputStream for sequence tag");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for sequence tag: " + e);
        }
    }

    private static void testToDerInputStreamWithSetTag() {
        try {
            byte setTag = 0x31; // DER tag for set
            byte[] bufferData = {setTag, 0x00}; // minimal set with length 0
            DerValue dv = new DerValue(bufferData);
            DerValue result = dv.toDerInputStream();
            if (result == null) {
                throw new AssertionError("Expected non-null DerInputStream for set tag");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for set tag: " + e);
        }
    }

    private static void testToDerInputStreamWithInvalidTag() {
        try {
            byte invalidTag = 0xFF; // invalid tag for this context
            byte[] bufferData = {invalidTag, 0x00};
            DerValue dv = new DerValue(bufferData);
            dv.toDerInputStream();
            throw new AssertionError("Expected IOException for invalid tag")
        } catch (IOException e) {
            // Expected exception
        }
    }
}
