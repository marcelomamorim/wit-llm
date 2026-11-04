/*
 * @test
 * @summary Test sun.text.normalizer.Norm2AllModes.append method behavior and exception
 * @run main Norm2AllModesWitupTest
 */

import sun.text.normalizer.Norm2AllModes;

public class Norm2AllModesWitupTest {
    public static void main(String[] args) {
        testAppendNormal();
        testAppendThrowsOnSameInstance();
    }

    private static void testAppendNormal() {
        StringBuilder sb = new StringBuilder("Hello");
        CharSequence cs = " World";
        StringBuilder result = new Norm2AllModes.Normalizer2WithImpl(null) {
            @Override
            public StringBuilder normalizeSecondAndAppend(StringBuilder first, CharSequence second, boolean unused) {
                if (first == second) {
                    throw new IllegalArgumentException();
                }
                return first.append(second);
            }
        }.append(sb, cs);
        if (result != sb) {
            throw new AssertionError("append should return the first StringBuilder instance");
        }
        if (!sb.toString().equals("Hello World")) {
            throw new AssertionError("append did not append correctly");
        }
    }

    private static void testAppendThrowsOnSameInstance() {
        Norm2AllModes.Normalizer2WithImpl normalizer = new Norm2AllModes.Normalizer2WithImpl(null) {
            @Override
            public StringBuilder normalizeSecondAndAppend(StringBuilder first, CharSequence second, boolean unused) {
                if (first == second) {
                    throw new IllegalArgumentException();
                }
                return first.append(second);
            }
        };
        StringBuilder sb = new StringBuilder("test");
        try {
            normalizer.append(sb, sb);
            throw new AssertionError("Expected IllegalArgumentException when first == second");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
