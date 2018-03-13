package com.company;
import amazonOA.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Amazon amoa = new Amazon();
        String s = "eatednldaegdfaadaaag";
        int k = 4;
        String[] words = {"main", "a", "main","a","in","main","m"};
        String[] tags = {"main","in","main"};
        //List<String> ksubstring = amoa.getKDistinctSub(s, k);
        //List<String> withrepeat = amoa.getKDiswithOneRepeat(s, k);
        //for (String str : withrepeat){
           // System.out.println(str);
        //}
//        int[][] points = new int[][]{{1,2},{1,1},{3,5},{0,0}};
//        int[][] res = amoa.getCloseM(points, 4);
//        for (int[] p : res){
//            System.out.println(p[0] + "-" + p[1]);
//        }
        //int[] index = amoa.containsTags(words, tags);
        //System.out.println(index[0] + "->" + index[1]);
        Movie m1 = new Movie(1, 12.34f);
        Movie m2 = new Movie(2, 9f);
        Movie m3 = new Movie(3, 14f);
        Movie m4 = new Movie(4, 7.9f);
        Movie m5 = new Movie(5, 10.89f);

//        m1.getSimiliarMovies().add(m2);
//        m1.getSimiliarMovies().add(m4);
//        m2.getSimiliarMovies().add(m5);
//        m4.getSimiliarMovies().add(m5);
//        List<Movie> movies = amoa.getNSimilarMovies(m3, 2);
//        for (Movie m : movies){
//             System.out.println(m.getId());
//        }
//          List<String> l1 = Arrays.asList("a","a","b","anything");
//          List<String> l2 = Arrays.asList("b","a","c","anythings","d");
//          List<List<String>> fruits = new ArrayList<List<String>>();
//          fruits.add(l1);
//          fruits.add(l2);
//          List<String> cart = Arrays.asList("c","a","a","b","d","b","a","b","a","c","d");
//          int res = amoa.checkWinner(fruits, cart);
//        String[][] strs = new String[][]{{"b","c"},{"w","g"},{"d","e"},{"e","f"},{"w","e"}};
//        List<String> res = amoa.itemAssociation(strs);
//        for (String str : res){
//            System.out.println(str);
//        }
        int[] array = new int[]{12,3,2,7,8,12,34,25};
        int dis = amoa.distanceBST(array, 8, 25,34 );
        System.out.println(dis);
    }
}
