package progen;

import javax.swing.tree.*;
import java.util.*;
import minijavaparser.*;

public class ProgenNode extends SimpleNode implements TreeNode, Enumeration
{	
	public ProgenNode(int id) 
	{
		super(id);
	}

	public ProgenNode(MiniJava p, int id) 
	{
		super(p, id);
	}

	public String toString() 
	{
		String className = this.getClass().getSimpleName();
		return className;
	}
	
	public boolean existsChild(String value) 
	{
		Production production = new Production(this);
		return production.existsChild(value);
	}
	
	public Entity getPreviousSibling(String value) 
	{
		Production production = new Production(this);
		return production.getPreviousSibling(value);
	}

	public Entity getNextSibling(String value) 
	{
		Production production = new Production(this);
		return production.getNextSibling(value);
	}
	
	public Entity getChild(String value) 
	{
		Production production = new Production(this);
		return production.getChild(value);
	}
	
	/**
	 * Methods from TreeNode interface.
	 */
	public Enumeration children()
	{
  	    return this;
	}

	public boolean getAllowsChildren()
	{
		return (children!=null) && (children.length!=0);
	}

	public TreeNode getChildAt(int nIndex)
	{
		return (ProgenNode)children[nIndex];
	}

	public int getChildCount()
	{
        return (children == null) ? 0 : children.length;
	}

	public int getIndex(TreeNode treeNode)
	{
		for(int nIndex=0;nIndex<children.length;nIndex++)
		{
			if(children[nIndex]==treeNode)
			{
				return nIndex;
			}
		}
		return -1;
	}

	public ProgenNode getParent()
	{
        return (ProgenNode)parent;
	}

	public boolean isLeaf()
	{
		return ((children == null) || (children.length==0)) ? true : false;
	}

	/**
	 * Enumeration methods implemented.
	 */
  
  	int m_nCurrentIndex=0;
	public boolean hasMoreElements()
	{
		return (children!=null) && (children.length>0) &&
                (m_nCurrentIndex<children.length-1);
	}

	public Object nextElement()
	{
		int nIndex;
		nIndex=m_nCurrentIndex;
		if(nIndex<children.length)
		{
			m_nCurrentIndex++;
		}
		else
		{
			m_nCurrentIndex=0;
		}
		return children[nIndex];
	}
}
