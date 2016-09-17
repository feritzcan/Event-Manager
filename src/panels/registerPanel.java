package panels;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.SystemColor;
import javax.swing.JLabel;

public class registerPanel extends JPanel {
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JLabel lblPleaseLoginOr;

	/**
	 * Create the panel.
	 */
	public registerPanel(final JFrame f) {
		setBackground(SystemColor.textHighlight);

		setSize(400,600);
		setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setText("USERNAME");
		txtUsername.setBounds(105, 117, 161, 42);
		add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setText("PASSWORD");
		txtPassword.setBounds(101, 195, 165, 50);
		add(txtPassword);
		txtPassword.setColumns(10);
		
		final JCheckBox chckbxNormalUse = new JCheckBox("Admin login");
		chckbxNormalUse.setBounds(105, 281, 128, 23);
		add(chckbxNormalUse);
		
		JButton btnSave = new JButton("save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				Connection connect;
				try {
					String server;
					if(chckbxNormalUse.isSelected()==true)
					{
						server="admin";
					}
					else
					{
						server="users";
					}
					connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+server+
							"?verifyServerCertificate=false"+
							"&useSSL=false"+
							"&requireSSL=true","root","feritferit");
					Statement mystm=(Statement) connect.createStatement();
					
					
					String sql;
					if(server=="admin")
					{
						sql="insert into login"+"(username,password,img)"+"values ('"+txtUsername.getText()+"','"+txtPassword.getText()+"','src/img/noimage.jpg')";
						mystm.executeUpdate(sql);

						
						 sql =  "CREATE TABLE "+txtUsername.getText()  +
				                   "(organization VARCHAR(45) not NULL, " +
				                   " date VARCHAR(45),"+
				                   " info VARCHAR(45))"; 
							mystm.executeUpdate(sql);



					}
					else
					{
						sql="insert into normal"+"(username,password,img,info,free)"+"values ('"+txtUsername.getText()+"','"+txtPassword.getText()+"','src/img/noimage.jpg','','')";
						mystm.executeUpdate(sql);

					}
					
					setVisible(false);
					f.getContentPane().add(new LoginPanel(f));
					  connect.close();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnSave.setBounds(264, 325, 130, 66);
		add(btnSave);
		
		lblPleaseLoginOr = new JLabel("Please login or Register");
		lblPleaseLoginOr.setBounds(96, 36, 298, 36);
		add(lblPleaseLoginOr);
	}
}
