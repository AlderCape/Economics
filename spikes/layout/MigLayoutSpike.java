package layout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class MigLayoutSpike extends JPanel {

	public MigLayoutSpike() {
		setLayout(new MigLayout("wrap 2", "[right]rel[left,grow,fill]", ""));
		add(new JLabel("Units"));
		add(new JTextField(20));
		add(new JLabel("Rate"));
		add(new JTextField());
		add(new JLabel("Person"));
		add(new JTextField());
		add(new JLabel("Client"));
		add(new JTextField());
		add(new JLabel("Bookkeeping month"));
		add(new JTextField());
		add(new JLabel("Cashflow month"));
		add(new JTextField());
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new MigLayoutSpike());
		frame.pack();
		frame.setVisible(true);
	}
}
