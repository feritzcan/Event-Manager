package panels;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class mainFrame {

	public static void main(String[] args) throws SQLException {

		
		
		JFrame frame=new JFrame();
		frame.setSize(400,600);
		frame.add(new profilePanel(null, frame));
		frame.setVisible(true);
	}

}
