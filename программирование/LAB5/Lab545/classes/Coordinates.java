package classes;
/**This class for Coordinates
 */
public class Coordinates {
	private static long MIN_X = -710;
	private static int MAX_Y = 407;
	/**
	*MIN_X = -710
	*  MAX_Y = 407
	*/
	public static class XException extends Exception {
		@Override
		public String toString() {
			return "x must be greater than " + MIN_X;
		}
	}
	/**
	 *  Exseption x must be greater than @param MIN_X
	 */

	public static class YException extends Exception {
		@Override
		public String toString() {
			return "y is greater than " + MAX_Y;
		}
	}

	public static class NullYException extends Exception {
		@Override
		public String toString() {
			return "y cannot be null";
		}
	}
	
	public static Integer getY(String y) {
		if(y==null) {
			return null;
		}
		if(y.length()==0) {
			return null;
		}
		return Integer.parseInt(y);
	}

	public final long x;
	public final Integer y;
	/*
	*This is setter fo Cordinats
	* @param x some int value
	* @param y some Integer value
	* @throws XException, YException, NullYException if somethings goes wring this exceptios will be trow
	* */
	public Coordinates(long x, Integer y) throws XException, YException, NullYException {
		if (x <= MIN_X) {
			throw new XException();
		}
		if (y == null) {
			throw new NullYException();
		}
		if (y > MAX_Y) {
			throw new YException();
		}
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
