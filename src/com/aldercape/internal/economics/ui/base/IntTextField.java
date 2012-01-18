package com.aldercape.internal.economics.ui.base;

import com.aldercape.internal.economics.ui.CustomTextField;

public class IntTextField extends CustomTextField<Integer> {

	private static final long serialVersionUID = -516835733085808480L;

	@Override
	public Integer getValue() {
		return Integer.parseInt(super.getRawValue());
	}

}
