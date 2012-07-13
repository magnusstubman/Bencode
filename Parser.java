package Sybil.bencode;

import Sybil.bencode.ast.*;
import Sybil.tools.T;

import java.util.ArrayList;
import java.util.TreeMap;

class Parser {
    private Token currentToken;
    private Scanner scanner;

    public AST parse(byte[] input) {
        scanner = new Scanner(input);
        currentToken = scanner.scan();
        if (currentToken.getTokenKind() == TokenKind.ERROR) {
            return null;
        } else {
            return parseBencode();
        }
    }

    private void accept(TokenKind expected) {
        if (currentToken.getTokenKind() == expected) {
            //System.out.print(currentToken.getTokenKind().toString() + "ø" + currentToken.getSpelling() + " ");
            currentToken = scanner.scan();
            //System.out.println(new String(currentToken.getSpelling()));
        } else {
            System.out.println("Parser error. expected '" + expected.toString() + "' got: '" + currentToken.getTokenKind().toString() + "'");
            System.out.println();
            System.out.println();
            Thread.dumpStack();
            System.exit(-1);
        }
    }

    static final String HEXES = "0123456789ABCDEF";

    public static String getHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    private AST parseBencode() {
        if (currentToken.getTokenKind() == TokenKind.INT) {
            return parseBytestring();
        } else if (currentToken.getTokenKind() == TokenKind.I) {
            return parseNatural();
        } else if (currentToken.getTokenKind() == TokenKind.L) {
            return parseBencodeList();
        } else if (currentToken.getTokenKind() == TokenKind.D) {
            return parseBencodeDictionary();
        } else {
            System.out.println(currentToken.getTokenKind().toString());
            //System.out.println(currentToken.getTokenKind().toString());
            System.out.println("'" + getHex(currentToken.getSpelling()) + "'");
            System.out.println("Parser Error1");
            System.exit(-1);
        }
        return null;
    }

    private AST parseBytestring() {
        Bytestring bs;
        accept(TokenKind.INT);
        while (currentToken.getTokenKind() == TokenKind.INT) {
            accept(TokenKind.INT);
        }
        byte[] bytes = currentToken.getSpelling();
        byte[] byteswithoutfirst = new byte[bytes.length - 1];
        /*System.out.println();
        System.out.println(T.getHex(bytes));
        System.out.println();*/
        for (int i = 0; i < byteswithoutfirst.length; i++) {
            byteswithoutfirst[i] = bytes[i + 1];
        }
        bs = new Bytestring(byteswithoutfirst);
        accept(TokenKind.COLON);
        //System.out.print(bs.toString() + " ");
        return bs;
    }

    private AST parseNatural() {
        Natural natural;
        StringBuffer stringBuffer = new StringBuffer();
        accept(TokenKind.I);
        if (currentToken.getTokenKind() == TokenKind.INT) {
            stringBuffer.append(currentToken.getSpelling());
            accept(TokenKind.INT);
        } else if (currentToken.getTokenKind() == TokenKind.DASH) {
            stringBuffer.append(currentToken.getSpelling());
            accept(TokenKind.DASH);
        }
        while (currentToken.getTokenKind() == TokenKind.INT) {
            stringBuffer.append(currentToken.getSpelling());
            accept(TokenKind.INT);
        }
        try {
            natural = new Natural(Long.parseLong(stringBuffer.toString()));
        } catch (Exception e) {
            System.out.println("parseNatural error! " + e.getMessage());
            System.out.println("stringbuffer: '" + T.getRawHex(String.valueOf(stringBuffer).getBytes()) + "'");
            System.out.println("stringbuffer: '" + T.getHex(String.valueOf(stringBuffer).getBytes()) + "'");
            System.out.println("returning new Natural(0)");
            natural = new Natural(0);
        }
        System.out.println("CurrentToken spelling: " + T.getRawHex(currentToken.getSpelling()));
        accept(TokenKind.E);

        return natural;
    }

    private AST parseBencodeList() {
        BencodeList bencodeList;
        ArrayList<AST> list = new ArrayList<AST>();
        accept(TokenKind.L);
        while (currentToken.getTokenKind() != TokenKind.E) {
            if (currentToken.getTokenKind() == TokenKind.INT) {
                list.add(parseBytestring());
            } else if (currentToken.getTokenKind() == TokenKind.I) {
                list.add(parseNatural());
            } else if (currentToken.getTokenKind() == TokenKind.L) {
                list.add(parseBencodeList());
            } else if (currentToken.getTokenKind() == TokenKind.D) {
                list.add(parseBencodeDictionary());
            } else {
                System.out.println("Parser Error2");
                System.exit(-1);
            }
        }
        bencodeList = new BencodeList(list);
        accept(TokenKind.E);
        return bencodeList;
    }

    private AST parseBencodeDictionary() {
        BencodeDictionary bencodeDictionary;
        TreeMap<AST, AST> treeMap = new TreeMap<AST, AST>();
        accept(TokenKind.D);
        while (currentToken.getTokenKind() != TokenKind.E) {
            AST key = null;
            AST value = null;
            if (currentToken.getTokenKind() == TokenKind.INT) {
                Object o = parseBytestring();
                if (!(o instanceof Bytestring)) {
                    System.out.println("Parser Error3");
                    System.exit(-1);
                }
                key = (Bytestring) o;
            } else {
                System.out.println("Parser Error4: '" + currentToken.getTokenKind().toString() + "' - '" + (new String(currentToken.getSpelling())) + "'");
                System.exit(-1);
            }

            if (currentToken.getTokenKind() == TokenKind.INT) {
                value = parseBytestring();
            } else if (currentToken.getTokenKind() == TokenKind.I) {
                value = parseNatural();
            } else if (currentToken.getTokenKind() == TokenKind.L) {
                value = parseBencodeList();
            } else if (currentToken.getTokenKind() == TokenKind.D) {
                value = parseBencodeDictionary();
            } else {
                System.out.println("Parser Error5");
                System.exit(-1);
            }
            treeMap.put(key, value);
        }
        accept(TokenKind.E);
        bencodeDictionary = new BencodeDictionary(treeMap);
        return bencodeDictionary;
    }
}
