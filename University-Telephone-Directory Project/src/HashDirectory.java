import java.util.*;

/**
 * class HashDirectory
 */
public class HashDirectory implements Directory{

	private List<List<Entry>> directory;
	private static int ALPHABET_SIZE = 26;
	
	public HashDirectory() {
		this.directory = new ArrayList<>();
		// initialize 26 empty linked lists
		for (int i = 0; i < ALPHABET_SIZE; i++) {
			directory.add(new LinkedList<Entry>());
		}
	}
	
	@Override
	public void insert(Entry entry) {
		int hash = entry.hashCode();
		if(directory.get(hash).isEmpty())
			directory.get(hash).add(entry);
		else {
			int size = directory.get(hash).size();
			for (int i = 0; i < size; i++) {
				if(directory.get(hash).get(i).getSurname().compareToIgnoreCase(entry.getSurname()) >= 0) {
					directory.get(hash).add(i, entry);
					break;
				}
				if(i==size-1)
					directory.get(hash).add(entry);
			}
		}
	}

	@Override
	public void deleteByName(String name) {
		int hash = hashCode(name);
		if(directory.get(hash).isEmpty())
			System.out.println("The directory is empty.");
		else {
			boolean found = false;
			int size = directory.get(hash).size();
			for (int i = 0; i < size; i++) {
				
				if(directory.get(hash).get(i).getSurname().equalsIgnoreCase(name)) {
					found = true;
					directory.get(hash).remove(i);
					break;
				}
				
			}
			
			if(!found)
				System.out.println("No entry found.");
			
		}
	}

	@Override
	public String findNumberByName(String name) {
		String number = null;
		int hash = hashCode(name);
		if(directory.get(hash).isEmpty()) {
			System.out.println("The directory is empty.");
			return number;
		}
		
		int size = directory.get(hash).size();
		for (int i = 0; i < size; i++) {
			
			if(directory.get(hash).get(i).getSurname().equalsIgnoreCase(name)) {
				number = directory.get(hash).get(i).getTelephone();
				break;
			}
			
		}
		
		if(number == null)
			System.out.println("No entry found.");
		return number;
	}

	@Override
	public void changeTelephoneNumber(String personName, String newNumber) {
		
		int hash = hashCode(personName);
		if(directory.get(hash).isEmpty()) {
			System.out.println("The directory is empty.");
			return;
		}
		
		for (int i = 0; i < size(); i++) {
			if(directory.get(hash).get(i).getSurname().equalsIgnoreCase(personName)) {
				directory.get(hash).get(i).setTelephone(newNumber);
				return;
			}
		}
		System.out.println("No entry found.");
	}

	@Override
	public void printDirectory() {
		System.out.println("-----------------------------------------");
		System.out.printf("%-20s%-15s%-20s\n","Name","Initials","Number");
		System.out.println("-----------------------------------------");
		for (List<Entry> linkedList : directory) {
			if(!linkedList.isEmpty()) {
				for (Entry entry : linkedList) {
					System.out.println(entry);
				}
			}
		}
	}
	
	public int size() {
		int count = 0;
		for (List<Entry> linkedList : directory) {
			if(linkedList != null) {
				count += linkedList.size();
			}
		}
		return count;
	}
	
	public int hashCode(String surname) {
		return (int)surname.toUpperCase().charAt(0) - 65;		
	}
	

}
