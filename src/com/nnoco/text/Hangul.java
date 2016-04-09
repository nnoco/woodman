package com.nnoco.text;

/* (@)# Hangul.java
 * 
 */
public class Hangul {
	//============================================================//
	// Const Literal Member Fields
	//============================================================//
	private static final char[] onsets = "��������������������������������������".toCharArray();
	private static final char[] nucleus = "�������¤äĤŤƤǤȤɤʤˤ̤ͤΤϤФѤҤ�".toCharArray();
	private static final char[] coda = "������������������������������������������������������".toCharArray();

	//============================================================//
	// Constructors
	//============================================================//
	private Hangul(){ }
	
	
	//============================================================//
	// Class(static) Member Method
	//============================================================//
	/**
	 * �ѱ� �ؽ�Ʈ�κ��� �ʼ����� �����Ͽ� ��ȯ�ϴ� �޼��� <br>
	 * getOnset("�ѱ� �ؽ�Ʈabc") ȣ�� ��<br>
	 * "���� ������abc" ��ȯ
	 * @param str �ʼ��� ������ ���� ���ڿ� 
	 * @return �ʼ� ���� ���
	 */
	public static String getOnsets(String str){
		StringBuilder result = new StringBuilder();
		char c ='\0';
		int indexOfOnset = 0;
		
		for(int i = 0 ; i < str.length() ; i++){
			c = str.charAt(i);
			
			if(isHangul(c))
			{
				indexOfOnset = (c - '��') / 588;
				result.append(onsets[indexOfOnset]);
			} else {
				result.append(c);
			}
		}
		
		return result.toString();
	}
	
	// �ѱ� ���� �Ǵ�
	private static boolean isHangul(char c){
		return '��' <= c && c <= '�R';
	}
	
	// ������ ������ ���� ������ ������ �ִ��� �Ǵ�
	public static boolean hasCoda(String text){
		char lastChar = text.charAt(text.length()-1);
		return (lastChar - '��') % 28 != 0;
	}
	
	//============================================================//
	// Main method for code test
	//============================================================//
	public static void main(String[] args) {
		System.out.println(Hangul.getOnsets("�ѱ� �׽�Ʈabc"));
	}
}