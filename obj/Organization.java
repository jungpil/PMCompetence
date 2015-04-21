package obj;

import java.io.PrintWriter;

import util.Globals;

public class Organization {
	//	private int period;  // NO NEED ANYMORE
	protected int index; 
	///protected String orgType;
	protected Location location;  //i.e. global location
	protected DMU[] units = new DMU[Globals.numSubOrgs];  // initialize number of DMUs to Globals.numSubOrgs
///	protected int[] searchStatus = new int[Globals.numSubOrgs]; // -2 for not started; -1 for local optimum; 0 for failed search(means current neighbor has lower fitness, not aLocation good search); 1 for moved
	protected boolean completed;
        public boolean bizCompleted;
        public boolean infoCompleted;
	protected int lastDMU;
///	protected int next = -1; // focal DMU (whose turn is it to search)?
	protected boolean lastPrinted = false;


	public Organization(int idx) {
		index = idx;
                // random location to start with
		location = new Location(); 
                if (Globals.debug) { System.out.println("Initial location " + location+"\n"); }
		units[0] = new Business(location);
		if (Globals.debug) { System.out.println("Business DMU " + idx + " created"+"\n"); }
///		searchStatus[0] = -2;   // DO I NEED THIS?
		units[1] = new InfoSys(location);
		if (Globals.debug) { System.out.println("InfoSys DMU " + idx + " created"+"\n"); }
///		searchStatus[1] = -2;   // DO I NEED THIS?

		/* period = 0; */
                bizCompleted = false;
                infoCompleted = false;
		completed = false;
		//lastDMU = -2;
	}

	public boolean finished() {
            return completed;
	}
        
        public void run(){
            // do nothing if already completed 
            if(!completed){
                
                Location bizProposal=null;
                Location infoProposal=null;
                Location moveTo = location; // initiated as current location
                boolean found = false;
                
                if(bizCompleted){
                    bizProposal = units[0].getProposedLoc();
                    //System.out.println("period "+Globals.periods + "bizCompleted");
                }
                if(infoCompleted)
                    infoProposal = units[1].getProposedLoc();
                
                // find A'
                while(!bizCompleted && !found){
                    bizProposal = units[0].search();
                    if(units[0].isLocalOptimum()){
                        bizProposal = location; // actually propose nothing
                        bizCompleted = true; 
                    } 
                    if (bizProposal != null){
                        if (Globals.debug) { System.out.println("bizProposal "+bizProposal.toString()); }
                        found = true;
                        units[0].setProposedLoc(bizProposal); // save the proposal to DMU for later retrieval 
                    }
                }
                
                /*if(found){
                    if(Globals.debug)System.out.println("bizProposal found");
                }
                if(bizCompleted){
                    if(Globals.debug)System.out.println("biz completed");
                }*/

                found = false; // reset the flag for searching B'

                // find B'
                while(!infoCompleted && !found){
                    infoProposal = units[1].search();
                    if(units[1].isLocalOptimum()){
                        infoCompleted = true;
                        infoProposal = location;
                    }
                    if (infoProposal != null){
                        if (Globals.debug) { System.out.println("infoProposal "+infoProposal.toString()); }
                        found = true;
                        units[1].setProposedLoc(infoProposal);
                    }
                }
               
                /*if(found){
                    if(Globals.debug)System.out.println("infoProposal found");
                }
                if(infoCompleted){
                    if(Globals.debug)System.out.println("info completed");
                }*/

                if(bizCompleted && infoCompleted){
                    completed = true;
                    if(Globals.debug)System.out.println("COMPLETED");
                }

                    // decision made by the project manager
                    if(Globals.mgtType.equals("ACTIVE")){
                        if(Globals.debug) System.out.println("\n Active Manager!!!");
                        String[] dLocString = new String[Globals.N];
                        Location aLocation, bLocation, cLocation, dLocation;  // stand for AB, A'B, AB', A'B' perspectively
                        double aFitness, bFitness, cFitness, dFitness;

                        boolean bizDomain[] = units[0].getDomainFilter();
                        boolean infoDomain[] = units[1].getDomainFilter();

                        for (int i = 0; i < Globals.N; i++){
                            if(bizDomain[i])
                                dLocString[i] = bizProposal.getLocationAt(i);
                            else if(infoDomain[i])
                                dLocString[i] = infoProposal.getLocationAt(i);
                        }

                        aLocation = location;
                        bLocation = units[0].getProposedLoc(); // only accept A'
                        cLocation = units[1].getProposedLoc(); // only accept B'
                        dLocation = new Location(dLocString);  

                        aFitness = Globals.landscape.getFitness(aLocation);
                        //if(Globals.debug) System.out.println("AB fitness "+aFitness);
                        bFitness = Globals.landscape.getFitness(bLocation);
                        //if(Globals.debug) System.out.println("A'B fitness "+bFitness);
                        cFitness = Globals.landscape.getFitness(cLocation);
                        //if(Globals.debug) System.out.println("AB' fitness "+cFitness);
                        dFitness = Globals.landscape.getFitness(dLocation);
                        //if(Globals.debug) System.out.println("A'B' fitness "+dFitness);

                        if(aFitness >= bFitness && aFitness >= cFitness && aFitness >= dFitness){
                            moveTo = aLocation;
                            //if(Globals.debug) System.out.println("AB selected");
                        }
                        else if (bFitness >= aFitness && bFitness >= cFitness && bFitness >= dFitness){
                            moveTo = bLocation;
                            //if(Globals.debug) System.out.println("A'B selected");
                        }
                        else if (cFitness >= aFitness && cFitness >= bFitness && cFitness >= dFitness){
                            moveTo = cLocation;
                            //if(Globals.debug) System.out.println("AB' selected");
                        }
                        else if (dFitness >= aFitness && dFitness >= bFitness && dFitness >= cFitness){
                            moveTo = dLocation;
                            //if(Globals.debug) System.out.println("A'B' selected");
                        }

                    }
                    if(Globals.mgtType.equals("RUB")){
                        if(Globals.debug) System.out.println("\nRubberstamping Manager!!!");
                        String[] dLocString = new String[Globals.N];
                        Location dLocation;  // stand for A'B'

                        boolean bizDomain[] = units[0].getDomainFilter();
                        boolean infoDomain[] = units[1].getDomainFilter();

                        for (int i = 0; i < Globals.N; i++){
                            if(bizDomain[i])
                                dLocString[i] = bizProposal.getLocationAt(i);
                            else if(infoDomain[i])
                                dLocString[i] = infoProposal.getLocationAt(i);
                        }

                        dLocation = new Location(dLocString);              
                        moveTo = dLocation; 

                    }                    
                    
                   // check whether moveTo is a new location
                    if(!location.toString().equals(moveTo.toString())){
                    //only move when moveTo is a new location
                    location = moveTo;
                    if(Globals.debug)System.out.println("new location "+location.toString()+"\n");
                    
                    // reset global location
                    units[0].setLocation(location);
                    units[1].setLocation(location);
                    
                    //reset neighbors
                    units[0].setNeighbors();
                    units[1].setNeighbors();
                    
                    //reset the flag since there are new neibors
                    bizCompleted = false;
                    infoCompleted = false;                
                   
                    }
                    
                }      // end of if(!completed)
                          
        }

	// PRINTERS: for report at detailed level
	public void printDetails(int period) {
		double globalFitness = Globals.landscape.getFitness(location);
		double[] proposedFitness = new double[Globals.numSubOrgs];
		for (int i = 0; i < Globals.numSubOrgs; i++) { // numSubOrgs is hard coded as 2
			proposedFitness[i] = units[i].getProposedFitness();
		}

		String proposedFitnessString = "";
		for (int i = 0; i < Globals.numSubOrgs; i++) { // numSubOrgs is hard coded as 2
			proposedFitnessString += proposedFitness[i] + "\t";
		}
                
                String[] originalLocString = new String[Globals.N];
                Location originalLocation;  // stand for A'B'
                
                boolean bizDomain[] = units[0].getDomainFilter();
                boolean infoDomain[] = units[1].getDomainFilter();
                
                for (int i = 0; i < Globals.N; i++){
                    if(!bizDomain[i])
                        originalLocString[i] = units[0].getProposedLoc().getLocationAt(i);
                    else if(!infoDomain[i])
                        originalLocString[i] = units[1].getProposedLoc().getLocationAt(i);
                }
                originalLocation = new Location(originalLocString);
                
                System.out.println(period + "\t" + index + "\t" +originalLocation.toString() + "\t" + location.toString() + "\t" + proposedFitnessString + globalFitness);

	}

	public void printDetails(PrintWriter pw, int period) {
		double globalFitness = Globals.landscape.getFitness(location);
		double[] proposedFitness = new double[Globals.numSubOrgs];
                Location[] proposedLoc = new Location[Globals.numSubOrgs];
		for (int i = 0; i < Globals.numSubOrgs; i++) { // numSubOrgs is hard coded as 2
                        proposedLoc[i] = units[i].getProposedLoc();
			proposedFitness[i] = units[i].getProposedFitness();
		}
		String proposedFitnessString = "";
                String proposedLocString ="";
		for (int i = 0; i < Globals.numSubOrgs; i++) { // numSubOrgs is hard coded as 2
                        proposedLocString += proposedLoc[i].toString()+"\t";
			proposedFitnessString += proposedFitness[i] + "\t";
		}
                if(period == 0)
                pw.println("landID"+ "\t" +"period"+"\t"+"org idx"+"\t"+ "location" + "\t"+"A'"+"\t\t"+"B'"+"\t\t\t" + "f(A')" +"\t\t\t"+"f(B')" +"\t\t\t\t"+ "global fitness"); 
                
                pw.println(Globals.landscape.getLandscapeID() + "\t" + period + "\t" + index + "\t" + location.toString() + "\t" + proposedLocString + "\t" +proposedFitnessString + "\t" + globalFitness);

	}	

	public void printDMUNeighbors(int i) {
		System.out.println("unit: " + i);
		units[i].printAllNeighbors();
	}

	public void printLocation() {
		System.out.println(location.toString());
	}

	public String toString() {
		String toString = index + "\t" + location.toString() + "\t" + Globals.landscape.getFitness(location);
		return toString;
	}
              
	public double getOrgFitness() {
		return Globals.landscape.getFitness(location);
	}
        
        public DMU getDMU(int i) {
		return units[i];
	}
        
        public static void main(String args[]) {
		Globals.createLandscape(0);
		//		Location l = new Location();
		//		System.out.println("initial location: " + l.toString());
		Organization o = new Organization(0);
		o.printLocation();
		o.printDMUNeighbors(0);
		o.printDMUNeighbors(1);
	}

}
