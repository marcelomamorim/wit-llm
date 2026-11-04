/*
 * @test
 * @summary Test OutputStreamWriter(OutputStream, CharsetEncoder) constructor behavior
 * @run main OutputStreamWriterWitupTest
 */

import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class OutputStreamWriterWitupTest {

    static class DummyOutputStream extends OutputStream {
        public void write(int b) {
            // no-op
        }
    }

    static class DummyCharsetEncoder extends CharsetEncoder {

        protected DummyCharsetEncoder(Charset cs) {
            super(cs, 1.0f, 1.0f);
        }

        @Override
        protected CoderResult encodeLoop(CharBuffer in, java.nio.ByteBuffer out) {
            return CoderResult.UNDERFLOW;
        }
    }

    public static void main(String[] args) throws Exception {
        // Test 1: Normal construction with non-null CharsetEncoder
        OutputStream os = new DummyOutputStream();
        Charset cs = Charset.forName("US-ASCII");
        CharsetEncoder enc = cs.newEncoder();
        OutputStreamWriter writer = new OutputStreamWriter(os, enc);
        if (writer == null) {
            throw new RuntimeException("OutputStreamWriter constructor returned null");
        }

        // Test 2: Construction with null CharsetEncoder should throw NullPointerException
        boolean npeThrown = false;
        try {
            new OutputStreamWriter(os, null);
        } catch (NullPointerException e) {
            if ("charset encoder".equals(e.getMessage())) {
                npeThrown = true;
            } else {
                throw e;
            }
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException not thrown or wrong message");
        }
    }
}
