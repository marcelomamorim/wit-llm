/*
 * @test
 * @summary Test for CompletableFuture.getNow method behavior.
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testGetNowThrowsException();
        testGetNowReturnsValueWhenSet();
    }

    public static void testGetNowThrowsException() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.getNow(null);
            System.out.println("testGetNowThrowsException: FAILED - no exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("testGetNowThrowsException: PASSED");
        } catch (Throwable t) {
            System.out.println("testGetNowThrowsException: FAILED - wrong exception: " + t);
        }
    }

    public static void testGetNowReturnsValueWhenSet() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        String expected = "value";
        // Use reflection or internal API to set the value if needed, but since only public API is allowed,
        // and no public setter exists, we test the default behavior where getNow returns value if completed.
        // Here, we simulate completion by completing the future.
        cf.complete(expected);
        Object result = cf.getNow("default");
        if (expected.equals(result)) {
            System.out.println("testGetNowReturnsValueWhenSet: PASSED");
        } else {
            System.out.println("testGetNowReturnsValueWhenSet: FAILED - got: " + result);
        }
    }
}
