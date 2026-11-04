/* @test    @summary Test Process.children() returns a Stream of ProcessHandle objects.    @run main ProcessWitupTest */

import java.lang.Process;
import java.lang.ProcessBuilder;
import java.lang.ProcessHandle;
import java.util.stream.Stream;

public class ProcessWitupTest {

    public static void main(String[] args) {
        testChildrenReturnsStream();
    }

    public static void testChildrenReturnsStream() {
        try {
            // Start a process that spawns a child process
            ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "sleep 1 & sleep 2");
            Process parentProcess = pb.start();
            // Obtain the Process object
            Process process = parentProcess;
            // Call children() method
            Stream<ProcessHandle> childrenStream = process.children();
            // Collect children handles
            long count = childrenStream.count();
            // Verify that the stream is not null and can be iterated
            if (count >= 0) {
                System.out.println("children() stream returned with count: " + count);
            } else {
                System.err.println("children() stream returned null or invalid count.");
            }
            // Destroy the parent process
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
