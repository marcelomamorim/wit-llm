/*
 * @test
 * @summary Test writeChars with various String inputs for exception paths
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteCharsWithEmptyString();
        testWriteCharsWithNormalString();
        testWriteCharsWithNullString();
        testWriteCharsWithLargeString();
    }

    static void testWriteCharsWithEmptyString() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.writeChars("");
        } catch (Exception e) {
            System.out.println("Empty string test: " + e);
        } finally {
            oos.close();
        }
    }

    static void testWriteCharsWithNormalString() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.writeChars("Hello");
        } catch (Exception e) {
            System.out.println("Normal string test: " + e);
        } finally {
            oos.close();
        }
    }

    static void testWriteCharsWithNullString() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.writeChars(null);
        } catch (NullPointerException e) {
            System.out.println("Null string test: Caught expected NullPointerException");
        } catch (Exception e) {
            System.out.println("Null string test: Unexpected exception: " + e);
        } finally {
            oos.close();
        }
    }

    static void testWriteCharsWithLargeString() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append('A');
        }
        String largeString = sb.toString();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.writeChars(largeString);
        } catch (Exception e) {
            System.out.println("Large string test: " + e);
        } finally {
            oos.close();
        }
    }
}
