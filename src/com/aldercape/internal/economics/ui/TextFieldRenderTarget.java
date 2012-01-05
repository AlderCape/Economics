package com.aldercape.internal.economics.ui;

import javax.swing.JTextField;

import com.aldercape.internal.economics.model.RenderTarget;
import com.aldercape.internal.economics.model.SelfRenderable;

public abstract class TextFieldRenderTarget<T extends SelfRenderable> extends JTextField implements RenderTarget {

	private static final long serialVersionUID = 3745664097755979656L;

	protected abstract T createDomainObject();

	@Override
	public final void setSelectedItem(Object item) {
	}

	@Override
	public void setDisplayText(String text) {
		setText(text);
	}

}
