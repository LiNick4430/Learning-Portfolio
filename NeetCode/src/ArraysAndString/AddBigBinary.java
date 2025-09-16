package ArraysAndString;

import java.math.BigInteger;

public class AddBigBinary {
	public static String addBinary(String a, String b) {
		// 二進位 字串 轉成 十進位 相加 再轉乘二進位  
		return Integer.toBinaryString(Integer.parseInt(a, 2) + Integer.parseInt(b, 2));
	}

	public static String addBigBinary(String a, String b) {
		// 用於大數字的 BigInteger
		BigInteger bigA = new BigInteger(a, 2);		// new BigInteger(a, 2) 	把 字串 a 用 2 進位 解析 後, 轉成 10進位 存入
		BigInteger bigB = new BigInteger(b, 2);

		BigInteger sum = bigA.add(bigB);
		return sum.toString(2);						// 把 sum (10進位 的 BigInteger) 轉成 2 進位 後 再轉成 字串
	}

	public static void main(String[] args) {

		String a = "11";
		String b = "1";

		System.out.println(addBinary(a, b));

		String bigA = "1111111111111111111111111111111";
		String bigB = "1";
		System.out.println(addBigBinary(bigA, bigB));
	}
}
