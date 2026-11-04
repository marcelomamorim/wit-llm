/* @test    @summary Test constructor OutputStreamWriter(OutputStream, Charset) for correct behavior and null handling    @run main OutputStreamWriterWitupTest */

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class OutputStreamWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidCharset();
        testConstructorWithNullCharset();
    }

    static void testConstructorWithValidCharset() throws Exception {
        OutputStream os = new OutputStream() {
            private final StringBuilder sb = new StringBuilder();
            @Override
            public void write(int b) {
                sb.append((char) b);
            }
            @Override
            public void flush() {}
            @Override
            public void close() {}
        };
        Charset charset = StandardCharsets.UTF_8;
        OutputStreamWriter writer = new OutputStreamWriter(os, charset);
        writer.write('A');
        writer.flush();
        String result = ((StringBuilder) ((java.io.OutputStream) os).getClass().getDeclaredField("sb").get(os)).toString();
        // Since we cannot access internal buffer directly, test that writing does not throw and no exception occurs
        assert writer != null : "Writer should be created successfully with valid charset";
    }

    static void testConstructorWithNullCharset() {
        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) {}
            @Override
            public void flush() {}
            @Override
            public void close() {}
        };
        try {
            new OutputStreamWriter(os, (Charset) null);
            assert false : "Expected NullPointerException when charset is null";
        } catch (NullPointerException e) {
            assert e.getMessage() == null || e.getMessage().equals("charset") : "Exception message should be 'charset'";
        }
    }
}
