package com.company.guardedLock;

import com.sun.corba.se.impl.orbutil.threadpool.ThreadPoolManagerImpl;

import java.util.concurrent.*;

public class GuardLockMain {
	public static void main(String[] args) {
		ArrayBlockingQueue<String> drop = new ArrayBlockingQueue<>(1);

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		executorService.execute(new Producer(drop));
		executorService.execute(new Consumer(drop));
//		executorService.execute(new Consumer(drop));
		executorService.shutdown();

		try {
			executorService.awaitTermination(10000000,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Shuted down");

	}
}
