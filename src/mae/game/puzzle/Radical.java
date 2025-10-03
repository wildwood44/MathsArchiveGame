package mae.game.puzzle;

public class Radical {
	String inside;
	String outside;
	double index, root;
	public Radical(String value) {
	    String regex = "√";
	    String[] ss = value.split(regex);
	    //double[] result = new double[2];
	    outside = ss[0];
	    inside = ss[1];
	    if(convert(inside) % 1 == 0) { 
	    	double nSquareRoot = Math.sqrt(convert(inside));
		    int squareRootRounded = (int)nSquareRoot;
		    //Here goes the first step of the algorithm
		    //...
		    for (int i = squareRootRounded; i>1; i--) {
		    	if(convert(inside) % Math.pow(i,2)==0) {
		    		index=i;
		    		double s = Math.pow(i,2);
		    		root=convert(inside)/s;
		    	}
		    }
	    }
	}
	
    public double getIndex() {
		return index;
	}

	public double getRoot() {
		return root;
	}

	// Function to convert String to integer
    public static double convert(String str)
    {
    	double foo;
    	try {
    	   foo = Double.parseDouble(str);
    	}
    	catch (NumberFormatException e) {
    	   foo = 0;
    	}
    	return foo;
    }
}
