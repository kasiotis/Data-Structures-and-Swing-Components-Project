import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * class Main for testing part1
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Testing ArrayDirectory....\n");
		ArrayDirectory ad = new ArrayDirectory(); 
		readFileTo(ad,"contacts.txt");
		ad.printDirectory(); // print directory
		ad.deleteByName("gayan");
		System.out.println("\ndeleted gayan\n");
		ad.printDirectory();
		System.out.println("\nnumber of jess - " + ad.findNumberByName("jess"));
		ad.changeTelephoneNumber("adikari", "08950");
		System.out.println("changed the phone number of adikari to 08950\n");
		ad.printDirectory();
		
		System.out.println("\nTesting ListDirectory....\n");
		ListDirectory ld = new ListDirectory(); 
		readFileTo(ld,"contacts.txt");
		ld.printDirectory();
		ld.deleteByName("gayan");
		System.out.println("\ndeleted gayan\n");
		ld.printDirectory();
		System.out.println("\nnumber of jess - " + ld.findNumberByName("jess"));
		ld.changeTelephoneNumber("adikari", "08950");
		System.out.println("changed the phone number of adikari to 08950\n");
		ld.printDirectory();
		
		System.out.println("\nTesting HashDirectory....\n");
		HashDirectory hd = new HashDirectory(); 
		readFileTo(hd,"contacts.txt");
		hd.printDirectory();
		hd.deleteByName("gayan");
		System.out.println("\ndeleted gayan\n");
		hd.printDirectory();
		System.out.println("\nnumber of jess - " + hd.findNumberByName("jess"));
		hd.changeTelephoneNumber("adikari", "08950");
		System.out.println("changed the phone number of adikari to 08950\n");
		hd.printDirectory();
		
		System.out.println("-------------------");
		System.out.println("PERFORMANCE TESTING");
		System.out.println("-------------------\n");
		
		long totalTime = 0;
		StopWatch watch = new StopWatch();
		
		System.out.println("--ArrayDirectory--\n");
		// average case
		watch.start();
		ad.insert(new Entry("niki", "L.C.P", "02693"));
		watch.stop();
		System.out.println("Average case time for insertion = " + (double)watch.elapsedTime()+" ns");
		for (int i = 0; i < 1000; i++) {
			watch.start();
			ad.findNumberByName("kasun");
			watch.stop();
			totalTime += watch.elapsedTime();
		}
		System.out.println("Average case time for search = " + ((double)totalTime/1000d)+" ns");
		
		
		System.out.println("\n--ListDirectory--\n");
		// average case
		watch.start();
		ld.insert(new Entry("niki", "L.C.P", "02693"));
		watch.stop();
		System.out.println("Average case time for insertion = " + (double)watch.elapsedTime()+" ns");
		totalTime = 0;
		for (int i = 0; i < 1000; i++) {
			watch.start();
			ld.findNumberByName("kasun");
			watch.stop();
			totalTime += watch.elapsedTime();
		}
		System.out.println("Average case time for search = " + ((double)totalTime/1000d)+" ns");
		// best case
		System.out.println();// new line
		watch.start();
		ld.insert(new Entry("aberathna", "M.K", "02872"));
		watch.stop();
		System.out.println("Best case time for insertion = " + (double)watch.elapsedTime()+" ns");
		totalTime = 0;
		for (int i = 0; i < 1000; i++) {
			watch.start();
			ld.findNumberByName("aberathna");
			watch.stop();
			totalTime += watch.elapsedTime();
		}
		System.out.println("Best case time for search = " + ((double)totalTime/1000d)+" ns");
		// worst case
		System.out.println();// new line
		watch.start();
		ld.insert(new Entry("zabran", "A.B.M", "01256"));
		watch.stop();
		System.out.println("Worst case time for insertion = " + (double)watch.elapsedTime()+" ns");
		totalTime = 0;
		for (int i = 0; i < 1000; i++) {
			watch.start();
			ld.findNumberByName("zabran");
			watch.stop();
			totalTime += watch.elapsedTime();
		}
		System.out.println("Worst case time for search = " + ((double)totalTime/1000d)+" ns");
		
		
		System.out.println("\n--HashDirectory--\n");
		// average case
		watch.start();
		hd.insert(new Entry("niki", "L.C.P", "02693"));
		watch.stop();
		System.out.println("Average case time for insertion = " + (double)watch.elapsedTime()+" ns");
		totalTime = 0;
		for (int i = 0; i < 1000; i++) {
			watch.start();
			hd.findNumberByName("kasun");
			watch.stop();
			totalTime += watch.elapsedTime();
		}
		System.out.println("Average case time for search = " + ((double)totalTime/1000d)+" ns");
		// best case
		System.out.println();// new line
		watch.start();
		hd.insert(new Entry("aberathna", "M.K", "02872"));
		watch.stop();
		System.out.println("Best case time for insertion = " + (double)watch.elapsedTime()+" ns");
		totalTime = 0;
		for (int i = 0; i < 1000; i++) {
			watch.start();
			hd.findNumberByName("aberathna");
			watch.stop();
			totalTime += watch.elapsedTime();
		}
		System.out.println("Best case time for search = " + ((double)totalTime/1000d)+" ns");
		// worst case
		System.out.println();// new line
		watch.start();
		hd.insert(new Entry("zabran", "A.B.M", "01256"));
		watch.stop();
		System.out.println("Worst case time for insertion = " + (double)watch.elapsedTime()+" ns");
		totalTime = 0;
		for (int i = 0; i < 1000; i++) {
			watch.start();
			hd.findNumberByName("zabran");
			watch.stop();
			totalTime += watch.elapsedTime();
		}
		System.out.println("Worst case time for search = " + ((double)totalTime/1000d)+" ns");
		
	}
	
	public static void readFileTo(Directory directory,String filePath){
	        
        FileReader fileRd=null; 
        BufferedReader reader=null;
		
        try { 
            fileRd = new FileReader(filePath); 
            reader = new BufferedReader(fileRd); 
           
            String [] items;
            for(String line = reader.readLine(); line != null; line = reader.readLine()) { 
                // split the lines of file and store into string array
                items = line.split("\t");
                Entry entry = new Entry(items[0], items[1], items[2]);
                directory.insert(entry);
            }

            if(fileRd != null) fileRd.close();
            if(reader != null) reader.close();

        } catch (IOException e) { 
            System.out.println("Error : Input file not found");
            System.exit(0);

        } catch (NumberFormatException e){
            System.out.println(e);      
        }
	       
	}
	
}