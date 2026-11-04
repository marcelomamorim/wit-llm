/*
 * @test
 * @summary Test for Streams.add(T t) method to verify behavior with null input.
 * @run main StreamsWitupTest
 */

import java.util.stream.Stream;
import java.util.stream.Streams;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testAddWithNull();
        testAddWithValidObject();
    }

    private static void testAddWithNull() {
        Stream.Builder<Object> builder = Stream.builder();
        try {
            builder.add(null);
            System.out.println("testAddWithNull: Passed");
        } catch (NullPointerException e) {
            System.out.println("testAddWithNull: Failed - NullPointerException thrown");
        } catch (Exception e) {
            System.out.println("testAddWithNull: Failed - Unexpected exception: " + e);
        }
    }

    private static void testAddWithValidObject() {
        Stream.Builder<String> builder = Stream.builder();
        String obj = "test";
        try {
            builder.add(obj);
            System.out.println("testAddWithValidObject: Passed");
        } catch (Exception e) {
            System.out.println("testAddWithValidObject: Failed - Exception: " + e);
        }
    }
}
