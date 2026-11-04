/* @test    @summary Test DerInputStream.getSet(int) for exception paths and behavior    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testGetSetWithInvalidTag();
        testGetSetWithUnexpectedEOF();
        testGetSetWithIndexOutOfBounds();
    }

    private static void testGetSetWithInvalidTag() {
        byte[] data = { (byte)0x30, 0x03, 0x02, 0x01, 0x01 }; // Tag not matching DerValue.tag_Set
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getSet(0);
            System.err.println("Failed: Expected IOException for invalid tag");
        } catch (IOException e) {
            System.out.println("Passed: IOException thrown for invalid tag: " + e.getMessage());
        }
    }

    private static void testGetSetWithUnexpectedEOF() {
        byte[] data = { (byte)DerValue.tag_Set, 0x02, 0x01 }; // Incomplete data
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getSet(0);
            System.err.println("Failed: Expected IOException for unexpected EOF");
        } catch (IOException e) {
            System.out.println("Passed: IOException thrown for unexpected EOF: " + e.getMessage());
        }
    }

    private static void testGetSetWithIndexOutOfBounds() {
        byte[] data = { (byte)DerValue.tag_Set, 0x02, 0x01, 0x02 };
        try {
            DerInputStream dis = new DerInputStream(data);
            // Force buffer to simulate index out of bounds during readFully
            dis.buffer = new sun.security.util.DerInputBuffer(new byte[0], 0, 0);
            dis.getSet(0);
            System.err.println("Failed: Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: IndexOutOfBoundsException thrown as expected");
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException: " + e.getMessage());
        }
    }
}
