import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Point;

import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class DirectoryGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField nameTxt;
	private JTextField initialsTxt;
	private JTextField telephoneTxt;
	private JTextField searchTermTxt;
	
	private JButton btnSearchByName;
	private JButton btnEditTelephone;
	private JButton btnDelete;
	private JButton btnAddEntry;
	
	private DefaultTableModel dtm;
	
	private ListDirectory ld = new ListDirectory();
	
	private boolean updateMode = false;
	private JLabel lblNotifications;

	/**
	 * Create the frame.
	 */
	public DirectoryGUI() {
		readFileTo(ld, "contacts.txt");
		setResizable(false);
		setTitle("University Telephone Directory");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblNewLabel = new JLabel("Surename\r\n");
		
		nameTxt = new JTextField();
		nameTxt.setColumns(10);
		
		JLabel lblInitials = new JLabel("Initials");
		
		initialsTxt = new JTextField();
		initialsTxt.setColumns(10);
		
		JLabel lblTelephone = new JLabel("Telephone");
		
		telephoneTxt = new JTextField();
		telephoneTxt.setColumns(10);
		
		btnAddEntry = new JButton("Add Contacts");
		btnAddEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = nameTxt.getText().trim();
		        String initial = initialsTxt.getText().trim();
		        String tele = telephoneTxt.getText().trim();
		        
		        if(name.isEmpty() || initial.isEmpty() || tele.isEmpty() ){
		            JOptionPane.showMessageDialog(null, "Complete all the informations !", "Message", JOptionPane.INFORMATION_MESSAGE);
		        }else if(!isValidTelephoneNumber(tele)){
		        	JOptionPane.showMessageDialog(null, "Invalid telephone number !", "Message", JOptionPane.INFORMATION_MESSAGE);
		        }else{
		        	if(updateMode) {
		        		ld.changeTelephoneNumber(name, tele);
		        		configToUpdateMode(false);		        		
		        	}else {
		        		ld.insert(new Entry(name, initial, tele));
		        	}
		        	clearAddForm();
		        	updateTable();
		        	JOptionPane.showMessageDialog(null, "Data saved !", "Message", JOptionPane.INFORMATION_MESSAGE);
		        }
			}
		});
		
		searchTermTxt = new JTextField();
		searchTermTxt.setColumns(10);
		
		btnSearchByName = new JButton("Search By Name");
		btnSearchByName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = searchTermTxt.getText().trim();
				if(!name.isEmpty()) {
					String num = ld.findNumberByName(name);
					if(num==null) {
						updateTable();
						lblNotifications.setText("No entry found.");
					}else {
						int rows = dtm.getRowCount();
						for (int i = 0; i < rows; i++) {
							if(Integer.parseInt(dtm.getValueAt(i, 2).toString())==Integer.parseInt(num)){
								table.setRowSelectionInterval(i, i);
								scrollPane.getViewport().setViewPosition(new Point(0,table.getRowHeight()*i));
								break;
							}
						}
					}
				}else {
					updateTable();
				}
				
			}
		});
		
		JLabel lblUniversityTelephoneDirectory = new JLabel("University Telephone Directory\r\n");
		lblUniversityTelephoneDirectory.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUniversityTelephoneDirectory.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnEditTelephone = new JButton("Edit Telephone\r\n");
		btnEditTelephone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowCount = table.getRowCount();
		        int selectedRowCount = table.getSelectedRowCount();
		        
		        if(rowCount==0){
		            JOptionPane.showMessageDialog(null, "Nothing to edit !", "Message", JOptionPane.INFORMATION_MESSAGE);
		            
		        }else if(selectedRowCount==0){
		           JOptionPane.showMessageDialog(null, "select one entry to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
		     
		        }else{
		        	int row=table.getSelectedRow();
		            String name=dtm.getValueAt(row, 0).toString();
		            String initial=dtm.getValueAt(row, 1).toString();
		            String tele=dtm.getValueAt(row, 2).toString();
		            
		            nameTxt.setText(name);
		    		initialsTxt.setText(initial);
		    		telephoneTxt.setText(tele);
		    		
		    		configToUpdateMode(true);
		        	
		        }
			}
		});
		
		btnDelete = new JButton("Delete\r\n");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowCount = table.getRowCount();
		        int selectedRowCount = table.getSelectedRowCount();
		        
		        if(rowCount==0){
		            JOptionPane.showMessageDialog(null, "Nothing to delete !", "Message", JOptionPane.INFORMATION_MESSAGE);
		            
		        }else if(selectedRowCount==0){
		           JOptionPane.showMessageDialog(null, "select one entry to delete.", "Message", JOptionPane.INFORMATION_MESSAGE);
		     
		        }else{
		        	int row=table.getSelectedRow();
		            String name=dtm.getValueAt(row, 0).toString();
		        	ld.deleteByName(name);
		        	updateTable();
		        	JOptionPane.showMessageDialog(null, ("Deleted the contacts of "+name+"."), "Message", JOptionPane.INFORMATION_MESSAGE);
		        }
			}
		});
		
		lblNotifications = new JLabel("notifications");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblUniversityTelephoneDirectory, GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblNotifications, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(searchTermTxt, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnSearchByName, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblTelephone)
										.addComponent(lblInitials)
										.addComponent(lblNewLabel))
									.addGap(4)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(10)
											.addComponent(btnAddEntry, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
										.addComponent(nameTxt, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
										.addComponent(initialsTxt, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
										.addComponent(telephoneTxt, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)))
								.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEditTelephone, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
							.addGap(6)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblUniversityTelephoneDirectory)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSearchByName)
						.addComponent(searchTermTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNotifications))
					.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnDelete)
							.addGap(7)
							.addComponent(btnEditTelephone)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(nameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))
							.addGap(14)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(initialsTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblInitials))
							.addGap(14)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(telephoneTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTelephone))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAddEntry))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE))
					.addGap(9))
		);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Surename", "Initials", "Telephone"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		panel.setLayout(gl_panel);
		this.dtm = (DefaultTableModel) table.getModel();
		updateTable();
	}
	
	private boolean isValidTelephoneNumber(String num) {
		if(num.length()==5)
			return true;
		else if(num.length()==6)
		{
			if(num.charAt(0)=='0') {
				return true;
			}
		}
		return false;
	}
	
	private void configToUpdateMode(boolean update) {
		this.updateMode = update;
		if(updateMode) {
			nameTxt.setEnabled(false);
			initialsTxt.setEnabled(false);
			btnAddEntry.setText("Update");
		}else {
			nameTxt.setEnabled(true);
			initialsTxt.setEnabled(true);
			btnAddEntry.setText("Add Entry");
		}
	}
	
	public void readFileTo(Directory directory,String filePath){
        
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
	
	private void updateTable() {
		cleanTable();
		for (Entry entry : ld.getDirectory()) {
			addEntryToTable(entry.getSurname(), entry.getInitials(), entry.getTelephone());
		}
		lblNotifications.setText(ld.getDirectory().size()+" result found");
	}
	
	private void cleanTable(){
	    dtm.getDataVector().removeAllElements();
	    dtm.fireTableDataChanged();
	}
	
	private void addEntryToTable(String name,String initials,String telephone){ 
        dtm.addRow(new Object[]{name, initials, telephone});
    }
	
	private void clearAddForm() {
		nameTxt.setText("");
		initialsTxt.setText("");
		telephoneTxt.setText("");
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// set the look and feel of the GUI
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DirectoryGUI frame = new DirectoryGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
