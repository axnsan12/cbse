/* Generated By:JJTree: Do not edit this line. ASTPrintStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=progen.Entity,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package minijavaparser;

public
class ASTPrintStatement extends progen.ProgenNode {
  public ASTPrintStatement(int id) {
    super(id);
  }

  public ASTPrintStatement(MiniJava p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MiniJavaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4223efe5883ef8b73732961db9743d97 (do not edit this line) */
