package algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by 153907N on 12/2/2016.
 */
public class DiskOptimization {
    Properties p = new Properties();
    DiskParameter dp = null;

    public static void main(String args[])
    {
        new DiskOptimization("diskq1.properties");
    }

    public DiskOptimization(String filename)
    {
        try
        {
            p.load(new BufferedReader(new FileReader(filename)));
            dp = new DiskParameter(p);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        generateAnalysis();
    }

    public void generateAnalysis()
    {
        generateFCFS();
        generateSSTF();
    }

    public void printSequence(String name, int location[])
    {
        String sequence = "";
        String working1 = "";
        String working2 = "";
        int total = 0;
        sequence += dp.getCurrent();
        int previous = dp.getCurrent();
        for(int i = 0; i < location.length; i++)
        {
            int current = location[i];
            sequence += "," + current;
            int d = Math.abs(previous - current);

            working1 += "|" + previous + "-" + current + "|+";
            working2 += d + " + ";
            total += d;
            previous = current;
        }

        System.out.println(name + '\n' + "====");
        System.out.println("Order of Access: " + sequence);

        System.out.println("Total Distance = " + working1.substring(0 , working1.length()-1));
        System.out.println("               = " + working2.substring(0, working2.length()-2));
        System.out.println("               = " + total + '\n');
    }

    public void generateFCFS()
    {
        int location[] = dp.getSequence();
        printSequence("FCFS", location);
    }

    public void generateSSTF()
    {
        int location[] = arrangeBySSTF(dp.getCurrent(), dp.getSequence());
        printSequence("SSTF", location);
    }

    private int[] arrangeBySSTF(int current, int sequence[])
    {
        int n = sequence.length;
        int sstf[] = new int[n];
        for(int i = 0; i < n; i++)
        {
            sstf[i] = sequence[i];

        }

        int ii = -1;
        for(int i = 0; i < n; i++)
        {
            int minimum = Integer.MAX_VALUE;
            ii = i;
            for(int j = i; j < n; j++)
            {
                int distance = Math.abs(current - sstf[j]);
                if(distance < minimum)
                {
                    ii = j;
                    minimum = distance;
                }
            }
            int tmp = sstf[i];
            sstf[i] = sstf[ii];
            sstf[ii] = tmp;
            current = sstf[i];
        }
        return sstf;
    }

    public void generateSCAN()
    {
        int location[] = arrangeBySCAN(dp.getCurrent(), dp.getSequence());
        printSequence("SCAN", location);
    }

    public int[] arrangeBySCAN(int current, int sequence[])
    {
        int n = sequence.length;
        int scan[] = new int[n+1];
        int nearEnd = getNearestEnd(current);
        int counter = 0;

        int tempList[] = new int[n];
        for(int i = 0; i <n; i++)
        {
            tempList[i] = sequence[i];
        }
        Arrays.sort(tempList);

        int temp = current;
        if(nearEnd > current)
        {
            for(int i = 0; i < n; i++)
            {
                if (tempList[i] > temp) {
                    scan[counter] = tempList[i];
                    temp = tempList[i];
                    counter++;
                }
            }
            scan[counter] = nearEnd;
            counter++;
            temp = current;
            for(int i = n-1; i >= 0; i--)
            {
                if(tempList[i] < temp)
                {
                    scan[counter] = tempList[i];
                    temp = tempList[i];
                    counter++;
                }
            }

        }else
    }

    public int getNearestEnd(int current)
    {
        return (dp.getCylinders() - current) < current ? dp.getCylinders() : 0;
    }




}
