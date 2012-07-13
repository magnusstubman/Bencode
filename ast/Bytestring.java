package Sybil.bencode.ast;

import Sybil.bencode.IVisitor;
import Sybil.tools.T;

/**
 * Created by IntelliJ IDEA.
 * User: magnusstubman
 * Date: 16/02/12
 * Time: 17.24
 * To change this template use File | Settings | File Templates.
 */
public class Bytestring extends AST implements Comparable<Bytestring> {
    private byte[] bytes;

    public Bytestring(byte[] string) {
        this.bytes = string;
    }

    public byte[] getRawBytes() {
        return bytes;
    }

    public void setString(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "\"" + new String(this.bytes) + "\"";
    }

    @Override
    public int compareTo(Bytestring bytestring) {
        return this.toString().compareTo(bytestring.toString());
    }

    public byte[] toByteArray() {
        return T.catBytes((new Integer(bytes.length)).toString().getBytes(), T.catBytes(":".getBytes(), bytes));
    }
}
