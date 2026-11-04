/* @test    @summary Test Runtime.exec(String[]) method with a simple command    @run main RuntimeWitupTest */

import java.io.*;

public class RuntimeWitupTest {

    public static void main(String[] args) throws Exception {
        testExecWithSimpleCommand();
    }

    public static void testExecWithSimpleCommand() throws Exception {
        String[] command = {"java", "-version"};
        Process process = Runtime.getRuntime().exec(command);
        int exitCode = process.waitFor();
        // The command "java -version" typically outputs to stderr and exits with code 0
        // We check that the process completes successfully
        if (exitCode != 0) {
            throw new AssertionError("Expected exit code 0, but got: " + exitCode);
        }
        // Optionally, verify that the process's output streams are accessible
        InputStream errorStream = process.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
        String line;
        boolean hasOutput = false;
        while ((line = reader.readLine()) != null) {
            hasOutput = true;
            // We could check for specific output, but for simplicity, just ensure output exists
        }
        // Ensure that some output was produced
        if (!hasOutput) {
            throw new AssertionError("Expected output from 'java -version' command.");
        }
    }
}
