/*
 * @test
 * @summary Tests for java.util.zip.InflaterInputStream.read()
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadWhenClosed();
        testReadWithNullBuffer();
        testReadWithInvalidBufferSize();
    }

    static void testReadWhenClosed() {
        Inflater inflater = new Inflater();
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        try {
            // Close the stream to simulate closed state
            iis.close();
            iis.read();
            System.out.println("testReadWhenClosed: Failed - no exception thrown");
        } catch (IOException e) {
            if ("Stream closed".equals(e.getMessage())) {
                System.out.println("testReadWhenClosed: Passed");
            } else {
                System.out.println("testReadWhenClosed: Failed - wrong exception message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("testReadWhenClosed: Failed - wrong exception type: " + e);
        }
    }

    static void testReadWithNullBuffer() {
        Inflater inflater = new Inflater();
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        try {
            // Use reflection to set singleByteBuf to null to simulate null buffer
            java.lang.reflect.Field field = InflaterInputStream.class.getDeclaredField("singleByteBuf");
            field.setAccessible(true);
            field.set(iis, null);
            iis.read();
            System.out.println("testReadWithNullBuffer: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testReadWithNullBuffer: Passed");
        } catch (Exception e) {
            System.out.println("testReadWithNullBuffer: Failed - wrong exception: " + e);
        } finally {
            try {
                // Reset the field to avoid side effects
                java.lang.reflect.Field field = InflaterInputStream.class.getDeclaredField("singleByteBuf");
                field.setAccessible(true);
                field.set(iis, new byte[1]);
            } catch (Exception e) {
                // ignore
            }
        }
    }

    static void testReadWithInvalidBufferSize() {
        Inflater inflater = new Inflater();
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        try {
            InflaterInputStream iis = new InflaterInputStream(bais, inflater, 0);
            System.out.println("testReadWithInvalidBufferSize: Failed - no exception thrown");
        } catch (IllegalArgumentException e) {
            if ("buffer size  0".equals(e.getMessage())) {
                System.out.println("testReadWithInvalidBufferSize: Passed");
            } else {
                System.out.println("testReadWithInvalidBufferSize: Failed - wrong message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("testReadWithInvalidBufferSize: Failed - wrong exception: " + e);
        }
    }
}
