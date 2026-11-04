/* @test    @summary Test for Unsafe.copyMemory(long, long, long) method    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testCopyMemoryValid();
        testCopyMemoryNegativeBytes();
        testCopyMemoryZeroBytes();
        testCopyMemoryNullAddresses();
        testCopyMemoryAddressSize();
    }

    static void testCopyMemoryValid() {
        Unsafe unsafe = Unsafe.getUnsafe();
        long srcAddress = unsafe.allocateMemory(8);
        long destAddress = unsafe.allocateMemory(8);
        try {
            unsafe.putLong(srcAddress, 123456789L);
            unsafe.copyMemory(srcAddress, destAddress, 8);
            long value = unsafe.getLong(destAddress);
            if (value != 123456789L) {
                throw new AssertionError("Memory copy failed: expected 123456789, got " + value);
            }
        } finally {
            unsafe.freeMemory(srcAddress);
            unsafe.freeMemory(destAddress);
        }
    }

    static void testCopyMemoryNegativeBytes() {
        Unsafe unsafe = Unsafe.getUnsafe();
        long srcAddress = unsafe.allocateMemory(8);
        long destAddress = unsafe.allocateMemory(8);
        try {
            boolean threw = false;
            try {
                unsafe.copyMemory(srcAddress, destAddress, -1);
            } catch (IllegalArgumentException e) {
                threw = true;
            }
            if (!threw) {
                throw new AssertionError("Expected IllegalArgumentException for negative bytes");
            }
        } finally {
            unsafe.freeMemory(srcAddress);
            unsafe.freeMemory(destAddress);
        }
    }

    static void testCopyMemoryZeroBytes() {
        Unsafe unsafe = Unsafe.getUnsafe();
        long srcAddress = unsafe.allocateMemory(8);
        long destAddress = unsafe.allocateMemory(8);
        try {
            unsafe.putLong(srcAddress, 987654321L);
            unsafe.copyMemory(srcAddress, destAddress, 0);
            long value = unsafe.getLong(destAddress);
            if (value != 987654321L) {
                throw new AssertionError("Memory copy with zero bytes altered memory");
            }
        } finally {
            unsafe.freeMemory(srcAddress);
            unsafe.freeMemory(destAddress);
        }
    }

    static void testCopyMemoryNullAddresses() {
        Unsafe unsafe = Unsafe.getUnsafe();
        boolean threw = false;
        try {
            unsafe.copyMemory(0L, 0L, 8);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException for null addresses");
        }
    }

    static void testCopyMemoryAddressSize() {
        Unsafe unsafe = Unsafe.getUnsafe();
        long addressSize = unsafe.addressSize0();
        if (addressSize != 4 && addressSize != 8) {
            throw new AssertionError("Unexpected address size: " + addressSize);
        }
    }
}
