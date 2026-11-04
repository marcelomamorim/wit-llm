/*
 * @test
 * @summary Verifies DerValue.getBitString(boolean) tag checking and content extraction
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import java.io.IOException;
import java.util.Arrays;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testBitStringTagNotImplicit();
        testBitStringTagImplicitOnNonBitStringTag();
        testWrongTagWithoutImplicitThrows();
    }

    static void testBitStringTagNotImplicit() throws Exception {
        byte[] content = {0x00, 0x01, 0x02};
        DerValue value = new DerValue(DerValue.tag_BitString, content);
        byte[] result = value.getBitString(false);
        if (!Arrays.equals(result, new byte[] {0x01, 0x02})) {
            throw new RuntimeException("Unexpected bit string content: "
                    + Arrays.toString(result));
        }
    }

    static void testBitStringTagImplicitOnNonBitStringTag() throws Exception {
        byte[] content = {0x00, 0x01, 0x02};
        DerValue value = new DerValue(DerValue.tag_Integer, content);
        byte[] result = value.getBitString(true);
        if (!Arrays.equals(result, new byte[] {0x01, 0x02})) {
            throw new RuntimeException("Unexpected implicit bit string content: "
                    + Arrays.toString(result));
        }
    }

    static void testWrongTagWithoutImplicitThrows() throws Exception {
        byte[] content = {0x00, 0x01, 0x02};
        DerValue value = new DerValue(DerValue.tag_Integer, content);
        try {
            value.getBitString(false);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException for non-BIT STRING tag");
    }
}
