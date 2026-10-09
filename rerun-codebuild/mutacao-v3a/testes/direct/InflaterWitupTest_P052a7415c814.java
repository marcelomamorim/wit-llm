/*
 * @test    @summary Test setInput(ByteBuffer) method of Inflater class
 * @run main InflaterWitupTest_P052a7415c814
 */

import java.util.zip.Inflater;
import java.nio.ByteBuffer;

public class InflaterWitupTest_P052a7415c814 {

    public static void main(String[] args) {
        testSetInputWithValidBuffer();
        testSetInputWithNullBuffer();
    }

    static void testSetInputWithValidBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        Inflater inflater = new Inflater();
        inflater.setInput(buffer);
        System.out.println("setInput with valid ByteBuffer executed successfully.");
    }

    static void testSetInputWithNullBuffer() {
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(null);
            System.err.println("Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("setInput with null ByteBuffer threw NullPointerException as expected.");
        }
    }
}
