package com.aldercape.internal.economics.ui;

import javax.swing.JTextField;

public class CustomTextField<T> extends JTextField {

	private static final long serialVersionUID = -5871629493420093865L;

	@Override
	public void setText(String text) {
		throw new RuntimeException("Should call setValue");
	}

	@Override
	public String getText() {
		throw new RuntimeException("Should call getValue");
	}

	public void setValue(T value) {
		super.setText(value.toString());
	}

	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) super.getText();
	}

	protected String getRawValue() {
		return super.getText();
	}

}
