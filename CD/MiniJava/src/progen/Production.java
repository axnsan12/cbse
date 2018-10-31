package progen;

import java.util.*;
import minijavaparser.*;

public class Production extends Entity 
{
	
	private ArrayList<Entity> entities;
	
	public Production(SimpleNode node)
	{
		if (node != null) {
			entities = new ArrayList<Entity>();
			Token nextToken = node.jjtGetFirstToken();
			Token token = new Token();
			token.next = nextToken;
			
			int i;
			SimpleNode child;
			for (i = 0; i < node.jjtGetNumChildren(); i++) {
				child = (SimpleNode)node.jjtGetChild(i);
				while (true) {
					token = token.next;
					if (token == child.jjtGetFirstToken())
						break;
					entities.add(token);
				}
				entities.add(child);
				token = child.jjtGetLastToken();
			}
			while (token != node.jjtGetLastToken())
			{
				token = token.next;
				entities.add(token);
			}
		}
	}
	
	public boolean existsChild(String value) 
	{
		int i;
		for (i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e.toString().equals(value))
				return true;
		}
		return false;
	}
	
	public Entity getChild(String value) 
	{
		int i;
		for (i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e.toString().equals(value))
				return e;
		}
		return null;
	}
	
	public Entity getPreviousSibling(String value)
	{
		int i;
		for (i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e.toString().equals(value)) {
				if (i == 0)
					return null;
				else
					return entities.get(i - 1);
			}
		}
		return null;
	}

	public Entity getNextSibling(String value)
	{
		int i;
		for (i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e.toString().equals(value)) {
				if (i == entities.size() - 1)
					return null;
				else
					return entities.get(i + 1);
			}
		}
		return null;
	}
}
