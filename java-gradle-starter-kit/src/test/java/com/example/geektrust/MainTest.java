package com.example.geektrust;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

import org.junit.Test;

public class MainTest {
	@Test
	public void test(){
		LinkedList<String> a = new LinkedList<String>();
		LinkedList<String> b = new LinkedList<String>();
		Main ts = new Main();
		try {
		BufferedReader br = new BufferedReader(new FileReader("./sample_input/input1.txt"));
		//Sample input 1
		String[] trainA = br.readLine().split(" ");
		String[] trainB = br.readLine().split(" ");
		assertEquals("[TRAIN_A, ENGINE, NDL, NDL, GHY, NJP, NGP]",ts.startTrain(a, trainA).toString());
		assertEquals("[TRAIN_B, ENGINE, NJP, GHY, AGA, BPL, PTA]",ts.startTrain(b, trainB).toString());
		assertEquals("[TRAIN_AB, ENGINE, ENGINE, GHY, GHY, NJP, NJP, PTA, NDL, NDL, AGA, BPL, NGP]",
				ts.combineTrains(a,b).toString());
		
		//Sample input 2
		br = new BufferedReader(new FileReader("./sample_input/input2.txt"));
		trainA = br.readLine().split(" ");
		trainB = br.readLine().split(" ");
		a.clear();
		b.clear();
		assertEquals("[TRAIN_A, ENGINE, HYB, NGP, ITJ]",ts.startTrain(b, trainA).toString());
		assertEquals("[TRAIN_B, ENGINE, NJP, PTA]",ts.startTrain(a, trainB).toString());
		assertEquals("[TRAIN_AB, ENGINE, ENGINE, NJP, PTA, ITJ, NGP]",ts.combineTrains(a, b).toString());
		
		//Empty trains
		a.clear();
		b.clear();
		assertEquals("[TRAIN_A, ENGINE]",ts.startTrain(a, "TRAIN_A ENGINE".split(" ")).toString());
		assertEquals("[TRAIN_B, ENGINE]",ts.startTrain(b, "TRAIN_B ENGINE".split(" ")).toString());
		assertEquals("[TRAIN_AB, ENGINE, ENGINE]",ts.combineTrains(a, b).toString());
		
		//Unknown stations
		a.clear();
		assertEquals(-1,ts.startTrain(a, "TRAIN_A ENGINE ABC".split(" ")).toString());
		
		
		//Test toggle
		assertEquals("TRAIN_A",ts.toggleTrain("TRAIN_B"));
		assertEquals("TRAIN_B",ts.toggleTrain("TRAIN_A"));
		assertNull("Unknown train", ts.toggleTrain("TRAIN_C"));
		
		//Test makeTrain
		a.clear();
		assertEquals("[TRAIN_A, ENGINE, NDL, NDL, KRN, GHY, SLM, NJP, NGP, BLR]",ts.makeTrain(a, trainA).toString());
		} catch(Exception e) {
			e.getMessage();
		}
		
	}
}