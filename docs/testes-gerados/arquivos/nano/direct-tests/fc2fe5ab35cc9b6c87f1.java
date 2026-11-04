/* @test    @summary Tests for java.io.PushbackReader constructors and read methods    @run main PushbackReaderWitupTest */

import java.io.StringReader;
import java.io.IOException;
import java.io.PushbackReader;

public class PushbackReaderWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidSize();
        testConstructorWithInvalidSize();
        testReadSingleCharacter();
        testReadWithBuffer();
    }

    static void testConstructorWithValidSize() {
        StringReader sr = new StringReader("abc");
        PushbackReader pbr = new PushbackReader(sr, 5);
        assert pbr != null : "Constructor with valid size should create instance";
    }

    static void testConstructorWithInvalidSize() {
        StringReader sr = new StringReader("abc");
        try {
            new PushbackReader(sr, 0);
            assert false : "Constructor with size 0 should throw IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("size") : "Exception message should mention size";
        }
    }

    static void testReadSingleCharacter() {
        StringReader sr = new StringReader("xyz");
        PushbackReader pbr = new PushbackReader(sr, 2);
        try {
            int ch1 = pbr.read();
            int ch2 = pbr.read();
            int ch3 = pbr.read();
            int ch4 = pbr.read();
            assert ch1 == 'x' : "First read should return 'x'";
            assert ch2 == 'y' : "Second read should return 'y'";
            assert ch3 == 'z' : "Third read should return 'z'";
            assert ch4 == -1 : "Fourth read should return -1 at end of stream";
        } catch (IOException e) {
            assert false : "IOException should not occur during read";
        }
    }

    static void testReadWithBuffer() {
        StringReader sr = new StringReader("abcde");
        PushbackReader pbr = new PushbackReader(sr, 2);
        try {
            char[] buffer = new char[3];
            int readCount = pbr.read(buffer, 0, 3);
            assert readCount == 3 : "Should read 3 characters";
            assert buffer[0] == 'a' : "First buffer char should be 'a'";
            assert buffer[1] == 'b' : "Second buffer char should be 'b'";
            assert buffer[2] == 'c' : "Third buffer char should be 'c'";
        } catch (IOException e) {
            assert false : "IOException should not occur during read";
        }
    }
}
