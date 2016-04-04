package pi;

public class Pipair {
	 public static void main(String[] args) {
		 String fileName = args[0];
		 int T_SUPPORT;
		 int T_CONFIDENCE;
		 int T_SUPPORT_DEFAULT = 3;
		 int T_CONFIDENCE_DEFAULT = 65;
		 //check the number of input parameters, it can be 1 or 3. 
		 //Otherwise, output error message and exit
	     if (args.length == 3){
	    	 T_SUPPORT = Integer.parseInt(args[1]);
	    	 T_CONFIDENCE = Integer.parseInt(args[2]);
	    	 Graph graph = new Graph();
		     graph.readGraph(fileName);
		     graph.findBug(T_SUPPORT, T_CONFIDENCE);
//		     graph.printPair();
		 }
		 else if(args.length == 1){
			 Graph graph = new Graph();
		     graph.readGraph(fileName);
		     graph.findBug(T_SUPPORT_DEFAULT, T_CONFIDENCE_DEFAULT);
//		     graph.printPair();
		 }else{
			 System.err.println("Error: the number of input variables is wrong.");
			 System.exit(1);
		 }
	     
	 }
}
