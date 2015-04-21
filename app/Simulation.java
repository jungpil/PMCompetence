package app;
// This is the one

import util.Globals;
import obj.Organization;
import java.util.Vector;
import java.io.PrintWriter;
import util.StatCalc;

public class Simulation {
	private static Vector<Organization> organizations; //= new Vector<Organization>();
	
	public static void main(String args[]) {
		String configFile = setConfigFile(args);
		Globals.loadGlobals(configFile);
		if (Globals.debug) { System.out.println("configFile loaded"); }
                if (Globals.debug) { System.out.println("degree "+Globals.degree); }
                
                //create numRuns(e.g. 100) landscapes
		for (int j = Globals.startLandscapeID; j < Globals.numRuns; j++) {
			Globals.setRandomNumbers(j);
                        
			// create landscape
			Globals.createLandscape(j);
			if (Globals.debug) { System.out.println("landscape created at " + j); }
                        if (Globals.debug)System.out.println("max fitness: "+Globals.landscape.getMaxFitness());
                        if (Globals.debug)System.out.println("min fitness: "+Globals.landscape.getMinFitness());
					
                        //for each landscape, create numOrgs(e.g. 100) organizations
                        organizations = new Vector<Organization>();
			for (int i = 0; i < Globals.numOrgs; i++) {
                            organizations.add(new Organization(i));
			} 
			//if (Globals.debug) { System.out.println("orgs created for landscape " + j); }                      
			//if (Globals.debug) { System.out.println("initialized for landscape " + j+"\n"); }
                        
			// run for a Globals.periods of time
			run();		
			//if (Globals.debug) { System.out.println("finished running for landscape " + j); }
//			System.out.println("landscape:\t" + j + "\t" + Globals.landscape.getMaxFitness());
//			Globals.landscape.printLandscapeFitness();
			Globals.landscape = null;
		}
	}
	
	private static void run() {
		for (int t = 0; t < Globals.periods + 1; t++) {
			if (Globals.debug) {
				System.out.println("Simulation.run()\tperiod:\t" + t);
			}
                        //each organization runs once for each period
			for (Organization org : organizations) {
//				org.run(t);
				org.run();
			}
                        //print report for each run
			if (Globals.reportLevel.equals("details")) {
				reportDetails(Globals.out, t);
			} else if (Globals.reportLevel.equals("summary")) {
				reportSummary(Globals.out, t);
			}
		}
	}
	
	private static String setConfigFile(String[] args) {
		String retString = "";
		if (args.length > 1) {
			System.err.println("Need at most one argument (config file).  Try again.");
			System.exit(0);
		} else if (args.length == 0) {
			retString = "";
			
		} else {
			retString = args[0];
		}
		return retString;
	}
	
	// A landscape * B organizations * t periods
	private static void reportDetails(PrintWriter pw, int period) {
		for (Organization org : organizations) {
			org.printDetails(pw, period);
		}
	}

        // A landscape * t periods
	private static void reportSummary(PrintWriter pw, int period) {
		// calc average and report average for landscape
		// calc average and report average for landscape
                double max, min, normalizedFitness;
                max = Globals.landscapeMax;
                min = Globals.landscapeMin;
                        
		StatCalc stat = new StatCalc();
		int completed = 0;
		for(Organization org : organizations) {
                        normalizedFitness = (org.getOrgFitness() - min)/(max - min);
                        stat.enter(normalizedFitness);
			///stat.enter(org.getOrgFitness());
//			stat.enter(org.getFitness());
			if (org.finished()) { completed++; }
		}
                //if(period == 0)
                //pw.println("landID"+ "\t" + "max" + "\t" +"min"+"\t"+"period"+"\t"+"completed"+ "\t"+"mean"+"\t"+ "SD"+"\t"+"max"+"\t"+"min"+"\t"+"count"); 
                //pw.println(Globals.landscape.getLandscapeID() + "\t" + max + "\t" +min+"\t" + period + "\t" + completed + "\t" + stat.getMean() + "\t" + stat.getStandardDeviation() + "\t" + stat.getMin() + "\t" + stat.getMax() + "\t" + stat.getCount());
                /*if(period == 0){
                    pw.println("DEGREE: "+Globals.degree +" TYPE: "+Globals.mgtType);
                    pw.println("landID"+"\t"+"period"+"\t"+"completed"+ "\t"+"mean"+"\t"+ "SD"+"\t"+"count");
                
                }*/
                pw.println(Globals.landscape.getLandscapeID() + "\t" +  period + "\t" + completed + "\t" + stat.getMean() + "\t" + stat.getStandardDeviation() + "\t");
	
        }

        
}
