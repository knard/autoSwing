package org.knard.tools.autoSwing.parser;

import java.util.ArrayList;
import java.util.List;

public abstract class AbtractListSelector implements TreeSelector {
	protected List<TreeSelector> selectors = new ArrayList<TreeSelector>();

	public void add(TreeSelector selector) {
		selectors.add(selector);
	}

	public boolean hasSelector() {
		return selectors.size() != 0;
	}

	public TreeSelector getLastSelector() {
		int size = getSize();
		if (size > 0) {
			return getSelector(size - 1);
		}
		return null;
	}

	public TreeSelector getSelector(int i) {
		return selectors.get(i);
	}

	public int getSize() {
		return selectors.size();
	}
}
