package com.denver;


public class Conveyor {

	private String startNode;

	private String endNode;

	private int distance;

	public Conveyor(String detials) throws InvalidDataException {
		String[] split = detials.split(ConveyorSystem.FIELD_SEPERATOR);
		if (split == null || split.length < 3) {
			throw new InvalidDataException("Conveyor Record:[" + detials + "] ");
		}
		startNode = split[0];
		endNode = split[1];
		try {
			distance = new Integer(split[2]);
		} catch (NumberFormatException e) {
			throw new InvalidDataException("Conveyor Record:[" + detials + "] ", e);
		}
	}

	public String getStartNode() {
		return startNode;
	}

	public String getEndNode() {
		return endNode;
	}

	public int getDistance() {
		return distance;
	}

	public String getKey() {
		return ConveyorSystem.createConveyorKey(startNode, endNode);
	}
}
