/* @test    @summary Test for Deflater.setInput(ByteBuffer) method handling null input    @run main DeflaterWitupTest_P5da4d20abeee */

import java.util.zip.Deflater;
import java.nio.ByteBuffer;

public class DeflaterWitupTest_P5da4d20abeee {

    public static void main(String[] args) {
        testSetInputWithNull();
        testSetInputWithValidBuffer();
    }

    public static void testSetInputWithNull() {
        Deflater deflater = new Deflater();
        try {
            deflater.setInput((ByteBuffer) null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testSetInputWithValidBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(buffer);
            System.out.println("PASS: setInput with valid ByteBuffer executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
