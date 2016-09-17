package panels;

import java.awt.Graphics;
import java.awt.Image;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import other.ObservingTextField;
import other.adminInfo;
import other.jtable;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.util.JDatePickerUtil;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.qt.datapicker.DatePicker;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;

public class AdminPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	adminInfo info;
	Image image;
	private JTable table;
	private ObservingTextField txtDate;
	private JTextField txtOrganizationName;
	private JButton btnNewButton;
	private JButton button;
	private JTextArea txtrInfo;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	public AdminPanel(final adminInfo info) {
		setBackground(SystemColor.textHighlight);
		setSize(400,600);
		this.info=info;
		setLayout(null);
		
		table = new JTable(new jtable(info));
		table.setBounds(28, 294, 156, 232);
		add(table);
		
		txtDate = new ObservingTextField();
		txtDate.setText("DATE");
		txtDate.setBounds(246, 387, 134, 28);
		add(txtDate);
		txtDate.setColumns(10);
		
		txtOrganizationName = new JTextField();
		txtOrganizationName.setText("Organization Name");
		txtOrganizationName.setBounds(246, 344, 134, 28);
		add(txtOrganizationName);
		txtOrganizationName.setColumns(10);

		JButton btnChange = new JButton("change");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");

				final JFileChooser fc = new JFileChooser();
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);
				if(returnVal==JFileChooser.APPROVE_OPTION)
				{
					Connection connect;
					try {
						connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/admin"+
								"?verifyServerCertificate=false"+
								"&useSSL=false"+
								"&requireSSL=true","root","feritferit");
						Statement mystm=(Statement) connect.createStatement();
						
						
						String sql="update login set img='"+fc.getSelectedFile().getPath()+"'"+" where username='"+info.name+"'";
						mystm.executeUpdate(sql);
						image=new ImageIcon(fc.getSelectedFile().getPath()).getImage();
						repaint();
						  connect.close();


					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}


			}
		});

		btnChange.setBounds(277, 155, 117, 29);
		add(btnChange);
		
		JButton btnAdd = new JButton("add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/admin"+
							"?verifyServerCertificate=false"+
							"&useSSL=false"+
							"&requireSSL=true","root","feritferit");
					Statement mystm=(Statement) connect.createStatement();
					String sql="insert into "+info.name+"(organization,date,info)"+"values ('"+txtOrganizationName.getText()+"','"+txtDate.getText()+"','"+txtrInfo.getText()+"')";
					mystm.executeUpdate(sql);
					table.repaint();
					
					connect.close();

					connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/organizations"+
							"?verifyServerCertificate=false"+
							"&useSSL=false"+
							"&requireSSL=true","root","feritferit");
					 mystm=(Statement) connect.createStatement();
					 sql =  "CREATE TABLE "+txtOrganizationName.getText()  +
			                   "(username VARCHAR(45))"; 
					 
					 
						mystm.executeUpdate(sql);
					
						connect.close();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnAdd.setBounds(263, 538, 117, 29);
		add(btnAdd);
		
		btnNewButton = new JButton("+");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String lang=null;
				final Locale locale=getLocale(lang);
				
				DatePicker dp=new DatePicker(txtDate, locale);
				Date selected=dp.parseDate(txtDate.getText());
				dp.setSelectedDate(selected);
				dp.start(txtDate);
				
			}
		});
		btnNewButton.setBounds(210, 388, 33, 28);
		add(btnNewButton);
		
		button = new JButton("Show Users");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str="";

				try {
					Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/organizations"+
							"?verifyServerCertificate=false"+
							"&useSSL=false"+
							"&requireSSL=true","root","feritferit");
					
					
					String query = "SELECT * FROM "+table.getValueAt(table.getSelectedRow(),0);
					Statement st = (Statement) connect.createStatement();
					ResultSet rs = st.executeQuery(query);
					
					while(rs.next())
					{
						str+=rs.getString("username")+"\n";
					}

					connect.close();


				} catch (SQLException w) {
					// TODO Auto-generated catch block
					w.printStackTrace();
				}
				
				JFrame frame = new JFrame("JOptionPane showMessageDialog example");
				JOptionPane.showMessageDialog(frame,str);
			}
		});
		button.setBounds(28, 538, 146, 29);
		add(button);
		
		txtrInfo = new JTextArea();
		txtrInfo.setText("INFO");
		txtrInfo.setBounds(256, 424, 122, 102);
		add(txtrInfo);
		
		label = new JLabel(info.name);
		label.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		label.setBounds(50, 42, 116, 42);
		add(label);
		
		label_1 = new JLabel("Current Events");
		label_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		label_1.setBounds(28, 241, 156, 42);
		add(label_1);
		
		label_2 = new JLabel("New Organization");
		label_2.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		label_2.setBounds(238, 279, 156, 42);
		add(label_2);
		

		image=new ImageIcon(info.image).getImage();

	}

	private Locale getLocale(String loc)
	{
		if(loc!=null&&loc.length()>0)
		{
			return new Locale(loc);
		}
		else
		{
			return Locale.US;
		}
	}
	public void paintComponent(Graphics g)
	{

	    g.drawImage(image, 250,0,150,150,this);
	}
}
