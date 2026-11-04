/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putSet(DerValue[] set) for normal and edge cases
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testPutSetWithEmptyArray();
        testPutSetWithSingleElement();
        testPutSetWithMultipleElements();
    }

    private static void testPutSetWithEmptyArray() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue[] emptySet = new DerValue[0];
        dos.putSet(emptySet);
        byte[] result = dos.toByteArray();
        // The output should start with the tag for Set (0x31) and length 0
        if (result.length < 2 || result[0] != DerValue.tag_Set || result[1] != 0) {
            throw new AssertionError("putSet with empty array did not produce expected DER encoding");
        }
    }

    private static void testPutSetWithSingleElement() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue[] set = new DerValue[1];
        // Create a DerValue representing an integer 5
        set[0] = new DerValue(DerValue.tag_Integer, new byte[] {5});
        dos.putSet(set);
        byte[] result = dos.toByteArray();
        // The output should start with the tag for Set
        if (result.length < 3 || result[0] != DerValue.tag_Set) {
            throw new AssertionError("putSet with single element did not produce expected DER encoding");
        }
        // The content should include the integer tag (0x02) and value 5
        boolean foundInteger = false;
        for (int i = 0; i < result.length - 2; i++) {
            if (result[i] == DerValue.tag_Integer && result[i+2] == 5) {
                foundInteger = true;
                break;
            }
        }
        if (!foundInteger) {
            throw new AssertionError("putSet output missing expected integer encoding");
        }
    }

    private static void testPutSetWithMultipleElements() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue[] set = new DerValue[2];
        set[0] = new DerValue(DerValue.tag_Integer, new byte[] {1});
        set[1] = new DerValue(DerValue.tag_Integer, new byte[] {2});
        dos.putSet(set);
        byte[] result = dos.toByteArray();
        if (result.length < 5 || result[0] != DerValue.tag_Set) {
            throw new AssertionError("putSet with multiple elements did not produce expected DER encoding");
        }
        // Check that both integer values 1 and 2 appear in the output
        boolean foundOne = false;
        boolean foundTwo = false;
        for (int i = 0; i < result.length - 2; i++) {
            if (result[i] == DerValue.tag_Integer) {
                if (result[i+2] == 1) foundOne = true;
                if (result[i+2] == 2) foundTwo = true;
            }
        }
        if (!foundOne || !foundTwo) {
            throw new AssertionError("putSet output missing expected integer encodings");
        }
    }
}
