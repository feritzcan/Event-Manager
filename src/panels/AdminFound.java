package panels;

import javax.swing.JPanel;

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

public class AdminFound extends JPanel {

	/**
	 * Create the panel.
	 */
	adminInfo info;
	Image image;
	private JTable table;
	private JButton button;
	public AdminFound(final adminInfo info,final profilePanel p) {
		setBackground(SystemColor.textHighlight);
		setSize(400,600);
		this.info=info;
		setLayout(null);
		
		table = new JTable(new jtable(info));
		table.setBounds(28, 294, 156, 232);
		add(table);
		
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
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				p.setVisible(true);
			}
		});
		btnBack.setBounds(277, 565, 117, 29);
		add(btnBack);
		

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
