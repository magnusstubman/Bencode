package Sybil.bencode.ast;

import Sybil.bencode.IVisitor;
import Sybil.tools.T;

/**
 * Created by IntelliJ IDEA.
 * User: magnusstubman
 * Date: 16/02/12
 * Time: 17.15
 * To change this template use File | Settings | File Templates.
 */
public class Natural extends AST {
    private long natural;

    public long getNatural() {
        return natural;
    }

    public Natural(long natural) {

        this.natural = natural;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return Long.toString(this.natural);
    }

    public byte[] toByteArray() { /* Untested. will probably never be used */
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[3 - i] = (byte) (natural >>> (i * 8));
        }
        //System.out.println("ERROR natural.toByteArray");
        //System.exit(-1);
        return T.catBytes("i".getBytes(), T.catBytes(b, "e".getBytes()));
    }
}
