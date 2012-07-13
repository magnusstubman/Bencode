package Sybil.bencode;

import Sybil.bencode.ast.BencodeDictionary;
import Sybil.bencode.ast.BencodeList;
import Sybil.bencode.ast.Bytestring;
import Sybil.bencode.ast.Natural;

/**
 * Created by IntelliJ IDEA.
 * User: magnusstubman
 * Date: 17/02/12
 * Time: 16.09
 * To change this template use File | Settings | File Templates.
 */
public interface IVisitor {
    public void visit(BencodeDictionary bencodeDictionary);

    public void visit(BencodeList bencodeList);

    public void visit(Bytestring bytestring);

    public void visit(Natural natural);
}
