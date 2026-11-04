/*
 * @test
 * @summary Test for SystemModuleFinders.list() method.
 * @run main SystemModuleFindersWitupTest
 */

import java.io.IOException;
import java.util.stream.Stream;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws IOException {
        testListMethod();
    }

    public static void testListMethod() {
        try {
            Stream<String> result = jdk.internal.module.SystemModuleFinders.list();
            if (result != null) {
                // Consume the stream to ensure it is valid
                result.forEach(System.out::println);
            } else {
                throw new AssertionError("Resulting stream is null");
            }
        } catch (IOException e) {
            // Since the method throws IOException, test that it can be thrown
            // For this test, we assume the stream is not closed, so no exception expected
            throw new AssertionError("Unexpected IOException: " + e.getMessage(), e);
        } catch (Exception e) {
            // Fail the test if any other exception occurs
            throw new AssertionError("Unexpected exception: " + e.getMessage(), e);
        }
    }
}
