/* @test    @summary Test Process.info() method for correct return of ProcessHandle.Info.    @run main ProcessWitupTest */

import java.lang.Process;
import java.lang.ProcessHandle;
import java.lang.ProcessHandle.Info;

public class ProcessWitupTest {

    public static void main(String[] args) {
        testProcessInfo();
    }

    public static void testProcessInfo() {
        Process process = createDummyProcess();
        Info info = process.info();
        if (info == null) {
            throw new AssertionError("Process.info() returned null");
        }
        // Verify that info() returns the same as toHandle().info()
        ProcessHandle handle = process.toHandle();
        Info handleInfo = handle.info();
        if (info != handleInfo) {
            throw new AssertionError("Process.info() does not match toHandle().info()");
        }
        // Additional checks can be added here if needed
        System.out.println("Process.info() test passed.");
    }

    private static Process createDummyProcess() {
        // Create a dummy Process object with minimal implementation for testing
        return new Process() {
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
                    public Info info() {
                        return new Info() {
                            @Override
                            public String command() { return "dummy"; }
                            @Override
                            public String[] arguments() { return new String[0]; }
                            @Override
                            public String commandLine() { return "dummy"; }
                            @Override
                            public long startInstant() { return 0; }
                            @Override
                            public long totalCpuDuration() { return 0; }
                            @Override
                            public boolean isAlive() { return true; }
                        };
                    }
                    @Override
                    public boolean isAlive() { return true; }
                    @Override
                    public void destroy() {}
                };
            }
        };
    }
}
