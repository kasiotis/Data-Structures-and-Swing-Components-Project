
/**
 * class ArrayDirectory
 *
 */
public class ArrayDirectory implements Directory {
	
	private static final int DEFAULT_CAPACITY = 500;
	private int capacity;
	private int size;
	private Entry[] directory;
	
	public ArrayDirectory() {
		this.capacity = DEFAULT_CAPACITY;
		this.directory = new Entry[capacity];
	}
	
	public ArrayDirectory(int capacity) {
		this.capacity = capacity;
		this.directory = new Entry[capacity];
	}

	@Override
	public void insert(Entry entry) {
		
		if(size == 0) {
			directory[0] = entry;
			size++;
		}else if(size == capacity)
			System.out.println("The directory is full.");
		else {
			Entry last = null;
			for (int i = 0; i < size; i++) {
				if(i == 0)
					last = entry;
				
				if(directory[i].getSurname().compareToIgnoreCase(last.getSurname()) >= 0) {
					Entry temp = directory[i];
					directory[i] = last;
					last = temp;
				}
			}
			directory[size] = last;
			size++;
		}
	}

	@Override
	public void deleteByName(String name) {
		if(size == 0)
			System.out.println("The directory is empty.");
		else {
			boolean found = false;
			for (int i = 0; i < size; i++) {
				if(!found) {
					if(directory[i].getSurname().equalsIgnoreCase(name)) {
						found = true;
					}
				}
				
				if (found) {
					if(i == this.capacity-1)
						directory[i] = null;
					else
						directory[i] = directory[i+1];
				}				
				
			}
			
			if(!found)
				System.out.println("No entry found.");
			else 
				size--;
		}
		
	}

	@Override
	public String findNumberByName(String name) {
		
		String number = null;
		
		for (int i = 0; i < size; i++) {
			if(directory[i].getSurname().equalsIgnoreCase(name)) {
				number = directory[i].getTelephone();
				break;
			}
		}
		return number;
		
	}

	@Override
	public void changeTelephoneNumber(String personName, String newNumber) {
		int foundIndex = -1;
		for (int i = 0; i < size; i++) {
			if(directory[i].getSurname().equalsIgnoreCase(personName)) {
				foundIndex = i;
				break;
			}
		}

		if (foundIndex == -1) {
			System.out.println("No entry found.");
		} else {
			directory[foundIndex].setTelephone(newNumber);
		}
	}

	@Override
	public void printDirectory() {
		System.out.println("-----------------------------------------");
		System.out.printf("%-20s%-15s%-20s\n","Name","Initials","Number");
		System.out.println("-----------------------------------------");
		for (int i = 0; i < size; i++) {
			System.out.println(directory[i]);
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public Entry[] getDirectory() {
		return directory;
	}

}
