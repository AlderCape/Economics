import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.ui.LedgerFrame;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class Application {

	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");

		ApplicationModel model = new ApplicationModel(new Ledger());
		__TestObjectMother objectMother = new __TestObjectMother();
		model.getCollaboratorRepository().add(objectMother.me());
		model.getCollaboratorRepository().add(objectMother.other());
		LedgerFrame frame = new LedgerFrame(model);
		frame.pack();
		frame.setVisible(true);
	}
}
