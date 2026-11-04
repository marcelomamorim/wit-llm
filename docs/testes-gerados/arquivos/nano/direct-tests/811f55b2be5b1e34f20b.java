/* @test    @summary Test writeChars method of ObjectOutputStream    @run main ObjectOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteCharsWithEmptyString();
        testWriteCharsWithSingleCharacter();
        testWriteCharsWithMultipleCharacters();
    }

    static void testWriteCharsWithEmptyString() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        String s = "";
        oos.writeChars(s);
        oos.close();
        byte[] result = baos.toByteArray();
        assert result.length == 0 : "Expected empty byte array for empty string";
    }

    static void testWriteCharsWithSingleCharacter() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        String s = "A";
        oos.writeChars(s);
        oos.close();
        byte[] result = baos.toByteArray();
        assert result.length == 2 : "Expected 2 bytes for single character";
        assert result[0] == 0 : "First byte should be 0";
        assert result[1] == 65 : "Second byte should be ASCII for 'A'";
    }

    static void testWriteCharsWithMultipleCharacters() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        String s = "Hello";
        oos.writeChars(s);
        oos.close();
        byte[] result = baos.toByteArray();
        assert result.length == s.length() * 2 : "Byte array length should be twice the string length";
        for (int i = 0; i < s.length(); i++) {
            int byteIndex = i * 2;
            assert result[byteIndex] == 0 : "High byte should be 0";
            assert result[byteIndex + 1] == (byte) s.charAt(i) : "Byte should match character";
        }
    }
}
