package Sybil.bencode;

import Sybil.tools.T;

import java.nio.ByteBuffer;

class Scanner {
    private ByteBuffer input;

    private byte currentByte;
    private char currentChar;
    private TokenKind currentTokenKind;
    //private StringBuffer currentSpelling;
    private byte[] currentBytes;
    private int currentBytesLength = 0;

    private Token previousToken = null;
    //private StringBuffer lastByteStringLength;
    private StringBuilder lastLengthBuilder = new StringBuilder();

    public Scanner(byte[] input) {

        this.input = ByteBuffer.wrap(input);
        byte b;
        byte[] barr;
        lastLengthBuilder = new StringBuilder();

        b = this.input.get();
        barr = new byte[1];
        barr[0] = b;
        currentByte = b;
        currentChar = (new String(barr)).charAt(0);

        //currentChar = input.charAt(0);
        //this.input = input.substring(1);
        //lastByteStringLength = new StringBuffer();
    }

    public Token scan() {
        currentBytesLength = 0;
        currentBytes = new byte[currentBytesLength];
        currentTokenKind = scanToken();
        previousToken = new Token(currentTokenKind, currentBytes);

        return previousToken;

        //currentSpelling = new StringBuffer();
        //currentTokenKind = scanToken();
        //previousToken = new Token(currentTokenKind, currentSpelling.toString());
        //return previousToken;
    }

    private void takeIt() {
        currentBytesLength++;
        byte[] tmp;
        if (currentBytesLength == 1) {
            tmp = new byte[1];
            tmp[0] = currentByte;
        } else {
            tmp = new byte[currentBytesLength];
            for (int i = 0; i < currentBytesLength - 1; i++) {
                tmp[i] = currentBytes[i];
            }
            tmp[currentBytesLength - 1] = currentByte;
        }
        this.currentBytes = new byte[currentBytesLength];
        for (int i = 0; i < currentBytesLength; i++) {
            currentBytes[i] = tmp[i];
            byte[] b = new byte[1];
            b[0] = tmp[i];
            char c = (new String(b).charAt(0));
        }
        byte b;
        byte[] barr;
        b = this.input.get();
        barr = new byte[1];
        barr[0] = b;
        currentByte = b;
        currentChar = (new String(barr)).charAt(0);
    }

    private TokenKind scanToken() {
        /*if (currentByte == 0x00) {
            return TokenKind.EOF;
        }*/

        switch (currentChar) {
            case 'i':
                takeIt();
                lastLengthBuilder = new StringBuilder();
                return TokenKind.I;
            case 'e':
                takeIt();
                lastLengthBuilder = new StringBuilder();
                return TokenKind.E;
            case 'l':
                takeIt();
                lastLengthBuilder = new StringBuilder();
                return TokenKind.L;
            case 'd':
                takeIt();
                lastLengthBuilder = new StringBuilder();
                return TokenKind.D;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                lastLengthBuilder.append(currentChar);
                takeIt();
                return TokenKind.INT;
            case '-':
                lastLengthBuilder.append("-");
                takeIt();
                return TokenKind.DASH;
            case ':':
                takeIt();
                //for (int i = 0; i < Integer.parseInt(lastByteStringLength.toString()); i++) {
                //System.out.println("length:" + Integer.parseInt(lastLengthBuilder.toString()));
                for (int i = 0; i < Integer.parseInt(lastLengthBuilder.toString()); i++) {
                    takeIt();
                }
                lastLengthBuilder = new StringBuilder();
                return TokenKind.COLON;
            default:
                if (currentByte == 0x00) {
                    return TokenKind.ZERO;
                } else {
                    byte[] barr = new byte[1];
                    barr[0] = currentByte;
                    System.out.println("Scanner error. received character: '" + Character.toString(currentChar) + "' hex: " + T.getHex(barr));
                    return TokenKind.ERROR;
                }
        }
    }
}
