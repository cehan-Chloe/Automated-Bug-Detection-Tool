package pi;

public class Pipair {
	 public static void main(String[] args) {
		 String fileName;
		 int T_SUPPORT;
		 int T_CONFIDENCE;
		 // the default value of T_SUPPORT & T_CONFIDENCE
		 int T_SUPPORT_DEFAULT = 3;
		 int T_CONFIDENCE_DEFAULT = 65;
		 // record times to expand the program		 
		 int  EXPAND_TIME = 0;
		 // for Part1 Question(c), we only consider situation that 
		 // the command contains 1,3 or 4 parameters
		 //Otherwise, output error message and exit
		 switch(args.length){
			 case 1:
				 fileName = args[0];
			     Graph graph_1 = new Graph();
			     graph_1.readGraph(fileName, EXPAND_TIME);
			     graph_1.findBug(T_SUPPORT_DEFAULT, T_CONFIDENCE_DEFAULT);
			     break;
			 case 4:
				 fileName = args[0];
				 EXPAND_TIME = Integer.parseInt(args[3]);
				 T_SUPPORT = Integer.parseInt(args[1]);
		    	 T_CONFIDENCE = Integer.parseInt(args[2]);
		    	 Graph graph_4 = new Graph();
		    	 graph_4.readGraph(fileName, EXPAND_TIME);
		    	 graph_4.findBug(T_SUPPORT, T_CONFIDENCE);
			     break;
			 case 3:
				 fileName = args[0];
				 T_SUPPORT = Integer.parseInt(args[1]);
		    	 T_CONFIDENCE = Integer.parseInt(args[2]);
		    	 Graph graph_3 = new Graph();
		    	 graph_3.readGraph(fileName, EXPAND_TIME);
		    	 graph_3.findBug(T_SUPPORT, T_CONFIDENCE);
				 break;
			 default:
			     System.err.println("Error: the number of input variables is wrong.");
				 System.exit(1);
		 }      
	 }
}