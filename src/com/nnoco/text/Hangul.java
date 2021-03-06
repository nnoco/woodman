package com.nnoco.text;

/* (@)# Hangul.java
 * 
 */
public class Hangul {
	//============================================================//
	// Const Literal Member Fields
	//============================================================//
	private static final char[] onsets = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ".toCharArray();
	private static final char[] nucleus = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ".toCharArray();
	private static final char[] coda = "ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ".toCharArray();

	//============================================================//
	// Constructors
	//============================================================//
	private Hangul(){ }
	
	
	//============================================================//
	// Class(static) Member Method
	//============================================================//
	/**
	 * 한글 텍스트로부터 초성만을 추출하여 반환하는 메서드 <br>
	 * getOnset("한글 텍스트abc") 호출 시<br>
	 * "ㅎㄱ ㅌㅅㅌabc" 반환
	 * @param str 초성을 추출할 원본 문자열 
	 * @return 초성 추출 결과
	 */
	public static String getOnsets(String str){
		StringBuilder result = new StringBuilder();
		char c ='\0';
		int indexOfOnset = 0;
		
		for(int i = 0 ; i < str.length() ; i++){
			c = str.charAt(i);
			
			if(isHangul(c))
			{
				indexOfOnset = (c - '가') / 588;
				result.append(onsets[indexOfOnset]);
			} else {
				result.append(c);
			}
		}
		
		return result.toString();
	}
	
	// 한글 범위 판단
	private static boolean isHangul(char c){
		return '가' <= c && c <= '힣';
	}
	
	// 조사의 구분을 위한 종성을 가지고 있는지 판단
	public static boolean hasCoda(String text){
		char lastChar = text.charAt(text.length()-1);
		return (lastChar - '가') % 28 != 0;
	}
	
	//============================================================//
	// Main method for code test
	//============================================================//
	public static void main(String[] args) {
		System.out.println(Hangul.getOnsets("한글 테스트abc"));
	}
}
