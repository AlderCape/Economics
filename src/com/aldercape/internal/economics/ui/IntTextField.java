package com.aldercape.internal.economics.ui;

public class IntTextField extends CustomTextField<Integer> {

	private static final long serialVersionUID = -516835733085808480L;

	@Override
	public Integer getValue() {
		return Integer.parseInt(super.getRawValue());
	}

}
