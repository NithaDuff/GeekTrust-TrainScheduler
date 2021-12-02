package com.example.geektrust;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Main {
	private static HashMap<String, Integer> trainAmap = new HashMap<>();
	private static HashMap<String, Integer> trainBmap = new HashMap<>();
	public static HashMap<String, HashMap<String, Integer>> trainSchd = new HashMap<>();
	static {
		trainSchd.put("TRAIN_A", trainAmap);
		trainSchd.put("TRAIN_B", trainBmap);

		trainBmap.put("ENGINE", 9999);
		trainBmap.put("TVC", 0);// TRIVANDRUM
		trainBmap.put("SRR", 300);// SHORANUR
		trainBmap.put("MAQ", 600);// MANGALORE
		trainBmap.put("MAO", 1000);// MADGAON
		trainBmap.put("PNE", 1400);// PUNE
		trainBmap.put("HYB", 2000);// HYDERABAD
		trainBmap.put("NGP", 2400);// NAGPUR
		trainBmap.put("ITJ", 2700);// ITARSI
		trainBmap.put("BPL", 2800);// BHOPAL
		trainBmap.put("PTA", 3800);// PATNA
		trainBmap.put("NJP", 4200);// NEW JALPAIGURI
		trainBmap.put("GHY", 4700);// GUWAHATI

		trainAmap.put("ENGINE", 9999);
		trainAmap.put("CHN", 0);// CHENNAI
		trainAmap.put("SLM", 350);// SALEM
		trainAmap.put("BLR", 550);// BANGALORE
		trainAmap.put("KRN", 900);// KURNOOL
		trainAmap.put("HYB", 1200);// HYDERABAD
		trainAmap.put("NGP", 1600);// NAGPUR
		trainAmap.put("ITJ", 1900);// ITARSI
		trainAmap.put("BPL", 2000);// BHOPAL
		trainAmap.put("AGA", 2500);// AGRA
		trainAmap.put("NDL", 2700);// NEW DELHI
	}

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		Main ts = new Main();

		LinkedList<String> a = new LinkedList<>();
		LinkedList<String> b = new LinkedList<>();

		String[] trainA = br.readLine().split(" ");
		String[] trainB = br.readLine().split(" ");
		try {
			a = ts.startTrain(a, trainA);
			b = ts.startTrain(b, trainB);
			ts.displayTrain(a);
			ts.displayTrain(b);

			LinkedList<String> ab = ts.combineTrains(a, b);

			ts.displayTrain(ab);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
	}

	private void displayTrain(LinkedList<String> a) {
		String status = a.getFirst().equals("TRAIN_AB") ? "DEPARTURE" : "ARRIVAL";
		if (a.getLast().equals("ENGINE")) {
			System.out.println("JOURNEY_ENDED");
			return;
		}
		System.out.print("\n" + status + " ");
		for (String stn : a) {
			System.out.print(stn + " ");
		}

	}

	public LinkedList<String> combineTrains(LinkedList<String> a, LinkedList<String> b) {
		b.pop();
		a.addAll(b);
		sortByDistance(a);
		if (a.contains("HYB")) {
			a.remove("HYB");
		}
		a.pop();
		a.addFirst("TRAIN_AB");
		return a;
	}

	public LinkedList<String> startTrain(LinkedList<String> train, String[] trainInput) throws Exception {
		if (verifyName(trainInput[0])) {
			makeTrain(train, trainInput);
			dropOffAtHYB(train);
		}
		return train;
	}

	public LinkedList<String> makeTrain(LinkedList<String> train, String[] trainInput) throws Exception {

		for (String bg : trainInput) {
			if (bg.startsWith("TRAIN_")) {
				train.add(bg);
				continue;
			}
			if (findDistance(bg, train.getFirst()) == -1) {
				throw new Exception(bg + " station not in train route.");
			}
			train.addLast(bg);
		}
		return train;
	}

	private boolean verifyName(String trainName) throws Exception {
		if (trainSchd.get(trainName) == null) {
			throw new Exception("Unknown train. Please check input.");
		}
		return true;
	}

	public LinkedList<String> dropOffAtHYB(LinkedList<String> t) {
		HashMap<String, Integer> map = new HashMap<>();
		String trainName = t.getFirst();
		List<String> droppedOff = new ArrayList<>();
		for (String stn : t) {
			map = trainSchd.get(trainName);
			if (map.get(stn) != null && map.get(stn) < map.get("HYB")) {
				droppedOff.add(stn);
			}
		}
		t.removeAll(droppedOff);
		return t;
	}

	public Integer findDistance(String stn, String train) {
		if (trainSchd.get(train).get(stn) == null) {
			try {
				train = toggleTrain(train);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return trainSchd.get(train).get(stn) == null ? -1 : trainSchd.get(train).get(stn);
	}

	public String toggleTrain(String train) {
		return train.equals("TRAIN_A") ? "TRAIN_B" : "TRAIN_A";
	}

	public void sortByDistance(LinkedList<String> list) {
		String train = list.pop();
		Collections.sort(list, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return findDistance(o2, train).compareTo(findDistance(o1, train));
			}
		});
		list.addFirst(train);
	}
}
