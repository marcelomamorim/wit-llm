/*
 * @test
 * @summary Verifies observable behavior of Unsafe.setMemory on heap byte arrays
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    private static final Unsafe U = Unsafe.getUnsafe();

    public static void main(String[] args) {
        testFillWholeArray();
        testPartialFillKeepsNeighbours();
        testZeroBytesIsNoOp();
        testNegativeValueFill();
        System.out.println("PASSED");
    }

    private static void testFillWholeArray() {
        byte[] arr = new byte[8];
        long base = U.arrayBaseOffset(byte[].class);
        U.setMemory(arr, base, 8, (byte) 0x5A);
        for (int i = 0; i < arr.length; i++) {
            check(arr[i] == (byte) 0x5A, "whole fill at index " + i);
        }
    }

    private static void testPartialFillKeepsNeighbours() {
        byte[] arr = new byte[8];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long base = U.arrayBaseOffset(byte[].class);
        U.setMemory(arr, base + 2, 3, (byte) 7);
        for (int i = 0; i < arr.length; i++) {
            byte expected = (i >= 2 && i < 5) ? (byte) 7 : (byte) 1;
            check(arr[i] == expected, "partial fill at index " + i);
        }
    }

    private static void testZeroBytesIsNoOp() {
        byte[] arr = new byte[4];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 3;
        }
        long base = U.arrayBaseOffset(byte[].class);
        U.setMemory(arr, base, 0, (byte) 0);
        for (int i = 0; i < arr.length; i++) {
            check(arr[i] == 3, "zero-length call modified index " + i);
        }
    }

    private static void testNegativeValueFill() {
        byte[] arr = new byte[5];
        long base = U.arrayBaseOffset(byte[].class);
        U.setMemory(arr, base, 5, (byte) 0xFF);
        for (int i = 0; i < arr.length; i++) {
            check(arr[i] == (byte) 0xFF, "negative value fill at index " + i);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
