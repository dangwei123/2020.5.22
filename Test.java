
import java.util.Scanner;
public class Main{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        while(sc.hasNext()){
            int n=sc.nextInt();
            int[] arr=new int[n];
            for(int i=0;i<n;i++){
                arr[i]=sc.nextInt();
            }
            int max=Integer.MIN_VALUE;
            int sum=0;
            for(int i=0;i<n;i++){
                sum+=arr[i];
                max=Math.max(max,sum);
                if(sum<0){
                    sum=0;
                }
            }
            System.out.println(max);
        }
    }
}



import java.util.Scanner;
public class Main{
    private static int isValid(String s){
        if("".equals(s)||s.length()==1) return -1;
        char t=s.charAt(0);
        if(t!='A'&&t!='S'&&t!='D'&&t!='W') return -1;
        int res=0;
        for(int i=1;i<s.length();i++){
            char c=s.charAt(i);
            if(c<'0'||c>'9') return -1;
            res=res*10+c-'0';
        }
        return res<100?res:-1;
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        while(sc.hasNext()){
            String line=sc.nextLine();
            String[] str=line.split(";");
            int x=0;
            int y=0;
            for(String s:str){
                int tmp=isValid(s);
                if(tmp!=-1){
                    char c=s.charAt(0);
                    if(c=='A'){
                        x-=tmp;
                    }else if(c=='S'){
                        y-=tmp;
                    }else if(c=='D'){
                        x+=tmp;
                    }else{
                        y+=tmp;
                    }
                }
            }
            System.out.println(x+","+y);
        }
    }
}


众所周知，牛妹是一个offer收割姬，这次面试她遇到了这样的一个问题。
给了一个序列，让找出最长的“凸子序列”
何为“凸子序列”：数列中有一个xi,使得所有x0<x1<x2….xi-1<xi且xi>xi+1>xi+1>….>xn
eg：12345431,是山峰序列，12345234不是山峰序列
注：单调递增或单调递减序列也算山峰序列；单独一个数是长度为1的山峰序列

import java.util.*;


public class Solution {
    /**
     * 返回最大山峰序列长度
     * @param numberList int整型一维数组 给定的序列
     * @return int整型
     */
    public int mountainSequence (int[] numberList) {
        // write code here
        int n=numberList.length;
        if(n==0) return 0;
        int max=1;
        int[] leftlen=new int[n];
        int[] maxindex=new int[n];
        int j=0;
        for(int i=0;i<n;i++){
            int left=0;
            int right=j;
            while(left<right){
                int mid=left+(right-left)/2;
                if(leftlen[mid]<numberList[i]){
                    left=mid+1;
                }else{
                    right=mid;
                }
            }
            leftlen[left]=numberList[i];
            if(left==j) j++;
            maxindex[i]=left+1;
        }
        
        int[] rightlen=new int[n];
        j=0;
        for(int i=n-1;i>=0;i--){
            int left=0;
            int right=j;
            while(left<right){
                int mid=left+(right-left)/2;
                if(rightlen[mid]<numberList[i]){
                    left=mid+1;
                }else{
                    right=mid;
                }
            }
            rightlen[left]=numberList[i];
            if(left==j) j++;
            maxindex[i]+=left;
            max=Math.max(max,maxindex[i]);
        }
        return max;
    }
}


牛妹在和牛牛玩扔骰子，他们的游戏规则有所不同；
每个人可以扔nn次mm面骰子，来获得nn个数
得分为任意选取nn个数中的某些数求和所不能得到的最小的正整数
得分大的人获胜
例如扔骰子33次得到了 11 22 55，那么这个人的得分是44

牛妹想知道这回合她是否能赢
牛妹的n个数存在数组a中，牛牛的n个数存在数组b中
数组下标从0开始


public class Solution {
    /**
     * 
     * @param n int整型 n
     * @param m int整型 m
     * @param a int整型一维数组 a
     * @param b int整型一维数组 b
     * @return string字符串
     */
    public String Throwdice (int n, int m, int[] a, int[] b) {
        // write code here
        Arrays.sort(a);
        Arrays.sort(b);
        long r1=0;
        for(int i=0;i<n;i++){
            if(a[i]>r1+1) break;
            r1+=a[i];
        }
        
        long r2=0;
        for(int i=0;i<n;i++){
            if(b[i]>r2+1) break;
            r2+=b[i];
        }
        
        if(r1>r2){
            return "Happy";
        }
        return "Sad";
    }
}


牛妹出去旅行啦，她准备去NN个城市旅行，去每个城市的开销是A_{i}A 
i元。但是牛妹有强迫症，她想在去y城市之前先旅游x城市，于是牛妹列出了这些限制条件list。
 并且牛妹很节约，她只有VV元，她每次会选择当前能去的花费最小的城市,如有多个花费一样的
 则首先去编号小的城市，她想知道她最多能到多少个城市去旅游。
 
//方法一(超时)：
class Solution {
    /**
     *
     * @param N int整型 N
     * @param V int整型 V
     * @param A int整型一维数组 A
     * @param list Point类一维数组 List
     * @return int整型
     */
    private Map<Integer,Integer> map=new HashMap<>();
    private Point[] p;
    private int max;
    public int Travel (int N, int V, int[] A, Point[] list) {
        for(int i=0;i<N;i++){
            map.put(A[i],i);
        }
        p=list;
        Arrays.sort(A);
        back(N,V,A,new HashSet<>(),0);
        return max;
    }
    private void back(int N,int V,int[] A,Set<Integer> set,int num){
        if(V<=0||num==N) return;
        for(int i=0;i<N;i++){
            if(V<A[i]) return;
            int index=map.get(A[i]);
            if(set.contains(index)) continue;
            if(isValid(index,set)){
                V-=A[i];
                set.add(index);
                num+=1;
                max=Math.max(max,num);
                back(N,V,A,set,num);
                V+=A[i];
                set.remove(index);
                num-=1;
            }
        }
    }
    private boolean isValid(int index, Set<Integer> set){
        for(Point t:p){
            if(t.y-1==index){
                if(!set.contains(t.x-1)) return false;
            }
        }
        return true;
    }
}

class Point {
   int x;
   int y;
   public Point(int x,int y){
       this.x=x;
       this.y=y;
   }
}


//方法二：

import java.util.*;

/*
 * public class Point {
 *   int x;
 *   int y;
 * }
 */

public class Solution {
    /**
     * 
     * @param N int整型 N
     * @param V int整型 V
     * @param A int整型一维数组 A
     * @param list Point类一维数组 List
     * @return int整型
     */
    public int Travel (int N, int V, int[] A, Point[] list) {
        // write code here
        Map<Integer,List<Integer>> map=new HashMap<>();
        int[] indegrees=new int[N];
        for(Point p:list){
            List<Integer> tmp=map.get(p.x-1);
            if(tmp==null){
                tmp=new ArrayList<>();
                map.put(p.x-1,tmp);
            }
            tmp.add(p.y-1);
            indegrees[p.y-1]++;
        }
        
        PriorityQueue<Integer> pq=new PriorityQueue<>((o1,o2)->{
            return A[o1]==A[o2]?o1-o2:A[o1]-A[o2];
        });
        for(int i=0;i<N;i++){
            if(indegrees[i]==0) pq.offer(i);
        }
        int res=0;
        while(!pq.isEmpty()&&V>0){
            int index=pq.poll();
            if(V>=A[index]){
                res++;
                V-=A[index];
                if(map.containsKey(index)){
                    for(Integer i:map.get(index)){
                        --indegrees[i];
                        if(indegrees[i]==0){
                            pq.offer(i);
                        }
                    }
                }
            }else{
                break;
            }
        }
        return res;
    }
}
