package com.example.squares;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.view.View;
import android.widget.Button;

public class GameAi {
	public final static String TAG = GameAi.class.getSimpleName();
	private int mWhichButton;
	private List<Integer> mListOfAiButtonsTemp = new ArrayList<Integer>(); 
	protected List<Integer> mListOfAiButtonsToPress = new ArrayList<Integer>();
	protected int compareCount;

	public void setupAiButtons(){
		compareCount = 0;
		mWhichButton = (getRandomNumber(MainActivity.mDifficulty)); // get random number for random button to press
	    mListOfAiButtonsToPress.add(mWhichButton); // add random number to mLOABTP
	    mListOfAiButtonsTemp.addAll(mListOfAiButtonsToPress); // add all elements of mLOABTP to mLOABT
		System.out.println("mListOfAiButtonsToPress: " + mListOfAiButtonsToPress);
	} // end setupAiButtons()
	
	public int getFirstButtonInList() {
		if (!mListOfAiButtonsTemp.isEmpty()) {
		return mListOfAiButtonsTemp.get(0);
		}
		else {
			return 0;
		}
	}
	
	public void deleteFirstButtonInList() {
		if (!mListOfAiButtonsTemp.isEmpty()){
		mListOfAiButtonsTemp.remove(mListOfAiButtonsTemp.get(0)); // first slot of MLOABT removed
		}
		else {
			return;
		}
	}
	
	public boolean buttonsListIsEmpty() {
		if (mListOfAiButtonsTemp.isEmpty()){
			return true;
		}
		else {
			return false;
		}
	}
	
	//TODO: This method...
	public void iterateButtons() { // for changing way AI presses buttons...
		int compareIndex = compareCount; // 0 for setupAiButtons	
	}
	
	public boolean comparePlayerAndAiButtons(View sender) {
			Button [] buttonArray = MainActivity.getArray();
			Button bt = (Button) sender;
			System.out.println("Button bt set to sender");
			int compareIndex = compareCount; // compareCount set to 0 in setupAiButtons()
			Button aibt = (Button) buttonArray[mListOfAiButtonsToPress.get(compareIndex)];
			//System.out.println("Button Aibt set to buttonArray[MLOABTP.get(compareCount)]: " + buttonArray[mListOfAiButtonsToPress.get(compareCount)]);
			if (bt.getId() == aibt.getId()) {
				compareCount++;
				System.out.println("Compared, true!");
				return true;
			}
			else
			{
				compareCount = mListOfAiButtonsToPress.size();
				System.out.println("Wrong button pressed");
				return false;
			}
		}
	
	public void resetGameAi() {
		mListOfAiButtonsTemp.clear();
		mListOfAiButtonsToPress.clear();
	}
	
	private int getRandomNumber(int amountOfButtons) {
		Random randomNumberGenerator = new Random();
		int randomNumberToReturn = randomNumberGenerator.nextInt(amountOfButtons);
		return randomNumberToReturn;
	}
}
