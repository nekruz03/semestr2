package classes;

import java.time.LocalDate;
/**This class for Humanbeings
 */
public class Humanbeing {

	public static class IdException extends Exception {
		@Override
		public String toString() {
			return "id must be positive";
		}
	}

	public static class NullIdException extends Exception {
		@Override
		public String toString() {
			return "id must be setted";
		}
	}

	public static class NameException extends Exception {
		@Override
		public String toString() {
			return "Empty name";
		}
	}

	public static class WeaponTypeException extends Exception {
		@Override
		public String toString() {
			return "Empty weapon type";
		}
	}

	public final int id;
	public final String name;
	public final Coordinates coordinates;
	public final LocalDate creationDate;
	public final boolean realHero;
	public final Boolean hasToothPick;
	public final long impactSpeed;
	public final WeaponType weaponType;
	public final Mood mood;
	public final Car car;

	public Humanbeing(Integer id, String name, Coordinates coordinates, boolean realHero, Boolean hasToothPick,
			long impactSpeed, WeaponType weaponType, Mood mood, Car car)
			throws IdException, NameException, WeaponTypeException, NullIdException {
		if (id == null) {
			throw new NullIdException();
		}
		if (id < 0) {
			throw new IdException();
		}
		this.id = id;
		if (name == null) {
			throw new NameException();
		}
		this.name = name;
		this.coordinates = coordinates;
		creationDate = LocalDate.now();
		this.realHero = realHero;
		this.hasToothPick = hasToothPick;
		this.impactSpeed = impactSpeed;
		if (weaponType == null) {
			throw new WeaponTypeException();
		}
		this.weaponType = weaponType;
		this.mood = mood;
		this.car = car;
	}

	public Humanbeing(int id, String name, Coordinates coordinates, LocalDate creationDate, boolean realHero,
			boolean hasToothPick, long impactSpeed, WeaponType weaponType, Mood mood, Car car) {
		this.id = id;
		this.name = name;
		this.coordinates = coordinates;
		this.creationDate = creationDate;
		this.realHero = realHero;
		this.hasToothPick = hasToothPick;
		this.impactSpeed = impactSpeed;
		this.weaponType = weaponType;
		this.mood = mood;
		this.car = car;
	}

	public String toString() {
		return name + ". ﷿﷿﷿﷿ ﷿﷿﷿﷿﷿﷿﷿﷿ - " + creationDate + ", ﷿﷿﷿﷿﷿﷿﷿﷿﷿﷿ - " + coordinates;
	}

}
