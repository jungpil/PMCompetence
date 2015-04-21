package obj;

import util.Globals;

// if don't have domain size, by default, business and infosys 1:1 share. i.e. biz: [0,N/2-1] infosys: [N/2,N-1]
public class Business extends DMU {
	protected int localKnowledgeIndex[]; // = new int[Globals.N / 2];

	public Business(Location globalLoc) {
		DMUType = "business";
                globalLocation = globalLoc;
		localKnowledgeIndex = new int[Globals.N / 2];
                
		for (int i = 0; i < Globals.N / 2; i++) {
			localKnowledgeIndex[i] = i;  // for InfoSys -> i = Globals.N / 2 through Globals.N
		}
		
		// set domain 
		for (int i = 0; i < Globals.N; i++) { domain[i] = false; } // first set everything to false;
		for (int i = 0; i < localKnowledgeIndex.length; i++) { domain[localKnowledgeIndex[i]] = true; } // for own domain, both know and has control

		setNeighbors();
	}

}
