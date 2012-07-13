package Sybil.bencode;

import Sybil.bencode.ast.BencodeDictionary;
import Sybil.bencode.ast.BencodeList;
import Sybil.bencode.ast.Bytestring;
import Sybil.bencode.ast.Natural;

/**
 * Created by IntelliJ IDEA.
 * User: magnusstubman
 * Date: 17/02/12
 * Time: 16.29
 * To change this template use File | Settings | File Templates.
 */
class ASTPrintVisitor implements IVisitor {

    @Override
    public void visit(BencodeDictionary bencodeDictionary) {
        System.out.println("BencodeDictionary begin");
    }

    @Override
    public void visit(BencodeList bencodeList) {
        System.out.println("List begin");
    }

    @Override
    public void visit(Bytestring bytestring) {
        System.out.println("Bytestring: " + bytestring.toString());
    }

    @Override
    public void visit(Natural natural) {
        System.out.println("Natural: " + natural.toString());
    }
}
