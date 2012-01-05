package com.aldercape.internal.economics.model;

public enum TimeUnit {
	HOUR {

		@Override
		public int inHours(Unit u) {
			return u.amount;
		}

		@Override
		public int inDays(Unit u) {
			return u.amount / 8;
		}
	},
	DAY {

		@Override
		public int inHours(Unit u) {
			return u.amount * 8;
		}

		@Override
		public int inDays(Unit u) {
			return u.amount;
		}
	};

	public abstract int inHours(Unit u);

	public abstract int inDays(Unit u);
}