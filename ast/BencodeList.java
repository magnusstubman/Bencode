package Sybil.bencode.ast;

import Sybil.bencode.IVisitor;
import Sybil.tools.T;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: magnusstubman
 * Date: 16/02/12
 * Time: 17.25
 * To change this template use File | Settings | File Templates.
 */
public class BencodeList extends AST {
    private ArrayList<AST> list;

    public BencodeList(ArrayList<AST> list) {
        this.list = list;

    }

    public ArrayList<AST> getList() {
        return list;
    }

    @Override
    public void accept(IVisitor visitor) {
        for (AST ast : list) {
            ast.accept(visitor);
        }
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String string = "[";
        for (AST ast : list) {
            string += ast.toString();
            string += ", ";
        }
        string += "]";
        return string;
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[0];
        for (AST ast : list) {
            bytes = T.catBytes(bytes, ast.toByteArray());
        }
        return T.catBytes(T.catBytes("l".getBytes(), bytes), "e".getBytes());
    }
}
