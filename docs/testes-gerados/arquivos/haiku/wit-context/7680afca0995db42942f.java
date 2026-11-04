/*
 * @test
 * @summary Verify that the MinimalStage obtrudeValue override throws UnsupportedOperationException and leaves the stage incomplete
 * @run main CompletableFutureWitupTest
 */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<String> base = new CompletableFuture<>();
        CompletionStage<String> stage = base.minimalCompletionStage();
        if (!(stage instanceof CompletableFuture)) {
            throw new AssertionError("minimalCompletionStage must return a CompletableFuture subclass instance");
        }
        CompletableFuture<String> minimal = (CompletableFuture<String>) stage;
        boolean thrown = false;
        try {
            minimal.obtrudeValue("x");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from obtrudeValue on minimal stage");
        }
        if (minimal.isDone()) {
            throw new AssertionError("Minimal stage must not be completed after failed obtrudeValue");
        }
        base.obtrudeValue("v");
        if (!"v".equals(base.join())) {
            throw new AssertionError("Regular obtrudeValue should set the value");
        }
    }
}
