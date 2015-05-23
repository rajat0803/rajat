package com.denver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConveyorSystem {

	public static final String FIELD_SEPERATOR = "~";

	public static final String SECTION_START = "#Section:";

	public static final String ARRIVAL = "ARRIVAL";

	public static final String BAGGAGE_CLAIM = "BaggageClaim";

	protected Map<String, Conveyor> conveyorMap = new HashMap<String, Conveyor>();

	protected Map<String, Departure> departureMap = new HashMap<String, Departure>();

	protected List<Bag> bagList = new ArrayList<Bag>();

	public static void main(String[] args) {

		try {
			ConveyorSystem conSys = new ConveyorSystem();
			conSys.readInputData();
			conSys.process();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void process() {
		for (int i = 0; i < bagList.size(); i++) {
			Bag bag = bagList.get(i);
			processBag(bag);
			printOut(bag);
		}
	}

	private void printOut(Bag bag) {

		String str = bag.getBagNo() + " ";
		for (int i = 0; i < bag.getPointList().size(); i++) {
			str = str + " " + bag.getPointList().get(i);
		}
		str = str + " : " + bag.getTotalTime();
		System.out.println(str);
	}

	private void processBag(Bag bag) {

		bag.getPointList().add(bag.getEntryPoint());
		String bagEndPoint = BAGGAGE_CLAIM;
		if (!ARRIVAL.equals(bag.getFlightId())) {
			bagEndPoint = departureMap.get(bag.getFlightId()).getGate();
		}

		Conveyor conveyor = conveyorMap.get(createConveyorKey(bag.getEntryPoint(), bagEndPoint));
		if (conveyor != null) {
			bag.getPointList().add(bagEndPoint);
			bag.setTotalTime(bag.getTotalTime() + conveyor.getDistance());
			return;
		}

		Conveyor nextBagPoint = getNextPoint(bag.getEntryPoint());
		while (!nextBagPoint.getEndNode().equals(bagEndPoint)) {
			bag.getPointList().add(nextBagPoint.getEndNode());
			bag.setTotalTime(bag.getTotalTime() + nextBagPoint.getDistance());

			conveyor = conveyorMap.get(createConveyorKey(nextBagPoint.getEndNode(), bagEndPoint));
			if (conveyor != null) {
				bag.getPointList().add(bagEndPoint);
				bag.setTotalTime(bag.getTotalTime() + conveyor.getDistance());
				return;
			}
			nextBagPoint = getNextPoint(nextBagPoint.getEndNode());
		}
		bag.getPointList().add(nextBagPoint.getEndNode());

	}

	private void readInputData() throws IOException, InvalidDataException {
		File inputFile = new File("src/inputData.dat");
		if (!inputFile.exists()) {
			throw new IOException("src/inputData.dat does not exist");
		}

		BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
		List<String> lines = new ArrayList<String>();
		String readLine = null;
		while ((readLine = bufferedReader.readLine()) != null) {
			lines.add(readLine);
		}
		bufferedReader.close();

		int currSection = 0;

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.startsWith(SECTION_START)) {
				currSection++;
				continue;
			}
			if (currSection == 1) {// Conveyor
				Conveyor conveyor = new Conveyor(line);
				conveyorMap.put(conveyor.getKey(), conveyor);
				continue;
			}
			if (currSection == 2) {// Departure
				Departure departure = new Departure(line);
				departureMap.put(departure.getFlightId(), departure);
				continue;
			}
			if (currSection == 3) {// Bags
				bagList.add(new Bag(line));
				continue;
			}
		}
	}

	private Conveyor getNextPoint(String startPoint) {
		Iterator<String> conKeyIter = conveyorMap.keySet().iterator();
		Conveyor outConvey = null;
		while (conKeyIter.hasNext()) {
			String key = (String) conKeyIter.next();
			if (key.startsWith(startPoint + FIELD_SEPERATOR)) {
				if (outConvey == null || outConvey.getDistance() > conveyorMap.get(key).getDistance()) {
					outConvey = conveyorMap.get(key);
					continue;
				}
			}
		}
		return outConvey;
	}

	public static String createConveyorKey(String startNode, String endNode) {
		return startNode + FIELD_SEPERATOR + endNode;
	}
}
