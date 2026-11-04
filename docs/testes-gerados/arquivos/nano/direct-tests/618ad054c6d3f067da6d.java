/* @test    @summary Test ProcessBuilder.command(List<String>) with valid list    @run main ProcessBuilderWitupTest */

import java.lang.ProcessBuilder;
import java.util.Arrays;
import java.util.List;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testCommandWithValidList();
        testCommandWithNull();
    }

    static void testCommandWithValidList() {
        List<String> command = Arrays.asList("echo", "hello");
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(command);
        assert pb.command().equals(command) : "Command list should be set correctly";
    }

    static void testCommandWithNull() {
        ProcessBuilder pb = new ProcessBuilder();
        try {
            pb.command(null);
            assert false : "NullPointerException expected";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
