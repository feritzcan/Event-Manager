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
import com.mysql.jdbc.Statement;

import other.UserInfo;
import other.adminInfo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;

import javax.swing.JTextPane;

import java.awt.Color;

import javax.swing.JTextField;

import java.awt.SystemColor;


public class userFound extends JPanel {

	/**
	 * Create the panel.
	 */
	Image image;
	public UserInfo info;
	userFound thiss;
	JFrame f;
	public userFound(final UserInfo info,final profilePanel p) {
		setBackground(SystemColor.textHighlight);
		
		this.thiss=this;
		this.f=f;
		this.info=info;
		setSize(400,600);
		setLayout(null);
		image=new ImageIcon(info.image).getImage();
		JLabel nameLabel = new JLabel(info.name);
		nameLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		nameLabel.setBounds(63, 70, 116, 42);
		add(nameLabel);
		
		final JLabel txtrInfo = new JLabel();
		txtrInfo.setBackground(Color.ORANGE);
		txtrInfo.setText(info.info);
		txtrInfo.setBounds(6, 270, 212, 278);
		add(txtrInfo);
		
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
		
		JButton btnBacm = new JButton("BACK");
		btnBacm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				p.setVisible(true);
			}
		});
		btnBacm.setBounds(243, 539, 117, 29);
		add(btnBacm);

	}
	
	public void paintComponent(Graphics g)
	{

	    g.drawImage(image, 250,0,150,150,this);
	}
}



