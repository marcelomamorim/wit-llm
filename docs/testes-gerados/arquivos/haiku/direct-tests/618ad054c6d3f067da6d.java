/*
 * @test
 * @summary Tests ProcessBuilder.command(List) null handling, return value and stored command observable through command()
 * @run main ProcessBuilderWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testReturnsSameInstance();
        testReplacesCommand();
        testStoresListReference();
        testEmptyListIsAccepted();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullThrowsNullPointerException() {
        ProcessBuilder pb = new ProcessBuilder("a");
        try {
            pb.command((List<String>) null);
            throw new RuntimeException("Expected NullPointerException for null command list");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReturnsSameInstance() {
        ProcessBuilder pb = new ProcessBuilder("a");
        List<String> list = Arrays.asList("x", "y");
        check(pb.command(list) == pb, "command(List) must return this builder");
    }

    static void testReplacesCommand() {
        ProcessBuilder pb = new ProcessBuilder("a");
        List<String> list = new ArrayList<>(Arrays.asList("x", "y"));
        pb.command(list);
        check(pb.command().equals(list), "command() must reflect the list set by command(List)");
    }

    static void testStoresListReference() {
        ProcessBuilder pb = new ProcessBuilder("a");
        List<String> list = new ArrayList<>();
        pb.command(list);
        list.add("z");
        List<String> current = pb.command();
        check(current.size() == 1 && "z".equals(current.get(0)),
              "command(List) stores the given list instance (current behavior)");
    }

    static void testEmptyListIsAccepted() {
        ProcessBuilder pb = new ProcessBuilder("a");
        pb.command(new ArrayList<String>());
        check(pb.command().isEmpty(), "empty list must be accepted and stored");
    }
}
