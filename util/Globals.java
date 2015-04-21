package util;

///import util.MersenneTwisterFast;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Random;

import obj.InfluenceMatrix;
import obj.Landscape;

public class Globals {
	/**
	 * simulation parameters: default values
	 */
	public static int N;
//	public static int K = 2; // no need
	public static int periods;
//	private static String outfilename = "results/test.txt";
	private static String outfilename = "/Users/qiuyan/git/koverlap/result/result.txt";
///	private static String influenceMatrixFile = "conf/n16k0.txt";
        private static String influenceMatrixFile;
	public static int numOrgs;
	public static int numSubOrgs;

///	public static int numAlternatives;
	public static Landscape landscape;
//	public static String reportLevel = "details";
	public static String reportLevel;
	public static boolean debug = false;
	public static boolean replicate = false; 
//	private static long seed = 1261505528597l;
	public static double landscapeMax;
        public static double landscapeMin;
	public static int numRuns;
	public static int startLandscapeID;
///	public static String localAssessment = "ac2010"; // for almirall & casadesus-masanell 2010 or "gl2000" for gavetti and levinthal
	public static String mgtType;
        public static double degree;
	/**
	 * utils
	 */
	//public static long runID = System.currentTimeMillis(); // need?
	private static long runID = 1261505528597l;
	public static PrintWriter out;
	public static MersenneTwisterFast rand = new MersenneTwisterFast(runID);
//	public static MersenneTwisterFast nkrnd = new MersenneTwisterFast(seed);
	public static Random random = new Random();

	public static void loadGlobals(String configFile) {
		if (!configFile.equals("")) {
			Properties p = new Properties();
			try {
				p.load(new FileInputStream(configFile));
				// simulation parameters
//				seed = Long.parseLong(p.getProperty("seed"));
				periods = Integer.parseInt(p.getProperty("periods"));
                                /// WHAT"S THIS??????
				numRuns = Integer.parseInt(p.getProperty("runs"));
				outfilename = p.getProperty("outfile");
				influenceMatrixFile = p.getProperty("influenceMatrix");
				numOrgs = Integer.parseInt(p.getProperty("numOrgs"));
                                numSubOrgs = Integer.parseInt(p.getProperty("numSubOrgs"));                          
				N = Integer.parseInt(p.getProperty("N"));
                                mgtType = p.getProperty("mgtType");
                                degree = Double.parseDouble(p.getProperty("degree"));
				String startLandscapeIDStr = p.getProperty("startLandscapeID");
				if (startLandscapeIDStr == null) {
					startLandscapeID = 0;
				} else {
					startLandscapeID = Integer.parseInt(startLandscapeIDStr);
				}
				// [end add]
				reportLevel = p.getProperty("reportLevel");
				String debugString = p.getProperty("debug");
				if (debugString.equals("true") || debugString.equals("1")) { debug = true; }
///				localAssessment = p.getProperty("fitnessCalc");
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			} // END try..catch

			// calculate derived values if any
//			numAlternatives = (int)(N / numSubOrgs);

		}  // end if confFile
		
		try {
			// create output printwriter
			out = new PrintWriter(new FileOutputStream(outfilename, true), true);
		} catch (IOException io) {
			System.err.println(io.getMessage());
			io.printStackTrace();
		}
	}
	
	public static void createLandscape(int id) {
		landscape  = new Landscape(id, new InfluenceMatrix(influenceMatrixFile));
		landscapeMax = landscape.getMaxFitness();
                landscapeMin = landscape.getMinFitness();
	}
	
	public static void setRandomNumbers(int intRunID) {
		long runID;
		if (replicate) { runID = (long)intRunID;
		} else { runID = System.currentTimeMillis(); }
		rand = new MersenneTwisterFast(runID);
	} 
	

	public static void main(String[] args) {
//		long runID = 1261505528597l;
//		long runID = Long.parseLong(args[0]);
//		System.out.println(runID);
                loadGlobals("/Users/qiuyan/git/koverlap/conf/a_n16k0_10,6_4_4_0.conf");
 
                System.out.println("periods "+periods);
                System.out.println("runs "+ numRuns);
                System.out.println("numOrgs "+numOrgs);
                System.out.println("N "+N);
                System.out.println("numSubOrgs "+numSubOrgs);
                System.out.println("reportLevel "+reportLevel);
                System.out.println("degree "+degree);
                System.out.println("mgtType "+mgtType);
                System.out.println("influenceMatrixFile "+influenceMatrixFile);
                
	}

}
