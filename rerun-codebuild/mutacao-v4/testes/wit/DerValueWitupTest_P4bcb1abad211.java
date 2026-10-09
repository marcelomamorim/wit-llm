/* @test
   @summary Tests getT61String() method for normal and exceptional behavior.
   @run main DerValueWitupTest_P4bcb1abad211
 */
import java.io.*;
public class DerValueWitupTest_P4bcb1abad211 {
    public static void main(String[] args) throws Exception {
        testValidTag();
        testInvalidTagThrowsIOException();
    }

    private static void testValidTag() throws Exception {
        // Create a DerValue with tag equal to tag_T61String (assumed 20)
        byte tagT61String = 0x14; // 20 in hex
        byte[] dataBytes = "TestString".getBytes("ISO-8859-1");
        DerValue dv = new DerValue(new byte[] {tagT61String});
        // Override getDataBytes to return dataBytes
        dv.buffer = new DerInputBuffer(dataBytes);
        dv.data = new DerInputStream(new ByteArrayInputStream(dataBytes));
        dv.tag = tagT61String;
        String result = dv.getT61String();
        if (!"TestString".equals(result)) {
            throw new AssertionError("Expected 'TestString', got: " + result);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        byte invalidTag = 0x01; // Boolean tag
        DerValue dv = new DerValue(new byte[] {invalidTag});
        dv.tag = invalidTag;
        try {
            dv.getT61String();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("not T61")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
