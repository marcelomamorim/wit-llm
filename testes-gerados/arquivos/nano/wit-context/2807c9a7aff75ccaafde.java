/* @test    @summary Test for CompletableFuture.supplyAsync with null supplier    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testSupplyAsyncWithNullSupplier();
        testSupplyAsyncWithValidSupplier();
    }

    private static void testSupplyAsyncWithNullSupplier() {
        try {
            CompletableFuture.supplyAsync((Supplier<?>) null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testSupplyAsyncWithValidSupplier() {
        Supplier<String> supplier = () -> "test";
        CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier);
        try {
            String result = future.get();
            if ("test".equals(result)) {
                System.out.println("PASS: supplyAsync with valid supplier succeeded with result: " + result);
            } else {
                System.out.println("FAIL: Unexpected result: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception while getting result: " + e);
        }
    }
}
