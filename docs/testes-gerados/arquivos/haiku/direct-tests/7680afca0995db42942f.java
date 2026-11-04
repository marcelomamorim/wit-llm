/*
 * @test
 * @summary CompletableFuture.obtrudeValue: public instances accept forced values, while the minimal stage view rejects them with UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> cf = new CompletableFuture<>();
        cf.obtrudeValue(1);
        if (cf.join() != 1) {
            throw new AssertionError("expected forced value 1, got " + cf.join());
        }
        cf.obtrudeValue(2);
        if (cf.getNow(0) != 2) {
            throw new AssertionError("expected forced value 2 after overwrite");
        }

        CompletionStage<Integer> stage = new CompletableFuture<Integer>().minimalCompletionStage();
        Object o = stage;
        if (!(o instanceof CompletableFuture)) {
            throw new AssertionError("minimal stage is expected to be a CompletableFuture subclass");
        }
        @SuppressWarnings("unchecked")
        CompletableFuture<Integer> minimal = (CompletableFuture<Integer>) o;
        boolean thrown = false;
        try {
            minimal.obtrudeValue(3);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException from minimal stage obtrudeValue");
        }
    }
}
