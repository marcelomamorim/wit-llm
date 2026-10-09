/* @test    @summary Test Deflater.setInput(ByteBuffer) with non-null ByteBuffer    @run main DeflaterWitupTest_P5da4d20abeee */

import java.nio.ByteBuffer;
import java.util.zip.Deflater;

public class DeflaterWitupTest_P5da4d20abeee {

    public static void main(String[] args) {
        testSetInputWithValidBuffer();
        testSetInputWithNullBuffer();
    }

    static void testSetInputWithValidBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        Deflater deflater = new Deflater();
        deflater.setInput(buffer);
        System.out.println("testSetInputWithValidBuffer passed");
    }

    static void testSetInputWithNullBuffer() {
        try {
            Deflater deflater = new Deflater();
            deflater.setInput(null);
            System.out.println("testSetInputWithNullBuffer failed: no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testSetInputWithNullBuffer passed");
        }
    }
}
