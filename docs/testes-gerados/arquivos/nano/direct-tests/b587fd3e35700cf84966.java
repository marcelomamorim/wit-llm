/* @test    @summary Test CompletableFuture.thenAccept with normal completion    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testThenAcceptCompletesSuccessfully();
        testThenAcceptWithNullAction();
    }

    private static void testThenAcceptCompletesSuccessfully() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        final boolean[] called = {false};
        Consumer<String> action = s -> { called[0] = true; };
        CompletableFuture<Void> result = cf.thenAccept(action);
        cf.complete("test");
        result.join();
        assert called[0] : "thenAccept action was not called after completion";
    }

    private static void testThenAcceptWithNullAction() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            cf.thenAccept(null).join();
            assert false : "Expected NullPointerException when passing null to thenAccept";
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
