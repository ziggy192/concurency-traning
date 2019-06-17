package com.company.guardedLock;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Producer implements Runnable {
	private ArrayBlockingQueue<String> messageQueue;

	public Producer(ArrayBlockingQueue messageQueue) {
		this.messageQueue = messageQueue;
	}


	public void run() {

		try {
			String importantInfo[] = {
					"Mares eat oats",
					"Does eat oats",
					"Little lambs eat ivy",
					"A kid will eat ivy too"
			};
			Random random = new Random();

			for (int i = 0;
				 i < importantInfo.length;
				 i++) {
				messageQueue.put(importantInfo[i]);
				Thread.sleep(random.nextInt(5000));
			}
			messageQueue.put("DONE");
		} catch (InterruptedException e) {
			//not handle the interupted exception
			e.printStackTrace();
		}
	}

}
