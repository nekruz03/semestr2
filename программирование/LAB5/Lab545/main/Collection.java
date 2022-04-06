package main;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import classes.Humanbeing;
import classes.Mood;
/**
 * This class for Collections
*/
public class Collection {

	public static class IdIsInUseException extends Exception {
		private final int id;

		public IdIsInUseException(int id) {
			this.id = id;
		}
		/*
		*This is me metod toString
		* */
		@Override
		public String toString() {
			return "id " + id + " is already in use";
		}
	}

	public static class WrongIdException extends Exception {
		private final int id;
		/*
		*@param id, this is int id
		**/
		public WrongIdException(int id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "id " + id + " is not found";
		}
	}

	private final LinkedList<Humanbeing> list;

	public Iterator<Humanbeing> getIterator() {
		return list.iterator();
	}

	public Collection() {
		list = new LinkedList<>();
	}

	private Humanbeing getById(int id) {
		for (Humanbeing hb : list) {
			if (hb.id == id) {
				return hb;
			}
		}
		return null;
	}
	/**
	 * method for command info
	 */
	public void info() {
		System.out.println("There are " + list.size() + " elements.");
	}
	/**
	 * method for command show
	 */
	public void show() {
		for (Humanbeing h : list) {
			System.out.println(h);
		}
	}

	public void add(Humanbeing hb) throws IdIsInUseException {
		if (getById(hb.id) != null) {
			throw new IdIsInUseException(hb.id);
		}
		list.add(hb);
	}

	public void update(Humanbeing hb) throws WrongIdException {
		Humanbeing old = getById(hb.id);
		if (old == null) {
			throw new WrongIdException(hb.id);
		}
		list.remove(old);
		list.add(hb);
	}

	public void remove_by_id(int id) throws WrongIdException {
		Humanbeing hb = getById(id);
		if(hb==null) {
			throw new WrongIdException(id);
		}
		list.remove(hb);
	}
	/**
	 * method for command clear
	 */
	public void clear() {
		list.removeAll(list);
	}
	/**
	 * method for command remove_first
	 */
	public void remove_first() {
		list.removeFirst();
	}
	/**
	 * method for command remove_greater
	 */
	public void remove_greater(long x) {
		LinkedList<Humanbeing> greater = new LinkedList<>();
		for (Humanbeing hb : list) {
			if (hb.coordinates.x > x) {
				greater.add(hb);
			}
		}
		list.removeAll(greater);
	}
	/**
	 * @return list of cordenats x
	 */
	public int count_less_than_mood(Mood mood) {
		int result = 0;
		for (Humanbeing hb : list) {
			if (hb.mood.compareTo(mood) < 0) {
				result++;
			}
		}
		return result;
	}

	public void filter_by_car(String car) {
		for (Humanbeing hb : list) {
			if (hb.car.name.equals(car)) {
				System.out.println(hb);
			}
		}
	}
	/**
	 * method for command print_field_ascending_car
	 */
	public void print_field_ascending_car() {
		HashSet<String> cars = new HashSet<>();
		for (Humanbeing hb : list) {
			cars.add(hb.car.name);
		}
		for (String car : cars) {
			System.out.println(car);
		}
	}

}
