/* @test    @summary Test DerValue.toDerInputStream() for expected behavior and exception handling    @run main DerValueWitupTest */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testValidSequenceTag();
        testValidSetTag();
        testInvalidTagThrowsIOException();
    }

    private static void testValidSequenceTag() {
        try {
            byte sequenceTag = 0x30; // DER sequence tag
            byte[] bufferData = {sequenceTag, 0x00}; // minimal valid buffer
            DerValue dv = new DerValue(bufferData);
            DerValue result = dv.toDerInputStream();
            if (result == null) {
                System.err.println("Failed: toDerInputStream returned null for sequence tag");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for sequence tag: " + e);
            System.exit(1);
        }
    }

    private static void testValidSetTag() {
        try {
            byte setTag = 0x31; // DER set tag
            byte[] bufferData = {setTag, 0x00}; // minimal valid buffer
            DerValue dv = new DerValue(bufferData);
            DerValue result = dv.toDerInputStream();
            if (result == null) {
                System.err.println("Failed: toDerInputStream returned null for set tag");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for set tag: " + e);
            System.exit(1);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        try {
            byte invalidTag = 0xFF; // invalid tag
            byte[] bufferData = {invalidTag, 0x00};
            DerValue dv = new DerValue(bufferData);
            dv.toDerInputStream();
            System.err.println("Failed: Expected IOException was not thrown for invalid tag");
            System.exit(1);
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("rejects tag type")) {
                System.err.println("Failed: IOException message unexpected: " + e);
                System.exit(1);
            }
        }
    }
}
