/*
 * @test
 * @summary Verifies CompletableFuture.completeAsync(Supplier) completes asynchronously, keeps the first completion, and propagates supplier failures
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class CompletableFutureWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testCompletesWithSupplierValue() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletableFuture<String> ret = cf.completeAsync(() -> "value");
        check(ret == cf, "completeAsync must return the same future");
        check("value".equals(cf.join()), "future must complete with supplier value");
        check(cf.isDone(), "future must be done after join");
        check(!cf.isCompletedExceptionally(), "future must not be exceptional");
    }

    static void testAlreadyCompletedIsNotOverwritten() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("first");
        CompletableFuture<String> ret = cf.completeAsync(() -> "second");
        check(ret == cf, "completeAsync must return the same future");
        check("first".equals(cf.join()), "existing completion must be kept");
    }

    static void testSupplierExceptionPropagates() {
        final RuntimeException boom = new RuntimeException("boom");
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.completeAsync(() -> { throw boom; });
        check(cf.handle((v, t) -> t != null).join(), "future must complete exceptionally");
        check(cf.isCompletedExceptionally(), "isCompletedExceptionally must be true");
        try {
            cf.join();
            throw new RuntimeException("join must throw");
        } catch (CompletionException e) {
            check(e.getCause() == boom, "cause must be the supplier exception");
        }
    }

    public static void main(String[] args) throws Exception {
        testCompletesWithSupplierValue();
        testAlreadyCompletedIsNotOverwritten();
        testSupplierExceptionPropagates();
    }
}
