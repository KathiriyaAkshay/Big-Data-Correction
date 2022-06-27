import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JProgressBar;

public class main_app {

	private JFrame frmDataCleaningBy;
	private JTextField d_name;
	private JTextField a_name;
	 ButtonGroup G1;
	 private JTextField t_name;
	 private JTable table_1;
	 DefaultTableModel model;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main_app window = new main_app();
					window.frmDataCleaningBy.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public main_app() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDataCleaningBy = new JFrame();
		frmDataCleaningBy.setTitle("Data Cleaning By Filling Missing Values");
		frmDataCleaningBy.getContentPane().setForeground(new Color(244, 164, 96));
		frmDataCleaningBy.getContentPane().setBackground(new Color(63, 81, 181));
		frmDataCleaningBy.setBackground(new Color(63, 81, 181));;
		frmDataCleaningBy.setBounds(100, 100, 900, 600);
		frmDataCleaningBy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDataCleaningBy.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Data Cleaning By Filling Missing Values");
		lblNewLabel.setForeground(new Color(244, 164, 96));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNewLabel.setBounds(219, 10, 592, 47);
		frmDataCleaningBy.getContentPane().add(lblNewLabel);
		
		d_name = new JTextField();
		d_name.setFont(new Font("Tahoma", Font.BOLD, 18));
		d_name.setForeground(new Color(244, 164, 96));
		d_name.setBackground(new Color(92, 107, 192));
		d_name.setBounds(267, 111, 160, 34);
		frmDataCleaningBy.getContentPane().add(d_name);
		d_name.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Database Name:");
		lblNewLabel_1.setForeground(new Color(244, 164, 96));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(42, 114, 160, 28);
		frmDataCleaningBy.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Attribute Name:");
		lblNewLabel_2.setForeground(new Color(244, 164, 96));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2.setBounds(42, 239, 153, 25);
		frmDataCleaningBy.getContentPane().add(lblNewLabel_2);
		
		a_name = new JTextField();
		a_name.setFont(new Font("Tahoma", Font.BOLD, 18));
		a_name.setForeground(new Color(244, 164, 96));
		a_name.setBackground(new Color(92, 107, 192));
		a_name.setBounds(267, 236, 160, 34);
		frmDataCleaningBy.getContentPane().add(a_name);
		a_name.setColumns(10);
		
		JRadioButton mean_rb = new JRadioButton("Mean");
		mean_rb.setBackground(new Color(244, 164, 96));
		mean_rb.setFont(new Font("Tahoma", Font.BOLD, 18));
		mean_rb.setToolTipText("replaces null value with mean of the selected attribute");
		mean_rb.setBounds(267, 324, 93, 23);
		frmDataCleaningBy.getContentPane().add(mean_rb);
		
		JRadioButton median_rb = new JRadioButton("Median");
		median_rb.setBackground(new Color(244, 164, 96));
		median_rb.setFont(new Font("Tahoma", Font.BOLD, 18));
		median_rb.setToolTipText("replaces null value with median of the selected attribute");
		median_rb.setBounds(362, 324, 93, 23);
		frmDataCleaningBy.getContentPane().add(median_rb);
		
		JRadioButton mode_rb = new JRadioButton("Mode");
		mode_rb.setBackground(new Color(244, 164, 96));
		mode_rb.setFont(new Font("Tahoma", Font.BOLD, 18));
		mode_rb.setToolTipText("replaces null value with mode of the selected attribute");
		mode_rb.setBounds(461, 324, 109, 23);
		frmDataCleaningBy.getContentPane().add(mode_rb);
		
		 G1 = new ButtonGroup();
		 G1.add(mean_rb);
	        G1.add(median_rb);
	        G1.add(mode_rb);
	        
	        JButton btnNewButton = new JButton("Submit");
	        btnNewButton.setBackground(new Color(138, 43, 226));
	        btnNewButton.setForeground(new Color(244, 164, 96));
	        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
	        btnNewButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		String dbname = d_name.getText().toString();
					String aname = a_name.getText().toString();
					String tname = t_name.getText().toString();

					try {
					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
					Connection con = DriverManager.getConnection(
							"jdbc:ucanaccess://C://Users//User//Documents//" + dbname + ".accdb");
//					Class.forName("com.mysql.jdbc.Driver");
//			        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ dbname + "","root","");
					Statement stmt = con.createStatement();
					String query_1 = "SELECT "+aname+ " FROM " + tname;
					
					ResultSet rs = stmt.executeQuery(query_1);
					Vector<Integer> vec = new Vector<Integer>();  
					int finval=0;
			        int sum=0;
			        stmt.execute(query_1);
					while(rs.next()) {
						vec.add(rs.getInt(1));
//						System.out.println(rs.getInt(1));
		        	}
//					System.out.println("1");
		        	
					int count=0;

			        if(mean_rb.isSelected()) {

						System.out.println("1");
						for(int i=0;i<vec.size();i++) {
							sum=sum+vec.elementAt(i);
							if(vec.elementAt(i)==0) {
								count++;
							}
						}

//						System.out.println("2");
					int size=vec.size()-count;
					finval=sum/size;
				 	Collections.replaceAll(vec,0,finval);
			        

//					System.out.println("3");
			        for(int i=0;i<vec.size();i++) {
							System.out.println(vec.elementAt(i));
		        	}

			        for(int i=0;i<vec.size();i++) {
			        String query = "UPDATE " + tname + " SET " + aname + "= '" + vec.elementAt(i) + "' where ID='" + (i+1)+"' ;";
			        
					 model = (DefaultTableModel)table_1.getModel();
							            
					 model.addRow(new Object[]
			                    {
			                    	vec.elementAt(i)
			                    });
			        stmt.execute(query);
			        }
					}
					
	        		

			    
			    	
			        if(median_rb.isSelected()) {
//						System.out.println("2");
			        	Vector<Integer> temp = new Vector<Integer>();  
						
			        	for(int i=0;i<vec.size();i++) {
			        		if(vec.elementAt(i)!=0) {
								temp.add(vec.elementAt(i));
							}
			        	}
			        		Collections.sort(temp);
							if(temp.size()%2==0) {
								finval=((temp.elementAt(temp.size()/2))+(temp.elementAt((temp.size()/2)-1)))/2;
							}
							else {
								finval=(temp.elementAt(temp.size()/2));	
							}

			       
					Collections.replaceAll(vec,0,finval);
					for(int i=0;i<vec.size();i++) {
						System.out.println(vec.elementAt(i));
	        	}
					
					for(int i=0;i<vec.size();i++) {
			        String query = "UPDATE " + tname + " SET " + aname + "= '" + vec.elementAt(i) + "' where ID='" + (i+1)+"' ;";
			        model = (DefaultTableModel)table_1.getModel();
		            
					 model.addRow(new Object[]
			                    {
			                    	vec.elementAt(i)
			                    });
			        stmt.execute(query);
			        }
			        }
			        
			        
			        if(mode_rb.isSelected()) {
//						System.out.println("2");
			        	Vector<Integer> temp = new Vector<Integer>();  
						
			        	for(int i=0;i<vec.size();i++) {
			        		if(vec.elementAt(i)!=0) {
								temp.add(vec.elementAt(i));
							}
			        	}
			        		Collections.sort(temp);
			        		int freq=temp.elementAt(0);
			        		int t=1,max=1;
			        		for(int i=1;i<temp.size();i++) {
			        			if(temp.elementAt(i)==temp.elementAt(i-1)) {
			        				t++;
			        			}
			        			else {
			        				if(t>max) {
			        					freq=temp.elementAt(i-1);
			        					max=t;
			        					t=1;
			        				}
			        			}
			        		}
			        		if(t>max) {
	        					freq=temp.elementAt(temp.size()-1);
	        					max=t;
	        					t=1;
	        				}
			        		finval=freq;

			       	Collections.replaceAll(vec,0,finval);
					for(int i=0;i<vec.size();i++) {
						System.out.println(vec.elementAt(i));
	        	}
					
					for(int i=0;i<vec.size();i++) {
			        String query = "UPDATE " + tname + " SET " + aname + "= '" + vec.elementAt(i) + "' where ID='" + (i+1)+"' ;";
			        model = (DefaultTableModel)table_1.getModel();
		            
					 model.addRow(new Object[]
			                    {
			                    	vec.elementAt(i)
			                    });
			        stmt.execute(query);
			        }
			        }			        
			        
			        
			        
					}
			        catch(Exception e1) {
			        	
			        }
	        	}
	        });
	        btnNewButton.setBounds(131, 395, 109, 41);
	        frmDataCleaningBy.getContentPane().add(btnNewButton);
	        
	        JLabel lblNewLabel_1_1 = new JLabel("Table Name:");
	        lblNewLabel_1_1.setForeground(new Color(244, 164, 96));
	        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
	        lblNewLabel_1_1.setBounds(42, 169, 140, 28);
	        frmDataCleaningBy.getContentPane().add(lblNewLabel_1_1);
	        
	        t_name = new JTextField();
	        t_name.setFont(new Font("Tahoma", Font.BOLD, 18));
	        t_name.setForeground(new Color(244, 164, 96));
	        t_name.setBackground(new Color(92, 107, 192));
	        t_name.setColumns(10);
	        t_name.setBounds(267, 166, 160, 34);
	        frmDataCleaningBy.getContentPane().add(t_name);
	        
	        JLabel lblNewLabel_2_1 = new JLabel("Filling Missing Value By:");
	        lblNewLabel_2_1.setForeground(new Color(244, 164, 96));
	        lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 18));
	        lblNewLabel_2_1.setBounds(42, 321, 226, 26);
	        frmDataCleaningBy.getContentPane().add(lblNewLabel_2_1);
	        
	        table_1 = new JTable();
	        table_1.setFont(new Font("Tahoma", Font.BOLD, 18));
	        table_1.setForeground(new Color(244, 164, 96));
	        table_1.setBackground(new Color(92, 107, 192));
	        table_1.setModel(new DefaultTableModel(
	        	new Object[][] {
	        	},
	        	new String[] {
	        		"Result"
	        	}
	        ));
	        table_1.setBounds(581, 152, 305, 401);
	        JScrollPane scrollPane = new JScrollPane(table_1);
			scrollPane.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
			scrollPane.setForeground(Color.YELLOW);
			scrollPane.setBackground(Color.BLACK);
			scrollPane.setBounds(new Rectangle(581, 152, 305, 401));
			frmDataCleaningBy.getContentPane().add(scrollPane);
			
			JLabel lblNewLabel_1_1_1 = new JLabel("Result:");
			lblNewLabel_1_1_1.setForeground(new Color(244, 164, 96));
			lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblNewLabel_1_1_1.setBounds(581, 91, 140, 28);
			frmDataCleaningBy.getContentPane().add(lblNewLabel_1_1_1);
//	        frame.getContentPane().add(table_1);
		
	}
}

