package panels;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import other.UserInfo;
import other.adminInfo;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JCheckBox;

public class LoginPanel extends JPanel {
	private JTextField textField;
	private JPasswordField textField_1;
	private JCheckBox chckbxAdmin;

	/**
	 * Create the panel.
	 */
	public LoginPanel(final JFrame f) {
		
		setSize(400,600);
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(119, 247, 143, 33);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JPasswordField();
		textField_1.setColumns(10);
		textField_1.setBounds(119, 327, 143, 33);
		add(textField_1);
		
		JButton btnEnter = new JButton("ENTER");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				

				Connection connect;
				try {
					boolean admin=chckbxAdmin.isSelected();
					String s=admin?"admin":"users";
					
					connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+s+
							"?verifyServerCertificate=false"+
							"&useSSL=false"+
							"&requireSSL=true","root","feritferit");
					
						String query;
						
						if(!admin)
						{
							query="select * from normal where username ='"+textField.getText()+"'" +" and password ='"+textField_1.getText()+"'";

						}
						else
						{
							query="select * from login where username ='"+textField.getText()+"'" +" and password ='"+textField_1.getText()+"'";

						}

						Statement stmnt=(Statement) connect.createStatement();
						
						ResultSet rs=stmnt.executeQuery(query);
						

						
						if(rs.next())
						{
							if(admin)
							{
								adminInfo info=new adminInfo(rs.getString("username"),rs.getString("password"),rs.getString("img"));
								setVisible(false);
								f.getContentPane().add(new AdminPanel(info));
							}
							else
							{
								UserInfo info=new UserInfo(rs.getString("username"), rs.getString("img"),rs.getString("info"),rs.getString("password"),rs.getString("free"));
								setVisible(false);
								f.getContentPane().add(new profilePanel(info,f));
							}
							
						
						}

					
				
				
						  connect.close();

					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEnter.setBounds(256, 377, 117, 29);
		add(btnEnter);
		
		JButton btnSignUp = new JButton("SIGN UP!");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				f.getContentPane().add(new registerPanel(f));
			}
		});
		btnSignUp.setBounds(256, 513, 117, 46);
		add(btnSignUp);
		
		chckbxAdmin = new JCheckBox("ADMIN");
		chckbxAdmin.setBounds(116, 393, 128, 23);
		add(chckbxAdmin);

	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(new ImageIcon("src/img/noimage.jpg").getImage(),150,100,100,100,this);
	}
}
