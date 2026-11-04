/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putSet(DerValue[])
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testPutSetWithEmptyArray();
        testPutSetWithSingleDerValue();
        testPutSetWithMultipleDerValues();
    }

    private static void testPutSetWithEmptyArray() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue[] emptySet = new DerValue[0];
        dos.putSet(emptySet);
        byte[] result = dos.toByteArray();
        // The result should start with tag_Set and length 0
        if (result.length < 2) {
            throw new RuntimeException("Encoded output too short for empty set");
        }
        if (result[0] != DerValue.tag_Set) {
            throw new RuntimeException("Expected tag_Set at start");
        }
        if (result[1] != 0) {
            throw new RuntimeException("Expected length 0 for empty set");
        }
    }

    private static void testPutSetWithSingleDerValue() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue[] set = new DerValue[1];
        // Create a DerValue with a simple integer encoding
        // DerValue(int tag, byte[] value) constructor is public
        // Use tag_Integer = 2
        byte[] intBytes = {0x01}; // integer value 1
        set[0] = new DerValue(DerValue.tag_Integer, intBytes);
        dos.putSet(set);
        byte[] result = dos.toByteArray();
        if (result.length < 4) {
            throw new RuntimeException("Encoded output too short for single element set");
        }
        if (result[0] != DerValue.tag_Set) {
            throw new RuntimeException("Expected tag_Set at start");
        }
        // The content should include the encoded integer
        boolean foundIntegerTag = false;
        for (int i = 2; i < result.length; i++) {
            if (result[i] == DerValue.tag_Integer) {
                foundIntegerTag = true;
                break;
            }
        }
        if (!foundIntegerTag) {
            throw new RuntimeException("Encoded output does not contain integer tag");
        }
    }

    private static void testPutSetWithMultipleDerValues() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue[] set = new DerValue[2];
        byte[] intBytes1 = {0x01};
        byte[] intBytes2 = {0x02};
        set[0] = new DerValue(DerValue.tag_Integer, intBytes1);
        set[1] = new DerValue(DerValue.tag_Integer, intBytes2);
        dos.putSet(set);
        byte[] result = dos.toByteArray();
        if (result.length < 6) {
            throw new RuntimeException("Encoded output too short for multiple element set");
        }
        if (result[0] != DerValue.tag_Set) {
            throw new RuntimeException("Expected tag_Set at start");
        }
        int countIntegerTags = 0;
        for (int i = 2; i < result.length; i++) {
            if (result[i] == DerValue.tag_Integer) {
                countIntegerTags++;
            }
        }
        if (countIntegerTags != 2) {
            throw new RuntimeException("Encoded output does not contain two integer tags");
        }
    }
}
