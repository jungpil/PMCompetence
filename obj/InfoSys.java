package obj;

import util.Globals;

public class InfoSys extends DMU {
	protected int localKnowledgeIndex[]; // = new int[Globals.N / 2];

	public InfoSys(Location globalLoc) {
		DMUType = "InfoSys";
                globalLocation = globalLoc;

		localKnowledgeIndex = new int[Globals.N / 2];
		for (int i = Globals.N / 2; i < Globals.N; i++) {
			localKnowledgeIndex[i - (Globals.N / 2)] = i;  // for InfoSys -> i = Globals.N / 2 through Globals.N
		}
                
		///if (Globals.debug) { System.out.println("knowledge index created for infoSys"); }

		// set domain 
		for (int i = 0; i < Globals.N; i++) { domain[i] = false; } // first set everything to false;
		for (int i = 0; i < localKnowledgeIndex.length; i++) { domain[localKnowledgeIndex[i]] = true; }
		///if (Globals.debug) { System.out.println("domain created for infoSys"); }
		
		setNeighbors();
		///if (Globals.debug) { System.out.println("reset search history for infoSys"); }
	}

}
