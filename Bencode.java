package Sybil.bencode;

import Sybil.bencode.ast.AST;

/**
 * Created by IntelliJ IDEA.
 * User: magnusstubman
 * Date: 16/02/12
 * Time: 16.59
 * <p/>
 * .
 * Sybil.bencode        := bytestring | integers | lists | dictionaries
 * <p/>
 * <p/>
 * bytestring     := NATURAL:STRING // natural is the number of bytes in 'string'
 * <p/>
 * integer        := iNATURALe      // iNATURALe e.g. i10e.
 * <p/>
 * lists          := lCONTENTSe     //  the string "spam" and the number 42 would be encoded as: "l4:spami42ee"
 * <p/>
 * dictionaries   := dCONTENTSe     //  A dictionary that associates the values 42 and "spam" with the keys "foo" and "bar",
 * //  respectively, would be encoded as follows: "d3:bar4:spam3:fooi42ee"
 * //  for readability: "d 3:bar 4:spam 3:foo i42e e"
 */
public class Bencode {

    public static AST parse(byte[] input) {
        Parser parser = new Parser();
        AST ast = parser.parse(input);
        //ast.accept(new ASTPrintVisitor());
        return ast;
    }


}
