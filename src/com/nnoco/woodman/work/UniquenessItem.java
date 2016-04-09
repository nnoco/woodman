package com.nnoco.woodman.work;

public class UniquenessItem {
	private String text;
	private boolean checked;

	public UniquenessItem(String text) {
		this.text = text;
		this.checked = false;
	}

	public void check(String target) {
		checked = text.equals(target);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
