/* Generated By:JJTree: Do not edit this line. ASTFunctionCallExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=progen.Entity,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package minijavaparser;

public
class ASTFunctionCallExpression extends progen.ProgenNode {
  public ASTFunctionCallExpression(int id) {
    super(id);
  }

  public ASTFunctionCallExpression(MiniJava p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MiniJavaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1ea0d1944709e7672595c887a1e46a55 (do not edit this line) */
