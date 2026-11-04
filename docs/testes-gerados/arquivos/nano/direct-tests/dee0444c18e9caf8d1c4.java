/* @test    @summary Test getUnalignedBitString method of DerValue class    @run main DerValueWitupTest */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testValidBitString();
        testInvalidTag();
    }

    private static void testValidBitString() {
        byte[] bitStringData = {0x03, 0x02, 0x05, 0xA0}; // Tag=0x03, Length=0x02, Content=0x05,0xA0
        DerValue dv = new DerValue(bitStringData);
        try {
            BitArray bitArray = dv.getUnalignedBitString();
            if (bitArray == null) {
                throw new AssertionError("Expected non-null BitArray");
            }
            System.out.println("testValidBitString passed");
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    private static void testInvalidTag() {
        byte[] invalidData = {0x02, 0x01, 0x00}; // Tag=0x02 (INTEGER), should throw
        DerValue dv = new DerValue(invalidData);
        try {
            dv.getUnalignedBitString();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            if (!e.getMessage().contains("not a bit string")) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
            System.out.println("testInvalidTag passed");
        }
    }
}
