
/**
 * Interface Directory
 *
 */
public interface Directory {

	// The ability to insert new entries into the directory
	public void insert(Entry entry);
	
	// Delete entries from the directory by name
	public void deleteByName(String name);
	
	// find telephone number from the directory by name
	public String findNumberByName(String name);
	
	// Change a person’s telephone number by name of the person
	public void changeTelephoneNumber(String personName, String newNumber);
	
	// print the directory
	public void printDirectory();
	
}
