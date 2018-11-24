class Main
{
	public static void main(String[] args)
	{
		Rectangle rect;
		rect = new Rectangle();
		rect.nWidth = 5;
		rect.nHeight = 2;
		Circle circ;
		circ = new Circle();
		circ.nRadius = 4;
		System.out.println(alfa);
	}
}

class Shape extends Object
{
	public int getArea()
	{
		return 0;
	}
	
	public String draw(String symbol)
	{
		if ((symbol.length != 1) == true) {
			System.out.println("error: symbol must be a single character! " + symbol);
			return "<bad symbol>";
		}
		
		return drawSelf(symbol);
	}
	
	public String drawSelf(String symbol) {
		return "<not implemented>";
	}
	
	public static int calcArea(Shape s)
	{
		return s.getArea();
	}
}

class Rectangle extends Shape
{
	int nWidth;
	int nHeight;
	
	public int getArea()
	{
		if (nWidth <= 0) {
			System.out.println("Rectangle width must be greater than zero!");
			return -1;
		}
		if (!(nHeight > 0)) {
			System.out.println("Rectangle width must be greater than zero!");
			return -1;
		}
		return nWidth * nHeight;
	}
	

	public String drawSelf(String symbol) {
		String result;
		result = "";
		int i;
		int j;
		
		i = 0;
		while (i < nWidth) {
			j = 0;
			while (j < nHeight) {
				if (j == 0 || j == nHeight - 1 || i == 0 || i == nWidth - 1) {
					result = result + "*";
				} else {
					result = result + " ";
				}
				j = j + 1;
			}
			i = i + 1;
		}
		return "<not implemented>";
	}
}

class Circle extends Shape
{
	int nRadius;
	
	public int getArea()
	{		
		return 3 * nRadius * nRadius;
	}
}
