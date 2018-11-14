package uebung6;

import java.util.Arrays;
import java.util.LinkedList;

public class MatrixGen {

  int n = 20;
  int[][] matrix;
  LinkedList<int[]> positions;

  public MatrixGen() {

    matrix = new int[6][n];
    positions = new LinkedList<int[]>();

    fillMatrix();
    printMatrix();

    genD();
    genX();
    genY();
    genT();
    printPositions();
    System.out.println("\nAnzahl möglicher Positionen: " + positions.size());
  }

  public static void main(String[] args) {
    new MatrixGen();
  }

  private void genD() {

    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < n; x++) {

        if(y + 1 < 6) {
          positions.add(new int[] {matrix[y][x], matrix[y + 1][x] });
        }
        if (x + 1 < n){
          positions.add(new int[] {matrix[y][x], matrix[y][x + 1] });
        }

      }
    }

  }


  private void genT(){
    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < n; x++) {
        if(x + 2 < n && y + 2 < 6){ //T normal
          positions.add(new int[] {matrix[y][x], matrix[y][x + 1], matrix[y][x + 2], matrix[y + 1][x + 1], matrix[y + 2][x + 1] });
        }
        if(y + 2 < 6 && x - 2 >= 0){// T nach rechts gedreht
          positions.add(new int[] {matrix[y][x], matrix[y + 1][x - 2], matrix[y + 1][x - 1], matrix[y + 1][x], matrix[y + 2][x] });
        }
        if(y + 2 < 6 && x + 2 < n){//T nach links gedreht
          positions.add(new int[] {matrix[y][x], matrix[y + 1][x], matrix[y + 1][x + 1], matrix[y + 1][x + 2], matrix[y + 2][x] });
        }
        if(y + 2 < 6 && x - 1 >= 0 && x + 1 < n){//T auf Kopf
          positions.add(new int[] {matrix[y][x], matrix[y + 1][x], matrix[y + 2][x], matrix[y + 2][x - 1], matrix[y + 2][x + 1] });
        }
      }
    }
  }

  private void genY(){
    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < n; x++) {
        if(y + 3 < 6 && x - 1 >= 0){ //y
          positions.add(new int[] {matrix[y][x], matrix[y + 1][x - 1], matrix[y + 1][x], matrix[y + 2][x], matrix[y + 3][x] });
        }
        if(y + 3 < 6 && x + 1  < n){ //y gespiegelt
          positions.add(new int[] {matrix[y][x], matrix[y + 1][x], matrix[y + 1][x + 1], matrix[y + 2][x], matrix[y + 3][x] });
        }
        if(y + 1 < 6 && x - 2 >= 0 && x + 1 < n){ //y nach rechts gedreht
          positions.add(new int[] {matrix[y][x], matrix[y + 1][x - 2], matrix[y + 1][x - 1], matrix[y + 1][x], matrix[y + 1][x + 1] });
        }
        if(y + 1 < 6 && x - 2 >= 0 && x + 1 < n){ //y nach rechts gedreht/gespiegelt
          positions.add(new int[] {matrix[y][x - 2], matrix[y][x - 1], matrix[y][x], matrix[y][x + 1], matrix[y + 1][x] });
        }
        if(y + 1 < 6 && x - 1 >= 0 && x + 2 < n){ //y nach links gedreht
          positions.add(new int[] {matrix[y][x - 1], matrix[y][x], matrix[y][x + 1], matrix[y][x + 2], matrix[y + 1][x] });
        }
        if(y + 1 < 6 && x - 1 >= 0 && x + 2 < n){ //y nach links gedreht/gespiegelt
          positions.add(new int[] {matrix[y][x], matrix[y + 1][x - 1], matrix[y + 1][x], matrix[y + 1][x + 1], matrix[y + 1][x + 2] });
        }
        if(y + 3 < 6 && x + 1 < n){ //y auf Kopf
          positions.add(new int[] {matrix[y][x], matrix[y + 1][x], matrix[y + 2][x], matrix[y + 2][x + 1], matrix[y + 3][x] });
        }
        if(y + 3 < 6 && x - 1  >= 0){ //y auf Kopf/gespiegelt
          positions.add(new int[] {matrix[y][x], matrix[y + 1][x], matrix[y + 2][x], matrix[y + 2][x - 1], matrix[y + 3][x] });
        }

      }
    }
  }

  private void genX(){
    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < n; x++) {

        if (y + 2 < 6 && x + 1 < n && x - 1 >= 0){
          positions.add(new int[] {matrix[y][x], matrix[y + 1][x - 1], matrix[y + 1][x], matrix[y + 1][x + 1], matrix[y + 2][x] });
        }

      }

    }
  }

  private void printPositions(){
    System.out.println();
    for (int[] pos : positions) {
      System.out.println(Arrays.toString(pos));

    }
  }

  private void fillMatrix() {
    int nmbr = 1;
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < n; j++) {
        matrix[i][j] = nmbr;
        nmbr++;
      }
    }
  }

  private void printMatrix() {

    for (int i = 0; i < 6; i++) {
      System.out.print("\n|");
      for (int j = 0; j < n; j++) {

        if (6 * n < 100) {
          if (matrix[i][j] < 10) {
            System.out.print("  " + matrix[i][j] + " ");
          } else {
            System.out.print(" " + matrix[i][j] + " ");
          }

          System.out.print("|");
        }
        else{
          if (matrix[i][j] < 10) {
            System.out.print("   " + matrix[i][j] + " ");
          } else if(matrix[i][j] < 100 && matrix[i][j] >= 10) {
            System.out.print("  " + matrix[i][j] + " ");
          }else{
            System.out.print(" " + matrix[i][j] + " ");
          }

          System.out.print("|");
        }
      }
    }

  }
}


