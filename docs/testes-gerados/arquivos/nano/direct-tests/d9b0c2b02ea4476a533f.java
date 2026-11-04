/* @test    @summary Test ProcessBuilder constructor with valid command list    @run main ProcessBuilderWitupTest */

import java.lang.ProcessBuilder;
import java.util.Arrays;
import java.util.List;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidCommand();
        testConstructorWithNullCommand();
    }

    static void testConstructorWithValidCommand() {
        List<String> command = Arrays.asList("echo", "hello");
        ProcessBuilder pb = new ProcessBuilder(command);
        assert pb.command().equals(command) : "Command list should match input";
    }

    static void testConstructorWithNullCommand() {
        try {
            new ProcessBuilder((List<String>)null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
