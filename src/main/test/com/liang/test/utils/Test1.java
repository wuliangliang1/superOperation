package com.liang.test.utils;

/**
 * Created by ll.wu on 2018/6/22.
 */
public class Test1 {
    public static void main(String[] args) {
        int[] A = {1,2,3};
        solution(A);
    }
    public static int solution(int[] A) {
        // write your code in Java SE 8
        int maxNum2 = 0;
        int maxNum5 = 0;
        int maxNum0 = 0;
        for(int i = 0;i< A.length;i++){
            int num = A[i];
            int num0 = 0;
            int num2 = 0;
            int num5 = 0;
            while (true){
                if(num % 10 == 0){
                    num = num /10;
                    num0 ++;
                }else{
                    break;
                }
            }
            if(num0 > maxNum0)
                maxNum0 = num0;


        }
        return 0;
    }
}
