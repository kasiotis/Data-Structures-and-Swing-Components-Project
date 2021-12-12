import java.util.List;
import java.util.LinkedList;

/**
 * class ListDirectory
 *
 */
public class ListDirectory implements Directory {
	
	private List<Entry> directory;

	public ListDirectory() {
		 this.directory = new LinkedList<>();
	}
	
	@Override
	public void insert(Entry entry) {
		if(size() == 0)
			directory.add(entry);
		else {
			int size = size();
			for (int i = 0; i < size; i++) {
				if(directory.get(i).getSurname().compareToIgnoreCase(entry.getSurname()) >= 0) {
					directory.add(i, entry);
					break;
				}
				if(i==size-1)
					directory.add(entry);
			}
		}
	}

	@Override
	public void deleteByName(String name) {
		if(size() == 0)
			System.out.println("The directory is empty.");
		else {
			boolean found = false;
			int size = size();
			for (int i = 0; i < size; i++) {
				
				if(directory.get(i).getSurname().equalsIgnoreCase(name)) {
					found = true;
					directory.remove(i);
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
		
		for (int i = 0; i < size(); i++) {
			if(directory.get(i).getSurname().equalsIgnoreCase(name)) {
				number = directory.get(i).getTelephone();
				break;
			}
		}
		return number;
	}

	@Override
	public void changeTelephoneNumber(String personName, String newNumber) {
		
		for (int i = 0; i < size(); i++) {
			if(directory.get(i).getSurname().equalsIgnoreCase(personName)) {
				directory.get(i).setTelephone(newNumber);
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
		for (Entry entry : directory) {
			System.out.println(entry);
		}
	}
	
	public List<Entry> getDirectory() {
		return directory;
	}
	
	private int size() {
		return this.directory.size();
	}
	
}
