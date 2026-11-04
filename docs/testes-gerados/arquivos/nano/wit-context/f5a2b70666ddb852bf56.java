/*
 * @test
 * @summary Tests for ObjectOutputStream.writeUTF(String s) method, focusing on exception paths.
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteUTFWithLongString();
        testWriteUTFWithEmptyString();
        testWriteUTFWithNullString();
    }

    static void testWriteUTFWithLongString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            String longString = new String(new char[257]).replace('\0', 'a'); // length 257
            oos.writeUTF(longString);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for long string.");
        } catch (IOException e) {
            if (e instanceof java.io.UTFDataFormatException) {
                System.out.println("Passed: Caught expected UTFDataFormatException for long string.");
            } else {
                System.out.println("Failed: Caught unexpected exception: " + e);
            }
        }
    }

    static void testWriteUTFWithEmptyString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeUTF("");
            System.out.println("Passed: Successfully wrote empty string.");
        } catch (IOException e) {
            System.out.println("Failed: Unexpected exception for empty string: " + e);
        }
    }

    static void testWriteUTFWithNullString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeUTF(null);
            System.out.println("Failed: Expected NullPointerException for null string.");
        } catch (NullPointerException e) {
            System.out.println("Passed: Caught expected NullPointerException for null string.");
        } catch (IOException e) {
            System.out.println("Failed: Unexpected IOException for null string: " + e);
        }
    }
}
