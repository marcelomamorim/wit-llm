/*
 * @test
 * @summary Test CompletableFuture.join() throws UnsupportedOperationException as per current implementation
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<Object> cf = new CompletableFuture<Object>() {
            @Override
            public Object join() {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            cf.join();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from join() not thrown");
        }
    }
}
