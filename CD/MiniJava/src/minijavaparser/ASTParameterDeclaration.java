/* Generated By:JJTree: Do not edit this line. ASTParameterDeclaration.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=progen.Entity,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package minijavaparser;

public
class ASTParameterDeclaration extends progen.ProgenNode {
  public ASTParameterDeclaration(int id) {
    super(id);
  }

  public ASTParameterDeclaration(MiniJava p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MiniJavaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=db228fe833779ff285de3957f3e4ce0c (do not edit this line) */
