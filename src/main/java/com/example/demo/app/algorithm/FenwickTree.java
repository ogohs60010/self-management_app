package com.example.demo.app.algorithm;

import java.util.ArrayList;

public class FenwickTree {
    private ArrayList<Integer> BIT;

    public FenwickTree(ArrayList<Integer> nums) {
        BIT = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            update(i, nums.get(i));
        }
    }

    public void update(int i, int val) {
        i++;
        while (i < BIT.size()) {
            BIT.add(i,BIT.get(i)+ val);
            i += i & -i;
        }
    }

    public int sum(int i) {
        i++;
        int res = 0;
        while (i > 0) {
            res += BIT.get(i);
            i -= i & -i;
        }
        return res;
    }

    public int sumRange(int i, int j) {
        return sum(j) - sum(i - 1);
    }
}