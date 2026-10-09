/* @test
   @summary Tests getGeneralString() method of DerValue for normal and exceptional behavior.
   @run main DerValueWitupTest_Pe5329d9b0089
 */
import java.io.*;
public class DerValueWitupTest_Pe5329d9b0089 {
    public static void main(String[] args) throws Exception {
        testValidGeneralString();
        testInvalidTagThrowsIOException();
    }

    private static void testValidGeneralString() throws Exception {
        // Simulate a DerValue with tag for GeneralString (assuming tag_GeneralString is 27)
        byte tagGeneralString = 0x1B; // 27 in decimal
        byte[] dataBytes = "TestString".getBytes("ASCII");
        DerValue dv = new DerValue(new byte[] {tagGeneralString});
        // Override getDataBytes to return dataBytes
        dv = new DerValue() {
            @Override
            public byte[] getDataBytes() {
                return dataBytes;
            }
            @Override
            public byte getTag() {
                return tagGeneralString;
            }
        };
        String result = dv.getGeneralString();
        if (!"TestString".equals(result)) {
            throw new AssertionError("Expected 'TestString', got: " + result);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        byte invalidTag = 0x02; // INTEGER tag
        DerValue dv = new DerValue() {
            @Override
            public byte getTag() {
                return invalidTag;
            }
            @Override
            public byte[] getDataBytes() {
                return new byte[] {0x01, 0x02};
            }
        };
        try {
            dv.getGeneralString();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("not GeneralString")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
