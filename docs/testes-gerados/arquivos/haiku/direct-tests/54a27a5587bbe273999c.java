/*
 * @test
 * @summary Verifies Base64.Encoder wrap OutputStream write(int) masks the argument to one byte and emits encoded output
 * @run main Base64WitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws IOException {
        testWriteIntEncodesThreeBytes();
        testWriteIntMasksHighBits();
        testWriteIntNegativeUsesLowByte();
    }

    static void testWriteIntEncodesThreeBytes() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = Base64.getEncoder().wrap(bos);
        os.write(0x41);
        os.write(0x42);
        os.write(0x43);
        String result = new String(bos.toByteArray(), StandardCharsets.US_ASCII);
        if (!result.equals("QUJD")) {
            throw new RuntimeException("Expected QUJD but got " + result);
        }
    }

    static void testWriteIntMasksHighBits() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = Base64.getEncoder().wrap(bos);
        os.write(0x141);
        os.write(0x142);
        os.write(0x143);
        String result = new String(bos.toByteArray(), StandardCharsets.US_ASCII);
        if (!result.equals("QUJD")) {
            throw new RuntimeException("Expected QUJD for masked input but got " + result);
        }
    }

    static void testWriteIntNegativeUsesLowByte() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = Base64.getEncoder().wrap(bos);
        os.write(-1);
        os.write(0);
        os.write(0);
        String result = new String(bos.toByteArray(), StandardCharsets.US_ASCII);
        if (!result.equals("/wAA")) {
            throw new RuntimeException("Expected /wAA but got " + result);
        }
    }
}
