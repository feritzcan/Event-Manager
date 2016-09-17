package panels;

import javax.swing.JPanel;

import other.eventInfo;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.SystemColor;

import javax.swing.JLabel;

import java.awt.Font;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EventPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	String event;
	int index=0;
	ArrayList<eventInfo> infos=new ArrayList<eventInfo>();
	eventInfo info;
	public EventPanel(final profilePanel p,String eventName) {
		
		this.event=eventName;
		// SETTİNG EVENT INFOS
		initInfo();
		info=infos.get(index);
		
		
		setBackground(SystemColor.textHighlight);
		setForeground(Color.BLUE);
		
		setSize(400,600);
		setLayout(null);
		
		final JLabel lblEventName = new JLabel(info.name);
		lblEventName.setFont(new Font("Lucida Grande", Font.BOLD, 23));
		lblEventName.setBounds(88, 6, 293, 72);
		add(lblEventName);
		
		final JLabel lblOrganizator = new JLabel(info.organizator);
		lblOrganizator.setBounds(128, 76, 166, 44);
		add(lblOrganizator);
		
		final JLabel lblDate = new JLabel(info.date);
		lblDate.setBounds(128, 121, 156, 31);
		add(lblDate);
		
		final JLabel lblInfo = new JLabel(info.info);
		lblInfo.setForeground(SystemColor.menu);
		lblInfo.setBackground(SystemColor.scrollbar);
		lblInfo.setBounds(47, 182, 293, 206);
		add(lblInfo);
		
		JButton btnJoin = new JButton("JOIN");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String server="organizations";
				Connection connect;
				try {
					connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+server+"?verifyServerCertificate=false"+"&useSSL=false"+"&requireSSL=true","root","feritferit");
					Statement mystm=(Statement) connect.createStatement();
					String sql="insert into "+info.organizator+"(username)"+"values ('"+p.info.name+"')";
					mystm.executeUpdate(sql);
					connect.close();

				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnJoin.setBounds(36, 490, 117, 29);
		add(btnJoin);
		
		JButton btnNewButton = new JButton("INVITE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  try {
					Desktop.getDesktop().browse(new URI("https://newmail.bilkent.edu.tr/src/webmail.php"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewButton.setBounds(205, 490, 117, 29);
		add(btnNewButton);
		
		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				index++;
				if(index>=infos.size())
				{
					index=0;
				}
				info=infos.get(index);
				lblDate.setText(info.date);
				lblEventName.setText(info.name);
				lblOrganizator.setText(info.organizator);
				lblInfo.setText(info.info);
				repaint();			}
		});
		button.setBounds(372, 161, 28, 119);
		add(button);
		
		JButton btnBackToProfile = new JButton("Back to Profile");
		btnBackToProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.setVisible(true);
				setVisible(false);
			}
		});
		btnBackToProfile.setBounds(6, 533, 104, 61);
		add(btnBackToProfile);

		
	}
	
	
	
	public void initInfo()
	{

		try{
			Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/admin"+
					"?verifyServerCertificate=false"+
					"&useSSL=false"+
					"&requireSSL=true","root","feritferit");
			
			String query = "SELECT * FROM login";
			Statement st = (Statement) connect.createStatement();
			ResultSet rs = st.executeQuery(query);
		    while(rs.next()){
		    	String adminName=rs.getString("username");
		    	String organization;
		    	String date;
		    	String info;
				 query = "SELECT * FROM "+adminName;
				 st = (Statement) connect.createStatement();
				 ResultSet rs2=st.executeQuery(query);

		    	while(rs2.next())
		    	{
		    		organization=rs2.getString("organization");
		    		date=rs2.getString("date");
		    		info=rs2.getString("info");
		    		infos.add(new eventInfo(adminName, date, info, organization));
		    		if(organization.equalsIgnoreCase(event))
		    		{
		    			index=infos.size()-1;
		    		}
		    	}
		    	
		    }
			connect.close();

		}catch (Exception ex){
		    System.out.println(ex.getMessage());
		}
	}

}
