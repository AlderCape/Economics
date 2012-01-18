package com.aldercape.internal.economics.ui.client;

import javax.swing.JLabel;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Client.ClientRenderTarget;
import com.aldercape.internal.economics.ui.base.AbstractEntryPanel;
import com.aldercape.internal.economics.ui.base.StringTextField;

public class ClientPanel extends AbstractEntryPanel implements ClientRenderTarget {

	private static final long serialVersionUID = 4376682418152888271L;
	private StringTextField name;

	public ClientPanel(ApplicationModel model) {
		super(model);
		add(new JLabel("Name"));
		name = new StringTextField();
		add(name);
	}

	@Override
	public void addEntry() {
		model.getClientRepository().add(createClient());

	}

	private Client createClient() {
		return new Client(name.getValue());
	}

	public void setEntry(Client client) {
		client.render(this);
	}

	@Override
	public void renderClient(Client client) {
	}

	@Override
	public void renderName(String name) {
		this.name.setValue(name);
	}

}
