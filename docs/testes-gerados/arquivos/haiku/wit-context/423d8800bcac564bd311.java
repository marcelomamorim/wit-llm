/*
 * @test
 * @summary Verify CompletableFuture.getNow behavior, including UnsupportedOperationException from the minimal completion stage
 * @run main CompletableFutureWitupTest
 */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testMinimalStageGetNowThrowsUnsupportedOperationException();
        testIncompleteFutureGetNowReturnsValueIfAbsent();
        testCompletedFutureGetNowReturnsResult();
    }

    static void testMinimalStageGetNowThrowsUnsupportedOperationException() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletionStage<String> stage = cf.minimalCompletionStage();
        check(stage instanceof CompletableFuture, "minimal stage should be a CompletableFuture instance");
        CompletableFuture<String> minimal = (CompletableFuture<String>) stage;
        boolean thrown = false;
        try {
            minimal.getNow("absent");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "getNow on minimal stage must throw UnsupportedOperationException");
    }

    static void testIncompleteFutureGetNowReturnsValueIfAbsent() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        String result = cf.getNow("absent");
        check("absent".equals(result), "getNow on incomplete future should return valueIfAbsent, got " + result);
    }

    static void testCompletedFutureGetNowReturnsResult() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("value");
        String result = cf.getNow("absent");
        check("value".equals(result), "getNow on completed future should return result, got " + result);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
