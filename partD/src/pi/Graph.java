 package pi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph {
	// to store the node and its called count hashName->count
	Map<Integer, Integer> calleeCountMap = new HashMap<Integer, Integer>();
	// to store the hashCode name and string name pair hashCodeName->stringName
	Map<Integer, String> nodeNameRelation = new HashMap<Integer, String>();
	// to store the caller and calledNodes set caller->calleeSet
	Map<Integer, Set<Integer>> nodeRelation = new HashMap<Integer, Set<Integer>>();
	// to store pair and pair count pairList->count
	Map<Pair<Integer, Integer>,Integer> pairCount= new HashMap<Pair<Integer, Integer>,Integer>();
	
	void addNode(String caller, int callerHash){
		nodeNameRelation.put(callerHash, caller);
	}
	
	void addCallee(int calleeHash){
		if(calleeCountMap.containsKey(calleeHash)){
			calleeCountMap.put(calleeHash, calleeCountMap.get(calleeHash) + 1);
		}
		else{
			calleeCountMap.put(calleeHash,1);
		}
		
	}
	
	void addPair(int caller){
		
		Pair<Integer, Integer> tempList = new Pair<Integer, Integer>(0, 0);
		Set<Integer> callee = new HashSet<Integer>();	
		callee = nodeRelation.get(caller);
		Integer[] newArray = new Integer[callee.size()];
		callee.toArray(newArray);
		for(int i = 0; i < newArray.length; i++)
		{
			for(int j = i+1; j < newArray.length; j++)
			{
				if (nodeNameRelation.get(newArray[i]).compareTo(nodeNameRelation.get(newArray[j])) < 0)
					tempList = new Pair<Integer, Integer>(newArray[i], newArray[j]);
				else
					tempList = new Pair<Integer, Integer>(newArray[j], newArray[i]);				
				if(pairCount.containsKey(tempList)){				
					pairCount.put(tempList, pairCount.get(tempList)+1);
				}
				else{
					pairCount.put(tempList,1);
				}
				
			}		
		}	
	}
//	void printPair(){
//		String[] aTemp = new String[5];
//		for(Pair<Integer, Integer> abc : pairCount.keySet()){
//			aTemp[0] = nodeNameRelation.get(abc.getLeft());
//			aTemp[1] = nodeNameRelation.get(abc.getRight());
//			int count = pairCount.get(abc);
//			System.out.println("pair: "+aTemp[0]+aTemp[1]+","+count);
//		}
//	}


	public void findBug(int support,int confidence){
		int flag = 1;
		Pattern patternLock = Pattern.compile(".*(lock).*");
		Pattern patternTrylock = Pattern.compile(".*(trylock).*");
		Pattern patternUnlock = Pattern.compile(".*(unlock).*");
		for(int x : nodeRelation.keySet()){
			Set<Integer> tempSet = new HashSet<Integer>();
			tempSet = nodeRelation.get(x);
			
			for(int y : tempSet){
				for(Pair<Integer,Integer> tempList : pairCount.keySet()){
					Matcher m = patternUnlock.matcher(nodeNameRelation.get(y));								
					if((tempList.getLeft() == y || tempList.getRight() == y) && m.matches()){
						for(int k :tempSet){
							Matcher g = patternTrylock.matcher(nodeNameRelation.get(k));
							Matcher n = patternLock.matcher(nodeNameRelation.get(k));
							if(g.matches() || n.matches()){
								flag = -1;
							}
						}
					}
					else if((tempList.getLeft() == y || tempList.getRight() == y) && pairCount.get(tempList) >= support && flag == 1){
						int nodeAppearCount = calleeCountMap.get(y);
						int pairAppearCount = pairCount.get(tempList);
						double confidentCount = (double)pairAppearCount / (double)nodeAppearCount * 100;
						
						if(tempList.getLeft()==y){
							if(!tempSet.contains(tempList.getRight()) && confidentCount >= confidence){
								printBug(x, y, tempList,pairAppearCount,confidentCount);
							}
						}else if(tempList.getRight()==y){
							if(!tempSet.contains(tempList.getLeft()) && confidentCount >= confidence){
								printBug(x, y, tempList,pairAppearCount,confidentCount);
							}
						}					
					}
				}
				flag = 1;
			}
		}
	}

	void printBug(int x, int y, Pair<Integer,Integer> tempList,int pairAppearCount,double confidentCount){
        System.out.println("bug: " +
                        nodeNameRelation.get(y)+" in "+nodeNameRelation.get(x)+", " +
                        "pair: "+"("+nodeNameRelation.get(tempList.getLeft())+", "+nodeNameRelation.get(tempList.getRight())+")"+", " +
                        "support: "+pairAppearCount+", " +
                        "confidence: "+String.format("%.2f",confidentCount)+"%");
}
	
	
	void readGraph(String fileName){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			Pattern patternCaller = Pattern.compile("Call graph node for function: \'(\\w+)\'.*");
			while ((line = reader.readLine()) != null){
				//RegExp: ignore null function && external node functions.				
				Matcher m = patternCaller.matcher(line);				
				if(m.matches()){
					String caller = m.group(1);
//					System.out.println(caller);
					int callerHash = caller.hashCode();
					addNode(caller, callerHash);
					
					// to store the set of callee
					Set<Integer> calledNodes = new HashSet<Integer>();
					Pattern patternCallee = Pattern.compile("  CS<\\w+> calls function \'(\\w+)\'.*");
					Pattern patternExternal = Pattern.compile("  CS<\\w+> calls external node.*");
					while((line = reader.readLine())!= null){
//						System.out.println("sub reg");
						
						Matcher n = patternCallee.matcher(line);
						Matcher e = patternExternal.matcher(line);
						
						if (n.matches()){
							String callee = n.group(1);
//							System.out.println(callee);
							int calleeHash = callee.hashCode();
							addNode(callee, calleeHash);
//							addCallee(calleeHash); 
							calledNodes.add(calleeHash);		
						}
						else if(e.matches()){
							continue;
						}
						else break;
					}
					for (int i : calledNodes){
						addCallee(i);
					}
					nodeRelation.put(callerHash, calledNodes);
					addPair(callerHash);
				}
//				System.out.println("tempList: "+pairCount.keySet());
			}
			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	
	}
}
