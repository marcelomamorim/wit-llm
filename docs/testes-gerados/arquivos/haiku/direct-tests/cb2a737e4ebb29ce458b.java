/*
 * @test
 * @summary Verifies that ChunkedOutputStream.write(int) buffers a single byte and emits it as chunk data
 * @modules java.base/sun.net.www.http
 * @run main ChunkedOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import sun.net.www.http.ChunkedOutputStream;

public class ChunkedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        singleByteIsChunkEncoded();
        highBitByteIsWrittenAsUnsignedValue();
    }

    static void singleByteIsChunkEncoded() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ChunkedOutputStream cos = new ChunkedOutputStream(ps);
        cos.write(0x41);
        cos.close();
        byte[] out = baos.toByteArray();
        if (out.length < 4
                || out[0] != '1'
                || out[1] != '\r'
                || out[2] != '\n'
                || out[3] != 'A') {
            throw new RuntimeException("Unexpected chunk output for write(0x41)");
        }
    }

    static void highBitByteIsWrittenAsUnsignedValue() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ChunkedOutputStream cos = new ChunkedOutputStream(ps);
        cos.write(0xFF);
        cos.close();
        byte[] out = baos.toByteArray();
        if (out.length < 4
                || out[0] != '1'
                || out[1] != '\r'
                || out[2] != '\n'
                || out[3] != (byte) 0xFF) {
            throw new RuntimeException("Byte 0xFF was not written as chunk data");
        }
    }
}
