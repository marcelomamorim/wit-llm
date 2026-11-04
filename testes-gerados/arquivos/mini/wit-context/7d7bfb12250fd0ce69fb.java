/*
 * @test
 * @summary Test java.util.zip.Inflater.setInput(ByteBuffer) for null input and normal input
 * @run main InflaterWitupTest
 */

import java.util.zip.Inflater;
import java.nio.ByteBuffer;
import java.util.Objects;

public class InflaterWitupTest {
    public static void main(String[] args) {
        testSetInputNull();
        testSetInputValid();
        System.out.println("All tests passed.");
    }

    private static void testSetInputNull() {
        Inflater inflater = new Inflater();
        try {
            inflater.setInput((ByteBuffer) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testSetInputValid() {
        Inflater inflater = new Inflater();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        inflater.setInput(buffer);
        // No exception expected, verify no crash and internal state set indirectly
        // Since inputArray is private, we rely on no exception and no crash
    }
}
