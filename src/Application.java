import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.aldercape.internal.economics.model.ApplicationModel;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.ui.AddEntryPanel;
import com.aldercape.internal.economics.ui.InvoiceEntryPanel;
import com.aldercape.internal.economics.ui.LedgerTableModel;
import com.aldercape.internal.economics.ui.TimeEntryPanel;

public class Application {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Ledger ledger = new Ledger();
		ApplicationModel model = new ApplicationModel(ledger);
		JTable table = new JTable();
		LedgerTableModel tableModel = new LedgerTableModel(ledger);
		table.setModel(tableModel);
		model.addLedgerListner(tableModel);
		frame.add(new JScrollPane(table));
		AddEntryPanel addEntryPanel = new AddEntryPanel();
		addEntryPanel.addType("Invoice Entry", new InvoiceEntryPanel(model));
		addEntryPanel.addType("Time Entry", new TimeEntryPanel(model));
		frame.add(addEntryPanel, BorderLayout.SOUTH);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}