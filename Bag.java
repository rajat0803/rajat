package com.denver;

import java.util.ArrayList;
import java.util.List;

public class Bag {

	private String bagNo;

	private String entryPoint;

	private String flightId;

	private List<String> pointList = new ArrayList<String>();

	private int totalTime = 0;

	public Bag(String detials) throws InvalidDataException {
		String[] split = detials.split(ConveyorSystem.FIELD_SEPERATOR);
		if (split == null || split.length < 3) {
			throw new InvalidDataException("Bag Record:[" + detials + "] ");
		}
		bagNo = split[0];
		entryPoint = split[1];
		flightId = split[2];
	}

	public String getBagNo() {
		return bagNo;
	}

	public String getEntryPoint() {
		return entryPoint;
	}

	public String getFlightId() {
		return flightId;
	}

	public List<String> getPointList() {
		return pointList;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

}
