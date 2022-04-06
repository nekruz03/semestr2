package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import java.util.Iterator;
import java.util.LinkedList;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import  classes.Car;
import classes.Coordinates;
import classes.Humanbeing;
import classes.Mood;
import classes.WeaponType;
/**
 * @author Nekruz
 * @version 1.1
 * @since 1.0
 *
 */

/**
 *This is My Main class, here I will write my code
 */
public class Main {

	private static String NAME = "humanbeings";

	private static String path = null;

	private static Node root;
	private static Document document;
	private static Scanner scanner = new Scanner(System.in);
	private static final LinkedList<String> history = new LinkedList<>();

	private static Collection collection = new Collection();
	/*
	*load method
	*/
	private static boolean load() {
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = documentBuilder.parse(path);
			root = document.getDocumentElement();
			NodeList humanbeings = root.getChildNodes();
			for (int i = 0; i < humanbeings.getLength(); i++) {
				Node humanbeing = humanbeings.item(i);
				if (humanbeing.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				NodeList children = humanbeing.getChildNodes();
				long x = -1;
				int y = -1;
				for (int j = 0; j < children.getLength(); j++) {
					Node child = children.item(j);
					if (child.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					NamedNodeMap coordinates = child.getAttributes();
					for (int k = 0; k < coordinates.getLength(); k++) {
						Node attribute = coordinates.item(k);
						switch (attribute.getNodeName()) {
						case "x":
							x = Long.parseLong(attribute.getNodeValue());
							break;
						case "y":
							y = Coordinates.getY(attribute.getNodeValue());
						}

					}
				}
				try {
					Coordinates coordinates = new Coordinates(x, y);
					NamedNodeMap attributes = humanbeing.getAttributes();
					int id = -1;
					String name = null;
					LocalDate creationDate = null;
					boolean realHero = false;
					boolean hasToothPick = false;
					long impactSpeed = -1;
					WeaponType weaponType = WeaponType.AXE;
					Mood mood = Mood.APATHY;
					String car = null;
					for (int j = 0; j < attributes.getLength(); j++) {
						Node attribute = attributes.item(j);
						switch (attribute.getNodeName()) {
						case "id":
							id = Integer.parseInt(attribute.getNodeValue());
							break;
						case "name":
							name = attribute.getNodeValue();
							break;
						case "creationDate":
							creationDate = LocalDate.parse(attribute.getNodeValue());
							break;
						case "realHero":
							realHero = Boolean.parseBoolean(attribute.getNodeValue());
							break;
						case "hasToothPick":
							hasToothPick = Boolean.parseBoolean(attribute.getNodeValue());
							break;
						case "impactSpeed":
							impactSpeed = Long.parseLong(attribute.getNodeValue());
							break;
						case "weaponType":
							weaponType = WeaponType.valueOf(attribute.getNodeValue().toUpperCase());
							break;
						case "mood":
							mood = Mood.valueOf(attribute.getNodeValue().toUpperCase());
							break;
						case "car":
							car = attribute.getNodeValue();

						}
					}

					try {
						collection.add(new Humanbeing(id, name, coordinates, creationDate, realHero, hasToothPick,
								impactSpeed, weaponType, mood, new Car(car)));
					} catch (Collection.IdIsInUseException e) {
						System.out.println(e);
						return false;
					}
				} catch (classes.Coordinates.XException e) {
					System.out.println(e);
					return false;
				} catch (classes.Coordinates.YException e) {
					System.out.println(e);
					return false;
				} catch (classes.Coordinates.NullYException e) {
					System.out.println(e);
					return false;
				}

			}
			return true;
		} catch (NumberFormatException e) {
			System.out.println(e);
			return false;
		} catch (DOMException e) {
			System.out.println(e);
			return false;
		} catch (ParserConfigurationException e) {
			System.out.println(e);
			return false;
		} catch (SAXException e) {
			System.out.println(e);
			return false;
		} catch (IOException e) {
			System.out.println(e);
			return false;
		}
	}

/*
*This is method for comman help
*/
	private static void help() {
		System.out.println("help - this text");
		System.out.println("info - main information about the collection");
		System.out.println("show - list of all elements in the collection");
		System.out.println("add - add a new element into the collection");

		System.out.println("update id - modify an element");
		System.out.println("remove_by_id id - delete an element");

		System.out.println("clear - wipe all from the collection");

		System.out.println("save - backup the collection into the file " + path);

		System.out.println("execute_script file - execute a list of commands");
		System.out.println("exit - quit");
		System.out.println("remove_first - delete the first element");
		System.out.println("remove_greater X - delete all the elements that are greater than the element X");
		System.out.println("history - history of commands");
		System.out.println("count_less_than_mood mood - count of elements that have 'bad mood'");
		System.out.println("filter_by_car car - list of elements with special car");
		System.out.println("print_field_ascending_car - list of used cars");

	}

	private static void print(Object text, boolean console) {
		if (console) {
			System.out.print(text);
		}
	}

	private static void println(Object text, boolean console) {
		if (console) {
			System.out.println(text);
		}
	}
	/*
	 *This method for addOrUpdate command
	 */
	private static void addOrUpdate(Scanner scanner, boolean console, Integer id) {
		boolean update = (id != null);
		if (!update) {
			print("Input id: ", console);
			id = scanner.nextInt();
		}
		print("Input name: ", console);
		String name = scanner.next();
		println("Input coordinates:", console);
		print("x=", console);
		long x = scanner.nextLong();
		print("y=", console);
		Integer y = Coordinates.getY(scanner.next());
		try {
			Coordinates coordinates = new Coordinates(x, y);
			print("Input whether the humanbeing is a real hero or not (true, false): ", console);
			boolean realHero = scanner.nextBoolean();
			print("Input whether the humanbeing has a tooth pick or not (true, false): ", console);
			boolean hasToothPick = scanner.nextBoolean();
			print("Input impact speed: ", console);
			long impactSpeed = scanner.nextLong();
			print("Input weapon type (", console);
			boolean first = true;
			for (WeaponType weaponType : WeaponType.values()) {
				if (first) {
					first = false;
				} else {
					print(", ", console);
				}
				print(weaponType, console);
			}
			println("):", console);
			WeaponType weaponType = WeaponType.valueOf(scanner.next());
			print("Input mood (", console);
			first = true;
			for (Mood mood : Mood.values()) {
				if (first) {
					first = false;
				} else {
					print(", ", console);
				}
				print(mood, console);
			}
			println("): ", console);
			Mood mood = Mood.valueOf(scanner.next());
			print("Input car: ", console);
			String car = scanner.next();
			Humanbeing hb = new Humanbeing(id, name, coordinates, LocalDate.now(), realHero, hasToothPick, impactSpeed,
					weaponType, mood, new Car(car));
			if (update) {
				try {
					collection.update(hb);
				} catch (Collection.WrongIdException e) {
					System.out.println(e);
				}
			} else {
				try {
					collection.add(hb);
				} catch (Collection.IdIsInUseException e) {
					System.out.println(e);
				}
			}
		} catch (classes.Coordinates.XException e) {
			System.out.println(e);
		} catch (classes.Coordinates.YException e) {
			System.out.println(e);
		} catch (classes.Coordinates.NullYException e) {
			System.out.println(e);
		}
	}
	/*
	 *This method for save command
	 */
	private static void save() {
		NodeList children;
		do {
			children = root.getChildNodes();
			root.removeChild(children.item(0));
		} while (children.getLength() > 0);
		for (Iterator<Humanbeing> i = collection.getIterator(); i.hasNext();) {
			Humanbeing h = i.next();
			Element element = document.createElement("humanbeing");
			Element coordinates = document.createElement("coordinates");
			element.appendChild(coordinates);
			coordinates.setAttribute("x", h.coordinates.x + "");
			coordinates.setAttribute("y", h.coordinates.y + "");
			element.setAttribute("id", h.id + "");
			element.setAttribute("name", h.name);
			element.setAttribute("creationDate", h.creationDate.toString());
			element.setAttribute("realHero", h.realHero + "");
			element.setAttribute("hasToothPick", h.hasToothPick + "");
			element.setAttribute("impactSpeed", h.impactSpeed + "");
			element.setAttribute("weaponType", h.weaponType.toString());
			element.setAttribute("mood", h.mood.toString());
			element.setAttribute("car", h.car.toString());
			root.appendChild(element);

		}
		try {
			Transformer tr = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource(document);
			FileOutputStream fos = new FileOutputStream(path);
			StreamResult result = new StreamResult(fos);
			tr.transform(source, result);
		} catch (TransformerException | IOException e) {
			e.printStackTrace(System.out);
		}
	}

	private static void history() {
		for (String command : history) {
			System.out.println(command);
		}
	}
	/*
	 *This method for execute command
	 */
	private static void execute(Scanner scanner, boolean console) {
		while (true) {
			String command;
			if (console) {
				command = scanner.nextLine();
			} else {
				try {
					command = scanner.nextLine();
				} catch (NoSuchElementException e) {
					return;
				}
			}
			if (console) {
				history.add(command);
			}
			int index = command.indexOf(" ");
			String commandverb;
			String commandobject;
			if (index == -1) {
				commandverb = command;
				commandobject = null;
			} else {
				commandverb = command.substring(0, index);
				commandobject = command.substring(index + 1);
			}
			switch (commandverb) {
			case "help":
				help();
				break;
			case "info":
				collection.info();
				break;

			case "show":
				collection.show();
				break;
			case "add":
				addOrUpdate(scanner, console, null);
				break;
			case "update":
				if (commandobject == null) {
					System.out.println("Parameter is missing");
					break;
				}
				addOrUpdate(scanner, console, Integer.parseInt(commandobject));
				break;
			case "remove_by_id":
				if (commandobject == null) {
					System.out.println("Parameter is missing");
					break;
				}
				int id = Integer.parseInt(commandobject);
				try {
					collection.remove_by_id(id);
				} catch (Collection.WrongIdException e1) {
					System.out.println(e1);
				}
				break;

			case "clear":
				collection.clear();
				break;
			case "save":
				save();
				break;
			case "execute_script":
				if (commandobject == null) {
					System.out.println("Parameter is missing");
					break;
				}
				try {
					execute(new Scanner(new FileReader(commandobject)), false);
				} catch (FileNotFoundException e) {
					System.out.println("The file " + commandobject + " is not found");
				}
				break;
			case "exit":
				System.out.println("Good bye!");
				System.exit(0);
				break;
			case "remove_first":
				collection.remove_first();
				break;
			case "remove_greater":
				if (commandobject == null) {
					System.out.println("Parameter is missing");
					break;
				}
				long x = Long.parseLong(commandobject);
				collection.remove_greater(x);
				break;
			case "history":
				history();
				break;
			case "count_less_than_mood":
				if (commandobject == null) {
					System.out.println("Parameter is missing");
					break;
				}
				Mood mood = Mood.valueOf(commandobject);
				System.out.println(collection.count_less_than_mood(mood) + " elements");
				break;
			case "filter_by_car":
				if (commandobject == null) {
					System.out.println("Parameter is missing");
					break;
				}
				collection.filter_by_car(commandobject);
				break;

			case "print_field_ascending_car":
				collection.print_field_ascending_car();
				break;

			}
			if (console) {
				System.out.print("> ");
			}
		}
	}

	/**
	 *Here start point of the programm
	 * @param args comand line values
	 */
	public static void main(String[] args) {
		Map<String, String> env = System.getenv();
		for (String name : env.keySet()) {

			if (name.equals(NAME)) {
				path = env.get(name);
			}
		}
		if (path == null) {
			System.out.println("The enviroment variable '" + NAME + "' isn't setted.");
			System.exit(1);

		}

		if (load()) {
			System.out.println("******The file " + path + " is loaded successfully.*********");
			help();
			System.out.print("> ");
			execute(scanner, true);
		} else {
			System.out.println("******The file " + path + " is invalid.*********");
		}

	}

}
