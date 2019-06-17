package com.company.forkJoin;

import java.util.concurrent.ForkJoinPool;

public class ForkMain {
	public static void main(String[] args) {
		int[] src = new int[10000];
		int[] dst = new int[10000];
		ForkBlur fb = new ForkBlur(src, 0, src.length, dst);
		fb.invoke();
//		ForkJoinPool pool = new ForkJoinPool(10);
//		pool.invoke(fb);
	}
}
