package progen;

public class Entity 
{

	private Integer progenID;
	
	public Integer getProgenID() 
	{
		return progenID;
	}
	
	public void setProgenID(Integer progenID) 
	{
		this.progenID = progenID;
	}
	
	public boolean equals(Object other) 
	{
		if (other instanceof Entity) 
		{
			return ((Entity)other).toString().equals(this.toString());
		}
		return false;
	}
}
