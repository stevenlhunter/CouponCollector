import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class RandomCaster {

	private int numRoles;
	private int numTrials;
	
	private List<String> cast;
	
	public RandomCaster(int numRoles, int numTrials) {
		super();
		this.numRoles = numRoles;
		this.numTrials = numTrials;
		cast = new ArrayList<String>();
		char castName = 'A';
		for (int i = 0; i < numRoles; i++) {
			cast.add( Character.toString(castName) );
			castName++;
		}
//		System.out.println( "cast=" + cast );
	}

	public List<Integer> runTrials(){
		List<Integer> results = new ArrayList<Integer>();
		int countTrials = 0;
		while( countTrials < numTrials ){
			results.add( runTrial() );
			countTrials++;
		}
		return results;
	}

	private int runTrial(){
		int countCastings = 0;
		Set<String> roleTracker = new HashSet<String>();

		while( true ){
			countCastings++;
			Collections.shuffle(cast);
//			System.out.println( "Iteration " +
//					countCastings + ": after drawing all names, cast looks like " + cast );
			//let's just say item at zero is hamlet
			roleTracker.add( cast.get(0) );
//			System.out.println( "role tracker now looks like: " + roleTracker + "\n");
			if( roleTracker.size() == numRoles 	){
				break;
			}
		}
		return countCastings;
	}

	/**
	 * @param results
	 */
	/**
	 * @param results
	 */
	private void printResults( List<Integer> results ){
		int min = Integer.MAX_VALUE;
		int max = 0;
		int total = 0;
		
		double avg;
		SortedMap<Integer, Integer> distribution = new TreeMap<Integer,Integer>();
		for (Integer cnt : results) {
			if( cnt < min ){
				min = cnt;
			}
			if( cnt > max ){
				max = cnt;
			}
			Integer tally = distribution.get( cnt );
			if( tally == null ){
				tally = 1;
			}else{
				tally +=1;
			}
			distribution.put( cnt, tally);
			total += cnt;
		}
		avg = total/results.size();
		System.out.println( "for " + numTrials + " trials, min=" + min + "; max=" +
				max + "; average=" + avg);
		System.out.println( "Distribution:\n");
		for( int j = min; j <= max; j++ ){
			System.out.format( "%4d: ", j );
			Integer numIters = distribution.get(j);
			if( numIters != null ){
				StringBuilder sb = new StringBuilder();
				double fractionalAmt = numIters/(double)numTrials;
				int numGlyphs = (int) (fractionalAmt*500);
				if( numGlyphs == 0 ){
					numGlyphs = 1;
				}
//				System.out.format( "%d itertions had count=%4d; fractional amount=%.6f; drawing %d glyphs", j, numIters, fractionalAmt, numGlyphs);
				for( int i = 0; i < numGlyphs; i++ ){
					sb.append( '=' );
				}
				System.out.print( sb.toString() );
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		if( args.length != 2 ){
			System.err.println( "Pass two args: number of roles and number to trials to run" );
			System.exit( -1 );
		}
		int numRoles = Integer.parseInt( args[0] );
		int numTrials = Integer.parseInt( args[1] );
		RandomCaster caster = new RandomCaster(numRoles, numTrials);
		List<Integer> results = caster.runTrials();
		caster.printResults( results );

	}

}
