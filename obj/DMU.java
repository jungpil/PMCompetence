package obj;

import java.util.Collections;
import java.util.Vector;
import util.Globals;

public class DMU {
	protected String DMUType;
	protected Location globalLocation; //initialized while creating subclasses
	protected Location proposedLoc; 
	protected boolean domain[] = new boolean[Globals.N];  // your domain knowledge
	protected Location moveTo;

//	private int index;
	private Vector<Location> neighbors; // initialized when creating DMU object (by resetSearchHistory())
	
        /*public void setLocationwithString(String[] locationStr) {
		proposedLoc = new Location(locationStr);
	}*/
        public void setLocation(Location loc){
            globalLocation = loc;
        }
        public void setProposedLoc(Location loc){
            proposedLoc = loc;
        }
        
        /*
         * only alternate elements within the domain as neighbors
         * e.g. 0 0 0(within domain)  0 0 0 has 3 neighbors
         *      1 0 0 0 0 0, 0 1 0 0 0 0, 0 0 1 0 0 0
         */
	public void setNeighbors() {
            neighbors = new Vector<Location>();
		for (int i = 0; i < Globals.N; i++) {
                    if(domain[i]){
			String[] neighborLocString = new String[Globals.N];
			boolean add = false;
			for (int j = 0; j < Globals.N; j++) {
				if (i == j) {
					if (globalLocation.getLocationAt(j).equals("1")) {
						neighborLocString[j] = "0"; add = true;
					} else if (globalLocation.getLocationAt(j).equals("0")) {
						neighborLocString[j] = "1"; add = true;
					} // else locationAt is blank so do nothing
				} else { // all other i != j
					neighborLocString[j] = globalLocation.getLocationAt(j);
				}
			}
			if (add) { neighbors.add(new Location(neighborLocString)); }
                 }
		}
		//Collections.shuffle(neighbors);  // shuffle so that order of retrieval is randomized
		
		if (Globals.debug) { System.out.println("Neighbors for " + DMUType); printNeighbors(); }
	}

        
	// SEARCH
	public Location search() {
		moveTo = null;
//		boolean success = false;
		int numRemainingNeighbors = neighbors.size();
                if (Globals.debug) System.out.println( DMUType + "neighbor size " + numRemainingNeighbors); 
		int r = Globals.rand.nextInt(numRemainingNeighbors);
                // check whether this neighbor has better fitness, remove it after checking 
		Location neighbor = (Location)neighbors.remove(r); // need to find global location for neighbor as well
		
		double globalFitness = 0d;
		double neighborFitness = 0d;

                globalFitness = Globals.landscape.getFitness(globalLocation,domain);
		neighborFitness = Globals.landscape.getFitness(neighbor,domain);
              
		
                if(Globals.debug)System.out.println(DMUType+" globalLocation:\t" + globalLocation);
                if(Globals.debug)System.out.println(DMUType+" neighborlocation:\t" + neighbor);
		if(Globals.debug)System.out.println(DMUType+" globalFitness:\t" + globalFitness);
		if(Globals.debug)System.out.println(DMUType+" neighborFitness(" + r + "):\t" + neighborFitness);

		if (neighborFitness > globalFitness) {
			moveTo = new Location(neighbor);
		}
		return moveTo;
	}

	
	public boolean isLocalOptimum() {
		return neighbors.isEmpty();
	}
	

	
	
	// debug only
	public void start() {
		setNeighbors();
	}
		
	public void printAllNeighbors() {
		printNeighbors();
	}
        
        private void printNeighbors() {
		for (Location neighbor : neighbors) {
			System.out.println(neighbor.toString());
		}
	}
                   
	public Location getLocation() {
		return globalLocation;
	}
        
        public Location getProposedLoc() {
		return proposedLoc;
	}
  
	public String[] getLocationString() {
		return globalLocation.getLocation();
	}
	public String[] getPropoasedLocationString() {
		return proposedLoc.getLocation();
	}
	public double getProposedFitness() {
		return Globals.landscape.getFitness(proposedLoc);
	}

	public boolean[] getDomainFilter() {
		return domain;
	}

	// for debug purposes only
	public static void main(String args[]) {
		Globals.createLandscape(0);
		Location l = new Location();
		System.out.println(l.toString());
		Business b = new Business(l);
		InfoSys is = new InfoSys(l);
		System.out.println(b.getLocation().toString());
		System.out.println(is.getLocation().toString());
	}
}
