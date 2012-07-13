package Sybil.bencode.ast;

import Sybil.bencode.IVisitor;
import Sybil.tools.T;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: magnusstubman
 * Date: 16/02/12
 * Time: 17.25
 * To change this template use File | Settings | File Templates.
 */
public class BencodeDictionary extends AST {
    private TreeMap<AST, AST> treeMap;

    public BencodeDictionary(TreeMap treeMap) {
        this.treeMap = treeMap;
    }

    public TreeMap getTreeMap() {
        return treeMap;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
        for (Map.Entry<AST, AST> entry : treeMap.entrySet()) {
            entry.getKey().accept(visitor);
            entry.getValue().accept(visitor);
        }
    }

    @Override
    public String toString() {
        String string = "{";
        for (Map.Entry<AST, AST> entry : treeMap.entrySet()) {
            string += entry.getKey().toString();
            string += ":";
            string += entry.getValue().toString();
            string += ", ";
        }
        return string.substring(0, string.length() - 2) + "}";
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[0];
        for (Map.Entry<AST, AST> entry : treeMap.entrySet()) {
            bytes = T.catBytes(bytes, T.catBytes(entry.getKey().toByteArray(), entry.getValue().toByteArray()));
        }
        return T.catBytes(T.catBytes("d".getBytes(), bytes), "e".getBytes());
    }
}
