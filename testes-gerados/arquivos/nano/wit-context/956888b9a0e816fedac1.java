/* @test    @summary Test deserialization of SerialNumber from DerValue, focusing on exception paths.    @run main SerialNumberWitupTest */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorThrowsOnInvalidTag();
        testConstructorThrowsOnEmptyData();
        testConstructorThrowsOnInvalidTagWithData();
    }

    private static void testConstructorThrowsOnInvalidTag() {
        try {
            byte[] invalidTagBytes = {0x00}; // tag != 2
            DerValue dv = new DerValue(invalidTagBytes);
            new SerialNumber(dv);
            System.err.println("Failed: Expected IOException for invalid tag");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testConstructorThrowsOnEmptyData() {
        try {
            byte[] emptyData = {0x02, 0x01, 0x01}; // tag=2, length=1, value=0x01
            DerValue dv = new DerValue(emptyData);
            // Simulate DerValue with no data after tag and length
            DerValue dvEmpty = new DerValue(new byte[0]);
            new SerialNumber(dvEmpty);
            System.err.println("Failed: Expected IOException for empty data");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testConstructorThrowsOnInvalidTagWithData() {
        try {
            byte[] data = {0x00, 0x00}; // invalid tag 0
            DerValue dv = new DerValue(data);
            new SerialNumber(dv);
            System.err.println("Failed: Expected IOException for invalid tag with data");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
