package progen;

import minijavaparser.ASTAssignmentStatement;
import minijavaparser.ASTBlockStatement;
import minijavaparser.ASTClassDeclaration;
import minijavaparser.ASTClassName;
import minijavaparser.ASTFuncName;
import minijavaparser.ASTIfStatement;
import minijavaparser.ASTLogicalNegationExpression;
import minijavaparser.ASTMainClass;
import minijavaparser.ASTMethodDeclaration;
import minijavaparser.ASTNewExpression;
import minijavaparser.ASTParameterDeclaration;
import minijavaparser.ASTPrintStatement;
import minijavaparser.ASTProgram;
import minijavaparser.ASTReturnStatement;
import minijavaparser.ASTVarName;
import minijavaparser.ASTVariableDeclaration;
import minijavaparser.ASTWhileStatement;
import minijavaparser.MiniJavaVisitor;
import minijavaparser.SimpleNode;
import minijavaparser.Token;

public class CD05Visitor implements MiniJavaVisitor	 {

	@Override
	public Object visit(SimpleNode node, Object data) {
		System.out.print(node + ":");
		for (Token t = node.jjtGetFirstToken(); t != null; t = t.next) {
			System.out.print(" " + t.image);
			if (t == node.jjtGetLastToken()) {
				break;
			}
		}
		System.out.println();
		node.childrenAccept(this, data);
		
		return null;
	}

	@Override
	public Object visit(ASTProgram node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTMainClass node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTClassDeclaration node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTMethodDeclaration node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTParameterDeclaration node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTVariableDeclaration node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTBlockStatement node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTIfStatement node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTWhileStatement node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTPrintStatement node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTAssignmentStatement node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTReturnStatement node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTLogicalNegationExpression node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTNewExpression node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTClassName node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTVarName node, Object data) {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTFuncName node, Object data) {
		return visit((SimpleNode) node, data);
	}

}
