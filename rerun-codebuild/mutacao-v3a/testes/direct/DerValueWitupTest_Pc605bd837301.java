/* @test    @summary Test DerValue.getIA5String() with valid IA5 string data    @run main DerValueWitupTest_Pc605bd837301 */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_Pc605bd837301 {

    public static void main(String[] args) throws Exception {
        testGetIA5StringWithValidData();
        testGetIA5StringWithInvalidTag();
    }

    static void testGetIA5StringWithValidData() throws Exception {
        byte[] ia5Data = {0x16, 0x05, 'H', 'e', 'l', 'l', 'o'}; // Tag=0x16, length=5, data="Hello"
        DerValue dv = new DerValue(ia5Data);
        String result = dv.getIA5String();
        assert result.equals("Hello") : "Expected 'Hello', got '" + result + "'";
    }

    static void testGetIA5StringWithInvalidTag() throws Exception {
        byte[] invalidData = {0x02, 0x01, 0x01}; // Tag=0x02 (INTEGER), length=1, data=0x01
        DerValue dv = new DerValue(invalidData);
        try {
            dv.getIA5String();
            assert false : "Expected IOException due to invalid tag";
        } catch (IOException e) {
            assert e.getMessage().contains("not IA5") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
