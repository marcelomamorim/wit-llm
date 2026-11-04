/*
 * @test
 * @summary Test Process.pid() method for expected behavior
 * @run main ProcessWitupTest
 */

public class ProcessWitupTest {
    public static void main(String[] args) throws Exception {
        Process p = new Process() {
            @Override
            public java.lang.ProcessHandle toHandle() {
                return new java.lang.ProcessHandle() {
                    @Override
                    public long pid() {
                        return 12345L;
                    }
                    @Override
                    public java.util.Optional<java.lang.ProcessHandle> parent() {
                        return java.util.Optional.empty();
                    }
                    @Override
                    public java.util.stream.Stream<java.lang.ProcessHandle> children() {
                        return java.util.stream.Stream.empty();
                    }
                    @Override
                    public java.util.stream.Stream<java.lang.ProcessHandle> descendants() {
                        return java.util.stream.Stream.empty();
                    }
                    @Override
                    public java.lang.ProcessHandle.Info info() {
                        return null;
                    }
                    @Override
                    public boolean supportsNormalTermination() {
                        return false;
                    }
                    @Override
                    public boolean isAlive() {
                        return false;
                    }
                    @Override
                    public boolean destroy() {
                        return false;
                    }
                    @Override
                    public boolean destroyForcibly() {
                        return false;
                    }
                    @Override
                    public java.util.concurrent.CompletableFuture<java.lang.ProcessHandle> onExit() {
                        return null;
                    }
                };
            }
            @Override
            public java.io.OutputStream getOutputStream() { return null; }
            @Override
            public java.io.InputStream getInputStream() { return null; }
            @Override
            public java.io.InputStream getErrorStream() { return null; }
            @Override
            public int waitFor() { return 0; }
            @Override
            public int exitValue() { return 0; }
            @Override
            public void destroy() {}
            @Override
            public ProcessHandle toHandle() { return toHandle(); }
        };

        long pid = p.pid();
        if (pid != 12345L) {
            throw new AssertionError("Expected pid 12345 but got " + pid);
        }
    }
}
