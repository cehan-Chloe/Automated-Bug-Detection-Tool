package pi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Expand {
	Graph graph ;
	public Expand(Graph graph) {
		this.graph = graph;
	}
	public Expand(){}
	
	// function to clone a new map, and return nodeRelationCopy
	Map<Integer, Set<Integer>> mapClone(Map<Integer, Set<Integer>> callerCalleeSet){
		Map<Integer, Set<Integer>> callerCalleeSetCopy = new HashMap<Integer, Set<Integer>>();
		for(int tempKey : callerCalleeSet.keySet()){
			Set<Integer> setCopy = new HashSet<Integer>();
            Set<Integer> setSrc = callerCalleeSet.get(tempKey);
            for(int srcId : setSrc){
            	setCopy.add(srcId);
            }    
            callerCalleeSetCopy.put(tempKey, setCopy);
		}	
		return callerCalleeSetCopy;
	}
	
	// function to expand for every set
    void expand_once(Map<Integer, Set<Integer>> callerCalleeSetCopy){
//    	System.out.println("enter expand_once");
    	Set<Integer> removeSet= new HashSet<Integer>();
    	Set<Integer> addSet= new HashSet<Integer>();
    	
    	for(int caller : graph.callerCalleeSet.keySet()){
    		Set<Integer> CalleeSet = graph.callerCalleeSet.get(caller);
    		if(CalleeSet.size() == 0)
    			continue;
    			
    		for(int callee : CalleeSet){
    			Set<Integer> setLevel_1 = callerCalleeSetCopy.get(callee);
    			if (setLevel_1.size() > 0){
//    				for(int level_1 : setLevel_1){
//    					Set<Integer> setLevel_2 = callerCalleeSetCopy.get(level_1);
//    					if (setLevel_2.size() > 0){
        				
		    				removeSet.add(callee);
		        			addSet.addAll(setLevel_1);
//                		System.out.format("remove %s from %s\n",
//                      	graph.nodeHashNameString.get(callee),
//                      	graph.nodeHashNameString.get(caller));
//                      for(int nameId : setLevel_1)
//                              System.out.format("\tadd %s\n",graph.nodeHashNameString.get(nameId));
//                      graph.callerCalleeSet.get(caller).addAll(setLevel_1);
//    					}
//    				}
        		}
        	}    		
    		graph.callerCalleeSet.get(caller).removeAll(removeSet);
    		graph.callerCalleeSet.get(caller).addAll(addSet);
    		removeSet.clear();
    		addSet.clear();
    	}	
    }

	void expandGraph(int expand_time){
		Map<Integer, Set<Integer>> callerCalleeSetCopy = mapClone(graph.callerCalleeSet);
		for(int i = 0; i < expand_time; i++){
			expand_once(callerCalleeSetCopy);
		}
	}
}
