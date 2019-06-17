package com.company.forkJoin;


import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ForkBlur extends RecursiveAction {

	private int[] mSource;
	private int mStart;
	private int mLength;
	private int[] mDestination;

	// Processing window size; should be odd.
	private int mBlurWidth = 15;

	protected static int sThreshold = 100;


	public ForkBlur(int[] mSource, int mStart, int mLength, int[] mDestination) {
		this.mSource = mSource;
		this.mStart = mStart;
		this.mLength = mLength;
		this.mDestination = mDestination;
	}

	private void computeDirectly() {
		int sidePixels = (mBlurWidth - 1) / 2;
		for (int index = mStart; index < mStart + mLength; index++) {
			// Calculate average.
			float rt = 0, gt = 0, bt = 0;
			for (int mi = -sidePixels; mi <= sidePixels; mi++) {
				int mindex = Math.min(Math.max(mi + index, 0),
						mSource.length - 1);
				int pixel = mSource[mindex];
				rt += (float) ((pixel & 0x00ff0000) >> 16)
						/ mBlurWidth;
				gt += (float) ((pixel & 0x0000ff00) >> 8)
						/ mBlurWidth;
				bt += (float) ((pixel & 0x000000ff) >> 0)
						/ mBlurWidth;
			}

			// Reassemble destination pixel.
			int dpixel = (0xff000000) |
					(((int) rt) << 16) |
					(((int) gt) << 8) |
					(((int) bt) << 0);
			mDestination[index] = dpixel;
		}

		System.out.println(String.format("Thread=%s|blurring mStart-mEnd | %05d-%05d", Thread.currentThread().getName(), mStart, mStart + mLength - 1));
		try {
			java.lang.Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void compute() {
		if (mLength < sThreshold
		) {
			computeDirectly();
			return;
		}

		int split = mLength / 2;
		ForkJoinTask<Void> fork = new ForkBlur(mSource, mStart, split, mDestination).fork();
		ForkJoinTask<Void> fork1 = new ForkBlur(mSource, mStart + split, mLength - split, mDestination).fork();
		fork.join();
		fork1.join();
	}
}
