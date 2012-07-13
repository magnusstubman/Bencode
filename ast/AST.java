package Sybil.bencode.ast;

import Sybil.bencode.IVisitor;

/**
 * Created by IntelliJ IDEA.
 * User: magnusstubman
 * Date: 16/02/12
 * Time: 17.14
 * To change this template use File | Settings | File Templates.
 */
public abstract class AST {
    public abstract void accept(IVisitor visitor);

    public abstract String toString();

    public abstract byte[] toByteArray();

    //public abstract byte[] getTransactionId();
}
