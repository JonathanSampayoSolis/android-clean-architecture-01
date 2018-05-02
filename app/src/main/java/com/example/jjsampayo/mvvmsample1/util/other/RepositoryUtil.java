package com.example.jjsampayo.mvvmsample1.util.other;

public class RepositoryUtil {

    public static int[] vectorXToY(int... n) {
        int min;
        int max;
        if (n.length > 1) {
            min = n[0];
            max = (n[1] - n[0]);
        } else {
            min = 1;
            max = n[0];
        }
        int[] v = new int[max];

        for (int i = 0; i < max; i++)
            v[i] = min++;

        return v;
    }

}
