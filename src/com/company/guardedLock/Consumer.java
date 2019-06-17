package com.company.guardedLock;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Consumer implements Runnable
{
	private ArrayBlockingQueue<String> messageQueue;

	public Consumer(ArrayBlockingQueue<String> messageQueue) {
		this.messageQueue = messageQueue;
	}

	@Override
	public void run() {
		try {
			Random random = new Random();
			for (String message = messageQueue.take();
				 ! message.equals("DONE");
				 message = messageQueue.take()) {
				System.out.format("MESSAGE RECEIVED: %s%n", message);
					Thread.sleep(random.nextInt(5000));
			}
		} catch (InterruptedException e) {
			//not handle interupted exception
			e.printStackTrace();
		}
	}
}
