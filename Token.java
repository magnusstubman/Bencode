package Sybil.bencode;

/**
 * Created by IntelliJ IDEA.
 * User: magnusstubman
 * Date: 16/02/12
 * Time: 17.51
 * To change this template use File | Settings | File Templates.
 */
class Token {
    private byte[] spelling;
    private TokenKind tokenKind;

    public Token(TokenKind tokenKind, byte[] spelling) {
        this.tokenKind = tokenKind;
        this.spelling = spelling;
    }

    public byte[] getSpelling() {
        return this.spelling;
    }

    public TokenKind getTokenKind() {
        return tokenKind;
    }
}
