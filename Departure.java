package com.denver;


public class Departure {

	private String flightId;

	private String gate;

	private String destination;

	private String time;

	public Departure(String detials) throws InvalidDataException {
		String[] split = detials.split(ConveyorSystem.FIELD_SEPERATOR);
		if (split == null || split.length < 4) {
			throw new InvalidDataException("Departure Record:[" + detials + "] ");
		}
		flightId = split[0];
		gate = split[1];
		destination = split[2];
		time = split[3];
	}

	public String getFlightId() {
		return flightId;
	}

	public String getGate() {
		return gate;
	}

	public String getDestination() {
		return destination;
	}

	public String getTime() {
		return time;
	}

}
