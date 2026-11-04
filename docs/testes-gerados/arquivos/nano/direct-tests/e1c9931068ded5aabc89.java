/* @test    @summary Test for Process.pid() method to ensure it returns a non-negative process ID.    @run main ProcessWitupTest */

import java.lang.Process;
import java.lang.ProcessBuilder;
import java.io.IOException;

public class ProcessWitupTest {

    public static void main(String[] args) throws Exception {
        testPidReturnsNonNegative();
    }

    public static void testPidReturnsNonNegative() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("java", "-version");
        Process process = pb.start();
        long pid = process.pid();
        assert pid >= 0 : "Process ID should be non-negative, but was " + pid;
        process.destroy();
        process.waitFor();
    }
}
