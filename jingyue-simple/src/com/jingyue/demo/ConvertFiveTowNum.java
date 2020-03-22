package com.jingyue.demo;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 将10进制的数据转换为15进制的实例代码
 */
public class ConvertFiveTowNum {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int nextInt = sc.nextInt();
            System.out.println(getResult(nextInt));
        }
    }
    public static String getResult(int x) {
        ArrayList<Integer> remainder = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        // 找到终点
        while(x != 0) {

            remainder.add(x % 15);
            x /= 15;

        }
        // 反向输出
        for(int i = remainder.size() -1; i>=0; i--) {
            if(remainder.get(i) == 10) {
                sb.append('A');
            }else if(remainder.get(i) == 11){
                sb.append('B');
            }else if(remainder.get(i) == 12){
                sb.append('C');
            }else if(remainder.get(i) == 13){
                sb.append('D');
            }else if(remainder.get(i) == 14){
                sb.append('E');
            }else {
                sb.append(remainder.get(i));
            }
        }
        return sb.toString();
    }
}
