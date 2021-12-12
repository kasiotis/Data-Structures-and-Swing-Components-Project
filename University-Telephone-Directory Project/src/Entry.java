
/**
 * class Entry
 *
 */
public class Entry {
	
	private String surname;
	private String initials;
	private String telephone;
	
	public Entry(String surname, String initials, String telephone) {
		
		this.surname = surname;
		this.initials = initials;
		this.telephone = telephone;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getInitials() {
		return initials;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Override
	public String toString() {
		
		String format = String.format("%-20s%-15s%-20s",this.surname,this.getInitials(),this.getTelephone());
		return format.toString();
	}
	
	@Override
	public int hashCode() {
		return (int)this.surname.toUpperCase().charAt(0) - 65;		
	}
	
}
