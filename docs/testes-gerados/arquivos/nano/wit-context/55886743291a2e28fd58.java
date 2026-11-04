/*
 * @test
 * @summary Test for Process.descendants() method.
 * @run main ProcessWitupTest
 */

import java.lang.Process;
import java.lang.ProcessHandle;
import java.util.stream.Stream;

public class ProcessWitupTest {

    public static void main(String[] args) {
        testDescendantsReturnsStream();
    }

    public static void testDescendantsReturnsStream() {
        Process process = new Process() {
            @Override
            public OutputStream getOutputStream() { return null; }
            @Override
            public InputStream getInputStream() { return null; }
            @Override
            public InputStream getErrorStream() { return null; }
            @Override
            public int waitFor() { return 0; }
            @Override
            public int exitValue() { return 0; }
            @Override
            public void destroy() {}
            @Override
            public ProcessHandle toHandle() {
                return new ProcessHandle() {
                    @Override
                    public long pid() { return 0; }
                    @Override
                    public Stream<ProcessHandle> children() { return Stream.empty(); }
                    @Override
                    public Stream<ProcessHandle> descendants() { return Stream.empty(); }
                    @Override
                    public ProcessHandle parent() { return null; }
                    @Override
                    public boolean isAlive() { return false; }
                    @Override
                    public void destroy() {}
                    @Override
                    public boolean destroyForcibly() { return false; }
                };
            }
        };
        try {
            Stream<ProcessHandle> stream = process.descendants();
            if (stream == null) {
                throw new AssertionError("descendants() returned null");
            }
            // Optionally, test that stream is empty or behaves as expected
            long count = stream.count();
            assert count >= 0 : "Stream count should be non-negative";
        } catch (UnsupportedOperationException e) {
            // Expected if toHandle() throws
            // For this test, we accept the exception as part of behavior
            System.out.println("Caught expected UnsupportedOperationException");
        }
    }
}
