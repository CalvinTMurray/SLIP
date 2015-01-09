package main;

import java.util.Arrays;

/**
 * Created by Calvin . T . Murray on 09/01/2015.
 */
public class Test {

    public static void main (String[] args) {

        double[] a = new double[]{0, 1.5, 2.3, 2.7, 3.1};

        System.out.println(Math.abs(Arrays.binarySearch(a, 0.1) + 1));
        System.out.println(closestValueBinarySearch(a, -0.1, 0.55));

    }

    public static Double closestValueBinarySearch(double[] a, double key, double interval) {

        int lowIndex = 0;
        int highIndex = a.length - 1;
        int mid = (lowIndex + highIndex)/2;

        // Binary search (mid should be index to insert value at
        while (lowIndex < highIndex) {

            if (a[mid] == key) {
                return a[mid];
            } else if (a[mid] > key) {
                highIndex = mid;
            } else if (a[mid] < key) {
                lowIndex = mid + 1;
            }

            mid = (lowIndex + highIndex)/2;
            System.out.println("mid: " + mid);
        }

        // Interval check
        if (mid > 0 && mid < a.length && (key - interval <= a[mid - 1] || key + interval >= a[mid])) {

            // Closest Value
            if ((key - a[mid-1]) < (a[mid] - key)) {
                return a[mid-1];
            } else {
                return a[mid];
            }
        }

        return null;
    }

}
