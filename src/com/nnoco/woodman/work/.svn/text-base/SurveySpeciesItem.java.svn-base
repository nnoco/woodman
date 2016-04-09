package com.nnoco.woodman.work;

import java.util.Arrays;
import java.util.StringTokenizer;

import android.util.Log;

import com.nnoco.data.HandleExternalData;

public class SurveySpeciesItem {
	private String name;
	private String type;

	int sumOfTreeCount; // 전체 입목 합계
	int sumOfRemoveCount; // 전체 제거 합계

	int[] countOfTree; // 입목 수
	int[] countOfRemove; // 제거 수

	public SurveySpeciesItem(String name, String type) {
		this.name = name;
		this.type = type;

		countOfTree = new int[22];
		countOfRemove = new int[22];
		Arrays.fill(countOfTree, 0);
		Arrays.fill(countOfRemove, 0);

		loadCounts();
	}

	// 파일로부터 카운트 데이터를 읽어옴
	private void loadCounts() {
		String treeCountData = HandleExternalData.load("/매목 조사/" + name
				+ "_입목.txt");
		String removeCountData = HandleExternalData.load("/매목 조사/" + name
				+ "_제거.txt");

		if (treeCountData != null && treeCountData.length() != 0) {
			treeCountData = treeCountData.replace("\r", "");
			String[] split = treeCountData.split("\n");

			for (int i = 0; i < split.length; i++) {
				if (split[i].length() == 0)
					break;

				countOfTree[i] = Integer.parseInt(split[i]);
			}

			sumOfTreeCount = sumOfArray(countOfTree);
		}

		if (removeCountData != null && removeCountData.length() != 0) {
			removeCountData = removeCountData.replace("\r", "");
			String[] split = removeCountData.split("\n");

			for (int i = 0; i < split.length; i++) {
				if (split[i].length() == 0)
					break;

				countOfRemove[i] = Integer.parseInt(split[i]);
				Log.d("값 확인", countOfRemove[i] + "");
			}

			sumOfRemoveCount = sumOfArray(countOfRemove);
		}
	}

	// 파일에 카운트 데이터를 저장
	public void saveCounts() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < countOfTree.length; i++) {
			builder.append(countOfTree[i] + "\r\n");
		}
		HandleExternalData.save("/매목 조사/" + name + "_입목.txt",
				builder.toString());

		builder = new StringBuilder();
		for (int i = 0; i < countOfRemove.length; i++) {
			builder.append(countOfRemove[i] + "\r\n");
		}
		HandleExternalData.save("/매목 조사/" + name + "_제거.txt",
				builder.toString());
	}

	public int adjustTree(int index, int value) {
		countOfTree[index] += value;
		if(countOfTree[index] < 0) {
			countOfTree[index] = 0;
			return 0;
		}
		sumOfTreeCount += value;
		
		return countOfTree[index];
	}

	public int adjustRemove(int index, int value) {
		countOfRemove[index] += value;
		if(countOfRemove[index] < 0){
			countOfRemove[index] = 0;
			return 0;
		}
		sumOfRemoveCount += value;
		
		return countOfRemove[index];
	}

	private int sumOfArray(int[] arr) {
		int sum = 0;
		for (int temp : arr)
			sum += temp;
		return sum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSumOfTreeCount() {
		return sumOfTreeCount;
	}

	public void setSumOfTreeCount(int sumOfTreeCount) {
		this.sumOfTreeCount = sumOfTreeCount;
	}

	public int getSumOfRemoveCount() {
		return sumOfRemoveCount;
	}

	public void setSumOfRemoveCount(int sumOfRemoveCount) {
		this.sumOfRemoveCount = sumOfRemoveCount;
	}

	public int[] getCountOfTree() {
		return countOfTree;
	}

	public void setCountOfTree(int[] countOfTree) {
		this.countOfTree = countOfTree;
	}

	public int[] getCountOfRemove() {
		return countOfRemove;
	}

	public void setCountOfRemove(int[] countOfRemove) {
		this.countOfRemove = countOfRemove;
	}
}
