/*
 * @test
 * @summary Verify Process.info() delegates to toHandle().info() for started and minimal Process subclasses
 * @run main ProcessWitupTest
 */

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class ProcessWitupTest {

    static class NoHandleProcess extends Process {
        @Override
        public OutputStream getOutputStream() {
            return new java.io.ByteArrayOutputStream();
        }

        @Override
        public InputStream getInputStream() {
            return new java.io.ByteArrayInputStream(new byte[0]);
        }

        @Override
        public InputStream getErrorStream() {
            return new java.io.ByteArrayInputStream(new byte[0]);
        }

        @Override
        public int waitFor() throws InterruptedException {
            return 0;
        }

        @Override
        public int exitValue() {
            return 0;
        }

        @Override
        public void destroy() {
        }
    }

    public static void main(String[] args) throws Exception {
        testInfoOnStartedProcess();
        testInfoWithoutToHandleOverride();
    }

    static void testInfoOnStartedProcess() throws Exception {
        String javaPath = System.getProperty("java.home") + File.separator
                + "bin" + File.separator + "java";
        ProcessBuilder pb = new ProcessBuilder(javaPath, "-version");
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
        Process p = pb.start();
        try {
            ProcessHandle.Info info = p.info();
            if (info == null) {
                throw new RuntimeException("info() returned null for a started process");
            }
            int exit = p.waitFor();
            if (exit != 0) {
                throw new RuntimeException("Unexpected exit value: " + exit);
            }
        } finally {
            p.destroy();
        }
    }

    static void testInfoWithoutToHandleOverride() {
        Process p = new NoHandleProcess();
        try {
            p.info();
            throw new RuntimeException("Expected UnsupportedOperationException from info()");
        } catch (UnsupportedOperationException expected) {
            // expected: info() delegates to the default toHandle(), which is unsupported
        }
    }
}
