package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FIFO {
    List<String> processID= new ArrayList<>();
    List<String> cycles = new ArrayList<>();
    List<String> memoryKB = new ArrayList<>();

    public void csvReader(){
        //Replace with path of the csv file
        String path = "/home/vforzce/IdeaProjects/CSC4320_Project1/processes.csv";
        String line = " ";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while((line = br.readLine()) != null ) {
                String [] values = line.split(",");
                processID.add(values[0]);
                cycles.add(values[1]);
                memoryKB.add(values[2]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] processIDArrayListIntoArray(){
        int[] processArray = processID.stream().mapToInt(i-> Integer.parseInt(i)).toArray();
        return processArray;
    }

    public int[] cyclesArrayListIntoArray(){
        int[] cyclesArray = cycles.stream().mapToInt(i-> Integer.parseInt(i)).toArray();
        return cyclesArray;
    }

    public int[] memoryArrayListIntoArray(){
        int[] memoryArray = memoryKB.stream().mapToInt(i-> Integer.parseInt(i)).toArray();
        return memoryArray;
    }

    public static void findingAverageTime(int processes [], int burstTime[], int num){
        int[] waitTime = new int[num];
        int [] turnAroundTime = new int[num];
        findingWaitTime(burstTime, num, waitTime);
        findingTurnAroundTime(burstTime, num, waitTime, turnAroundTime);
        int totalWaitTime = 0;
        int totalTurnAroundTime = 0;

        for(int i = 0; i<num; i++) {
            totalWaitTime += waitTime[i];
            totalTurnAroundTime += turnAroundTime[i];
            System.out.println("Process: " + (i + 1) + ", Burst Time: " + burstTime[i] + ", Wait Time: " + waitTime[i] + ", Turn Around Time: " + turnAroundTime[i]);
        }
        float averageWaitTime = (float) totalWaitTime/num;
        float averageTurnAroundTime = (float) totalTurnAroundTime/num;
        System.out.println("Average Wait Time: " + averageWaitTime);
        System.out.print("Average Turn Around Time: " + averageTurnAroundTime);
    }

    public static void findingWaitTime(int burstTime[], int num, int waitTime[]){
        waitTime[0] = 0;
        for (int i = 1; i < num - 1; i++) {
            waitTime[i] = burstTime[i - 1] + waitTime [i - 1];
        }
    }

    public static void findingTurnAroundTime(int burstTime[], int num, int waitTime[], int turnAroundTime[]) {
        for(int i = 0; i< num; i++) {
            turnAroundTime[i] = burstTime[i] + waitTime[i];
        }
    }

    public void FiFoScheduling(){
        csvReader();
        findingAverageTime(processIDArrayListIntoArray(), cyclesArrayListIntoArray(), processID.size());

    }



}
