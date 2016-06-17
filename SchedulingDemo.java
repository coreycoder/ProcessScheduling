package processScheduling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;

public class SchedulingDemo extends Thread {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner kb = new Scanner(System.in);
		System.out.println("Welcome to Corey's Scheduling Algorithm Program!");
		int input = 0;
		while(true) {
			System.out.println("----------------------------------------------------");
			System.out.println("Enter name of file to read jobs from.");
			System.out.print("=> ");
			File file = new File(kb.nextLine());
			
			if (!file.exists()) {
				System.out.println("Input file not found.");
				System.exit(0);
			}
			input = 0;
			while (input != 5) {
				System.out.println("\n----------------------------------------------------");
				System.out.println("1) First Come First Serve");
				System.out.println("2) Shortest Job First");
				System.out.println("3) Round Robin w/ Time Slice = 3");
				System.out.println("4) Round Robin w/ Time Slice = 4");
				System.out.println("5) Enter a different file");
				System.out.println("6) Exit Program");
				System.out.println("Enter scheduling algorithm to run");
				System.out.print("=> ");
				input = kb.nextInt();
				kb.nextLine();
				
				switch(input) {
				case 1: {
					System.out.println("First Come First Serve");
					System.out.println("----------------------------------------------------");
					firstComeFirstServe(file);
					break;
				}
				case 2: {
					System.out.println("Shortest Job First");
					System.out.println("----------------------------------------------------");
					shortestJobFirst(file);
					break;
				}
				case 3: {
					System.out.println("Round Robin w/ Time Slice = 3");
					System.out.println("----------------------------------------------------");
					roundRobin(file, 3);
					break;
				}
				case 4: {
					System.out.println("Round Robin w/ Time Slice = 4");
					System.out.println("----------------------------------------------------");
					roundRobin(file, 4);
					break;
				}
				case 6: {
					System.out.println("Thank you for using!");
					System.exit(0);
				}
				}
			}
		}
	}
	public static void firstComeFirstServe(File file) throws FileNotFoundException {
		Scanner input_file = new Scanner(file);
		
		Queue<Process> queue = new LinkedList<Process>();
		ArrayList<Integer> gantt = new ArrayList<Integer>();
		ArrayList<Integer[]> places = new ArrayList<Integer[]>();
		
		while(input_file.hasNext()) {
			String name = input_file.next();
			int time = input_file.nextInt();
			Process process = new Process(name, time);
			queue.add(process);			
		}
		
		double start = System.currentTimeMillis();
		while(!queue.isEmpty()) {
			Process process = queue.poll();
			String name = process.getProcess();
			int time = process.getBurst();
			System.out.println(name + " with burst of " + time + " ms.");
			try {
				Thread.sleep(time);
			}
			catch(Exception e) {
				System.out.print("thread not working");
			}
			gantt.add(time);
		}
		double end = System.currentTimeMillis() - start;
		System.out.println("This took " + end + " milliseconds.");
		
		System.out.println("\nGANTT CHART\n-------------------------------------------");
		
		int sum = 0;
		for (int i = 0; i < gantt.size(); i++) {
			sum += gantt.get(i);
		}
		for (int i = 0; i < gantt.size(); i++) {
			float percentage = (float)gantt.get(i) / sum;
			int spaces = (int)(percentage * 150);
			Integer[] temp = {spaces, gantt.get(i)};
			places.add(temp);
			for (int j = 0; j < spaces; j++)
				System.out.print("@");
			System.out.print("|");
		}
		System.out.print("\n0");
		int total = 0; 
		for (int i = 0; i < places.size(); i++) {
			boolean flag = true;
			for (int j = 0; j < places.get(i)[0] - 1; j++) {
				if (total > 100 && flag) {
					places.get(i)[0]--;
					flag = false;
				}
				System.out.print(" ");
			}
			total += places.get(i)[1];
			System.out.print(total);
		}
		input_file.close();
	}
	
	public static void shortestJobFirst(File file) throws FileNotFoundException { 
		Scanner input_file = new Scanner(file);
		
		PriorityQueue<Process> queue = new PriorityQueue<Process>();
		ArrayList<Integer> gantt = new ArrayList<Integer>();
		ArrayList<Integer[]> places = new ArrayList<Integer[]>();
		
		while(input_file.hasNext()) {
			String name = input_file.next();
			int time = input_file.nextInt();
			Process process = new Process(name, time);
			queue.add(process);			
			
		}
		
		double start = System.currentTimeMillis();
		while(!queue.isEmpty()) {
			Process process = queue.poll();
			String name = process.getProcess();
			int time = process.getBurst();
			System.out.println(name + " with burst of " + time + " ms.");
			try {
				Thread.sleep(time);
			}
			catch(Exception e) {
				System.out.print("thread not working");
			}
			gantt.add(time);
		}
		double end = System.currentTimeMillis() - start;
		System.out.println("This took " + end + " milliseconds.");
		
		System.out.println("\nGANTT CHART\n-------------------------------------------");
		
		int sum = 0;
		for (int i = 0; i < gantt.size(); i++) {
			sum += gantt.get(i);
		}
		for (int i = 0; i < gantt.size(); i++) {
			float percentage = (float)gantt.get(i) / sum;
			int spaces = (int)(percentage * 150);
			Integer[] temp = {spaces, gantt.get(i)};
			places.add(temp);
			for (int j = 0; j < spaces; j++)
				System.out.print("@");
			System.out.print("|");
		}
		System.out.print("\n0");
		int total = 0; 
		for (int i = 0; i < places.size(); i++) {
			boolean flag = true;
			for (int j = 0; j < places.get(i)[0] - 1; j++) {
				if (total < 10 && flag) {
					places.get(i)[0]++;
					flag = false;
				}
				
				if (total > 100 && flag) {
					places.get(i)[0]--;
					flag = false;
				}
				System.out.print(" ");
			}
			total += places.get(i)[1];
			System.out.print(total);
		}
		input_file.close();
	}
	
	public static void roundRobin(File file, int quantum) throws FileNotFoundException {
		Scanner input_file = new Scanner(file);
		
		Queue<Process> queue = new LinkedList<Process>();
		
		while(input_file.hasNext()) {
			String name = input_file.next();
			int time = input_file.nextInt();
			Process process = new Process(name, time);
			queue.add(process);			
		}
		
		double start = System.currentTimeMillis();
		while(!queue.isEmpty()) {
			Process process = queue.poll();
			String name = process.getProcess();
			int time = process.getBurst();
			System.out.println(name + " with burst of " + time + " ms has started");

			try {
				Thread.sleep(quantum);
			}
			catch(Exception e) {
				System.out.print("thread not working");
			}
			if (quantum < time) {
				process.setBurst(time - quantum);
				queue.add(process);
			}
			else 
				System.out.println(name + " with burst of " + time + " ms has ENDED");
		}
		double end = System.currentTimeMillis() - start;
		System.out.println("This took " + end + " milliseconds.");
		
		input_file.close();
	}
	
	private static class Process implements Comparable<Process> {
		private String process;
		private int burst;
		
		public Process(String process, int burst) {
			this.process = process;
			this.burst = burst;
		}
		
		public String getProcess() {
			return this.process;
		}
		
		public int getBurst() {
			return this.burst;
		}
		
		public void setBurst(int burst) {
			this.burst = burst;
		}

		public boolean equals(Process other) {
			return this.getBurst() == other.getBurst();
		}
		
		public int compareTo(Process other) {
			if (this.equals(other))
				return 0;
			else if (this.getBurst() > other.getBurst())
				return 1;
			else
				return -1;
		}
	}
}
