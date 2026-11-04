/* @test    @summary Test for ObjectOutputStream.writeUTF(String s) method    @run main ObjectOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteUTFWithEmptyString();
        testWriteUTFWithRegularString();
        testWriteUTFWithNull();
    }

    static void testWriteUTFWithEmptyString() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeUTF("");
        oos.close();
        byte[] data = baos.toByteArray();
        // Verifica se o método escreve algum dado (não nulo)
        assert data.length > 0 : "Data should not be empty for empty string";
    }

    static void testWriteUTFWithRegularString() throws IOException {
        String testString = "Hello, World!";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeUTF(testString);
        oos.close();
        byte[] data = baos.toByteArray();
        assert data.length > 0 : "Data should not be empty for regular string";
    }

    static void testWriteUTFWithNull() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeUTF(null);
            oos.close();
            assert false : "Expected NullPointerException for null string";
        } catch (NullPointerException e) {
            // esperado
        } catch (IOException e) {
            assert false : "Unexpected IOException: " + e;
        }
    }
}
