package Sybil.bencode;

/**
 * Created by IntelliJ IDEA.
 * User: magnusstubman
 * Date: 16/02/12
 * Time: 17.52
 * To change this template use File | Settings | File Templates.
 */
enum TokenKind {
    I,
    E,
    L,
    D,
    COLON,
    INT,
    DASH,
    EOF,
    ZERO,

    ERROR;
}
