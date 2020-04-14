package net.skhu.qrcheck.domain;

import java.util.ArrayList;
import java.util.List;

public class Att {
	String name;
	List<Integer> state = new ArrayList<Integer>();
	
	public Att() {
		for(int i=0; i<6; ++i)
			state.add(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getState() {
		return state;
	}

	public void setState(List<Integer> state) {
		this.state = state;
	}
}
