package com.nhaowan.mobile.base.task;

public interface IProxyTask<T> {
	public enum Status {
		FAILED, COMPLETE, GOING;
	}

	public void onTaskStart();

	public Status getStatus();
}
