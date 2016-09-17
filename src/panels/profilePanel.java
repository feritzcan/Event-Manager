package panels;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.Statement;
import com.qt.datapicker.DatePicker;

import other.ObservingTextField;
import other.UserInfo;
import other.adminInfo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;
import java.util.Observer;
import java.awt.Font;

import javax.swing.JTextPane;

import java.awt.Color;

import javax.swing.JTextField;

import java.awt.SystemColor;

public class profilePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	Image image;
	public UserInfo info;
	private ObservingTextField time;
	profilePanel thatPanel;
	JFrame f;
	private JTextField textField;
	public profilePanel(final UserInfo info,final JFrame f) {
		setBackground(SystemColor.textHighlight);
		
		this.thatPanel=this;
		this.f=f;
		this.info=info;
		setSize(400,600);
		setLayout(null);
		image=new ImageIcon(info.image).getImage();
		JLabel nameLabel = new JLabel(info.name);
		nameLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		nameLabel.setBounds(63, 70, 116, 42);
		add(nameLabel);
		
		final JTextArea txtrInfo = new JTextArea();
		txtrInfo.setBackground(Color.ORANGE);
		txtrInfo.setText(info.info);
		txtrInfo.setBounds(6, 270, 212, 278);
		add(txtrInfo);
		
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
						connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users"+
								"?verifyServerCertificate=false"+
								"&useSSL=false"+
								"&requireSSL=true","root","feritferit");
						Statement mystm=(Statement) connect.createStatement();
						
						
						String sql="update normal set img='"+fc.getSelectedFile().getPath()+"'"+" where username='"+info.name+"'";
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
		
		final JTextPane txtpnAda = new JTextPane();
		txtpnAda.setText(info.freeTimes);
		txtpnAda.setBounds(243, 269, 144, 120);
		add(txtpnAda);
		
		JLabel lblPersonalInfo = new JLabel("Personal Info");
		lblPersonalInfo.setBounds(18, 229, 167, 29);
		add(lblPersonalInfo);
		
		JLabel label = new JLabel("Free Tımes");
		label.setBounds(233, 229, 167, 29);
		add(label);
		
		time = new ObservingTextField();
		time.setBounds(287, 401, 84, 28);
		add(time);
		time.setColumns(10);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connect;
				try {
					connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users"+
							"?verifyServerCertificate=false"+
							"&useSSL=false"+
							"&requireSSL=true","root","feritferit");
					Statement mystm=(Statement) connect.createStatement();
					
					String newFree=txtpnAda.getText()+"\n"+time.getText();
					
					String sql="update normal set free='"+newFree+"' where username='"+info.name+"' and password='"+info.password+"'";
					mystm.executeUpdate(sql);
					
					txtpnAda.setText(newFree);
					repaint();
			

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(228, 402, 57, 29);
		add(btnNewButton);
		
		JButton btnSeeEvents = new JButton("SEE Events");
		btnSeeEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				f.getContentPane().add(new EventPanel(thatPanel,""));
				
			}
		});
		btnSeeEvents.setBounds(255, 456, 116, 73);
		add(btnSeeEvents);
		
		JButton btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username=textField.getText();
				String searching="admin";
				Connection connect;
				String query;
				ResultSet rs;
				Statement stmnt;
				boolean isFound=false;

				
				
				try {
					connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+searching+
							"?verifyServerCertificate=false"+
							"&useSSL=false"+
							"&requireSSL=true","root","feritferit");
					
					query="select * from login where username ='"+textField.getText()+"'";
					stmnt=(Statement) connect.createStatement();
					
					rs=stmnt.executeQuery(query);

					if(rs.next())
					{
						
							isFound=true;
							adminInfo info=new adminInfo(rs.getString("username"),rs.getString("password"),rs.getString("img"));
							setVisible(false);
							f.getContentPane().add(new AdminFound(info, thatPanel));
					
					}
					
					if(isFound==false)
					{
						searching="users";
						connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+searching+
								"?verifyServerCertificate=false"+
								"&useSSL=false"+
								"&requireSSL=true","root","feritferit");
						
						query="select * from normal where username ='"+textField.getText()+"'";
						stmnt=(Statement) connect.createStatement();
						
						rs=stmnt.executeQuery(query);
						
						if(rs.next())
						{

							isFound=true;
							UserInfo info=new UserInfo(rs.getString("username"),rs.getString("img"),rs.getString("info"),rs.getString("password"),rs.getString("free"));
							setVisible(false);
							f.getContentPane().add(new userFound(info, thatPanel));
						}
					}
					if(isFound==false)
					{
						searching="organizations";
						connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+searching+
								"?verifyServerCertificate=false"+
								"&useSSL=false"+
								"&requireSSL=true","root","feritferit");
						
						DatabaseMetaData md = (DatabaseMetaData) connect.getMetaData();
						ResultSet result= md.getTables(null, null, textField.getText(), null);

						if(result.next())
						{
							isFound=true;
							setVisible(false);
							f.getContentPane().add(new EventPanel(thatPanel,textField.getText()));

						}
						

					}

					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnSearch.setBounds(315, 548, 79, 29);
		add(btnSearch);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(236, 549, 84, 28);
		add(textField);
		
		JButton button = new JButton("SAVE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connect;
				try {
					connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users"+
							"?verifyServerCertificate=false"+
							"&useSSL=false"+
							"&requireSSL=true","root","feritferit");
					Statement mystm=(Statement) connect.createStatement();
					
					String newFree=txtrInfo.getText()+"\n"+time.getText();
					
					String sql="update normal set info='"+newFree+"' where username='"+info.name+"' and password='"+info.password+"'";
					mystm.executeUpdate(sql);
					
					txtpnAda.setText(newFree);
					repaint();
			

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(6, 548, 79, 29);
		add(button);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				String lang=null;
				final Locale locale=getLocale(lang);
				
				DatePicker dp=new DatePicker( time, locale);
				Date selected=dp.parseDate(time.getText());
				dp.setSelectedDate(selected);
				dp.start(time);
			}
		});
		btnNewButton_1.setBounds(373, 401, 21, 29);
		add(btnNewButton_1);

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
