/*
 * @test
 * @summary Test for Process.pid() method to observe behavior and exception handling.
 * @run main ProcessWitupTest
 */

import java.lang.Process;

public class ProcessWitupTest {

    public static void main(String[] args) {
        testPidMethod();
    }

    public static void testPidMethod() {
        Process process = null;
        try {
            process = new Process() {
                @Override
                public long pid() {
                    throw new UnsupportedOperationException();
                }
                @Override
                public OutputStream getOutputStream() { return null; }
                @Override
                public InputStream getInputStream() { return null; }
                @Override
                public InputStream getErrorStream() { return null; }
                @Override
                public int waitFor() throws InterruptedException { return 0; }
                @Override
                public int exitValue() { return 0; }
                @Override
                public void destroy() {}
            };
            long pid = process.pid();
            System.out.println("PID: " + pid);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
