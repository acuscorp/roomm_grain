package com.acuscorp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @class public class Main
 * @brief this is an exercise to find how may spot light can be set in a house which is represented as a matrix, the 0 are the empty spaces and
 *  1 are like walls. The spot light moves to x and y direction. I got this algorithm in this way
 *  first: if we have something like this (as a base)
 *      00,01,02
 *      10,11,12
 *      20,21,22
 *
 *      I think the best way to find the best position is checking if in the base the potions 01,10,11,12,21 has zeros (5 available positions  then it will be like this
 *
 *      0,2,0
 *      2,2,2
 *      0,2,0
 *
 *  Second: 4 available positions
 *      1,2,0
 *      0,0,0
 *      0,0,0
 *
 *      the algorithm makes this
 *      1,1,2
 *      0,2,2
 *      0,0,2
 *      but after that it will fil the 0 spaces
 *      1,1,2
 *      2,2,2
 *      0,0,2
 *
 *      then it'll find if there is 3 position available then 2 and at the en 1 position
 *      if you run the code will print the steps.
 */
public class Main {
    private static int xLength;
    private static int yLength;
    private static int[][] file;
    private static StringBuffer sb;
    static List<String[]> myList;


    public static void main(String[] args) {


        int spotCounter = 0;
        int numberHelper = 5;
        try {
            FileReader fr=new FileReader("C:\\Users\\xman_\\IdeaProjects\\GrainChain\\src\\com\\acuscorp\\room.txt");
            BufferedReader br=new BufferedReader(fr);
            String line="";
            try {
                sb = new StringBuffer();
                int[] readFileArray;

                myList = new ArrayList<>();
                List<Integer> numberList = new ArrayList<>();
                while((line=br.readLine())!=null)
                {
                    String[] arrayRows = line.replaceAll("\\s","").split(",");
                    myList.add(arrayRows);
                }
                int ii =myList.get(0).length;
                int jj = myList.size();
                xLength = jj;
                yLength = ii;
                file = new int[ii][jj];
                for (int i  = 0; i<jj;i++) {

                        String[] str = myList.get(i);
                    for (int j  = 0; j<ii;j++) {
                        if(str[j].contains("1")){
                            file[j][i] =1;
                        }
                        else {
                            file[j][i] =0;
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        for (int z = numberHelper; z >0; --z ){
            for (int x = 0; x < xLength; x++ ){
                for (int y = 0; y < yLength; y++ ){
                    if(x<xLength   && y<yLength ){

                            if(check(x,y,z)){
                            spotCounter++;
                            putSSpotLight(x,y);
                            printMatrix();
                            System.out.println(z);

                        }


                    }


                }

            }
        }
        System.out.println("spot number:" + spotCounter);

    }


    private static void printMatrix() {
        for (int i = 0; i < yLength; i++ ){
            for (int j = 0; j < xLength; j++ ) {
                System.out.print(file[i][j] +" , ");
            }
            System.out.println();
        }
    }

    private static void putSSpotLight(int y, int x) {
        boolean xLeft =false;
        boolean xRight =false;
        boolean yUp =false;
        boolean yDown =false;


        for (int i = 0; i < yLength; i++ ) {
            if(x+i<yLength&& !xRight){
                if (file[x+i][y] == 1 ) {
                    xRight=true;
                } else {
                    file[x+i][y] = 2;
                }
            }
            if(x-i>=0&& !xLeft){
                if (file[x-i][y] == 1 ) {
                    xLeft=true;
                } else {
                    file[x-i][y] = 2;
                }
            }

        }


        for (int j = 0; j < xLength; j++ ) {
            if(y+j<xLength && !yDown){
                if (file[x][y+j] == 1 ) {
                    yDown=true;
                } else {
                    file[x][y+j] = 2;
                }
            }
            if(y-j>=0 && !yUp){
                if (file[x][y-j] == 1 ) {
                    yUp=true;
                } else {
                    file[x][y-j] = 2;
                }
            }
        }

    }

    private static boolean check(int x, int y,int count) {
        if(count==1){
            if(file[y][x]==0){
                return true;
            }
        }
        int counter=0;
        for (int i = x-1; i <= x+1; i++ ){
            for (int j = y-1; j <= y+1; j++ ){
                if((i>=0 && j>=0 && i<yLength && j<xLength)&&(i==x-1 && j==y || i==x && j==y-1 ||
                        i==x && j==y+1 || i==x+1 && j==y || x==i)){

                    if(file[i][j]==0){
                        counter++;
                    }

                }
            }
        }
        if (counter==count){
            return true;
        }
        else {
            return false;
        }

    }


}
