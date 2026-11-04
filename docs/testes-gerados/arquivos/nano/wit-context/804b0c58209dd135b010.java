/*
 * @test
 * @summary Tests for java.util.stream.Stream methods, focusing on exception paths.
 * @run main StreamWitupTest
 */

import java.util.stream.Stream;
import java.util.function.UnaryOperator;

public class StreamWitupTest {

    public static void main(String[] args) {
        testIterateWithNullFunction();
        testIterateWithValidFunction();
    }

    private static void testIterateWithNullFunction() {
        try {
            Stream.iterate("seed", null);
            System.out.println("FAIL: Expected NullPointerException for null function");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null function");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testIterateWithValidFunction() {
        try {
            Stream<String> stream = Stream.iterate("a", s -> s + "b");
            String result = stream.limit(3).reduce((a, b) -> a + "," + b).orElse("");
            if ("a,aab,abb".equals(result)) {
                System.out.println("PASS: iterate with valid function produces expected output");
            } else {
                System.out.println("FAIL: Unexpected output: " + result);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
