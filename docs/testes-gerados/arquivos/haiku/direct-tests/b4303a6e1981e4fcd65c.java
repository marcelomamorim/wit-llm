/*
 * @test
 * @summary Process.toHandle() default implementation must throw UnsupportedOperationException with the class-based message
 * @run main ProcessWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ProcessWitupTest {

    static class StubProcess extends Process {
        public OutputStream getOutputStream() { return new ByteArrayOutputStream(); }
        public InputStream getInputStream() { return new ByteArrayInputStream(new byte[0]); }
        public InputStream getErrorStream() { return new ByteArrayInputStream(new byte[0]); }
        public int waitFor() throws InterruptedException { return 0; }
        public int exitValue() { return 0; }
        public void destroy() { }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectUnsupported(StubProcess p, String expected) {
        try {
            p.toHandle();
            throw new RuntimeException("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            check(expected.equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StubProcess p = new StubProcess();
        String expected = p.getClass() + ".toHandle() not supported";
        expectUnsupported(p, expected);
        // Repeated invocation must behave identically
        expectUnsupported(p, expected);
    }
}
