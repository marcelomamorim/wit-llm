/*
 * @test
 * @summary Test for Process.children() method.
 * @run main ProcessWitupTest
 */

import java.lang.Process;
import java.lang.ProcessHandle;
import java.util.stream.Stream;

public class ProcessWitupTest {

    public static void main(String[] args) {
        testChildrenReturnsStream();
    }

    public static void testChildrenReturnsStream() {
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
                    public long pid() { return 1; }
                    @Override
                    public Stream<ProcessHandle> children() {
                        return Stream.empty();
                    }
                    @Override
                    public Stream<ProcessHandle> descendants() { return Stream.empty(); }
                    @Override
                    public ProcessHandle parent() { return null; }
                    @Override
                    public boolean isAlive() { return false; }
                };
            }
        };
        Stream<ProcessHandle> childrenStream = process.children();
        assert childrenStream != null : "children() should not return null";
        // Optionally, check if the stream is empty
        assert !childrenStream.iterator().hasNext() : "Expected empty children stream";
    }
}
