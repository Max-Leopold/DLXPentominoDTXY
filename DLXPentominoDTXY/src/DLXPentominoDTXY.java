import java.util.Scanner;

/**
 * Programm zur Errechnung der Möglichkeiten wie ein 6*n Feld mit Dominos, Y, T und X Pentominos gefüllt werden kann
 * @author Nico Köpf, Maximilian Leopold
 */
public class DLXPentominoDTXY {

  int n = 4;
  int[][] matrix;
  DLXNode h;
  DLXNode[] columnHeader;
  int cnt = 0;
  int linecount = 1;

  public DLXPentominoDTXY() {

    Scanner sc = new Scanner(System.in);
    System.out.print("n: ");
    n = sc.nextInt();

    long start = System.currentTimeMillis();

    if(n != 0) {


      matrix = new int[6][n];

      fillMatrix();
      printMatrix();

      h = new DLXNode();
      h.posX = 0;
      h.posY = 0;

      columnHeader = new DLXNode[(6 * n) + 1];
      columnHeader[0] = h;

      genHeader();
      genD();
      genX();
      genY();
      genT();

      search(0);
    }
    System.out.println("\n\nAnzahl der Kombinationen: " + cnt);
    System.out.println("\nTotal Execution Time: " + (System.currentTimeMillis() - start) + " ms");
  }

  public static void main(String[] args) {
    new DLXPentominoDTXY();
  }

  /**
   * Dominos generieren
   */
  private void genD() {

    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < n; x++) {

        if (y + 1 < 6) {
          genRows(new int[] {matrix[y][x], matrix[y + 1][x]});
        }
        if (x + 1 < n) {
          genRows(new int[] {matrix[y][x], matrix[y][x + 1]});
        }

      }
    }

  }

  /**
   * T Pentomino generieren
   */
  private void genT() {
    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < n; x++) {
        if (x + 2 < n && y + 2 < 6) { //T normal
          genRows(new int[] {matrix[y][x], matrix[y][x + 1], matrix[y][x + 2], matrix[y + 1][x + 1], matrix[y + 2][x + 1]});
        }
        if (y + 2 < 6 && x - 2 >= 0) {// T nach rechts gedreht
          genRows(new int[] {matrix[y][x], matrix[y + 1][x - 2], matrix[y + 1][x - 1], matrix[y + 1][x], matrix[y + 2][x]});
        }
        if (y + 2 < 6 && x + 2 < n) {//T nach links gedreht
          genRows(new int[] {matrix[y][x], matrix[y + 1][x], matrix[y + 1][x + 1], matrix[y + 1][x + 2], matrix[y + 2][x]});
        }
        if (y + 2 < 6 && x - 1 >= 0 && x + 1 < n) {//T auf Kopf
          genRows(new int[] {matrix[y][x], matrix[y + 1][x], matrix[y + 2][x], matrix[y + 2][x - 1], matrix[y + 2][x + 1]});
        }
      }
    }
  }

  /**
   * Y Pentomino generieren
   */
  private void genY() {
    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < n; x++) {
        if (y + 3 < 6 && x - 1 >= 0) { //y
          genRows(new int[] {matrix[y][x], matrix[y + 1][x - 1], matrix[y + 1][x], matrix[y + 2][x], matrix[y + 3][x]});
        }
        if (y + 3 < 6 && x + 1 < n) { //y gespiegelt
          genRows(new int[] {matrix[y][x], matrix[y + 1][x], matrix[y + 1][x + 1], matrix[y + 2][x], matrix[y + 3][x]});
        }
        if (y + 1 < 6 && x - 2 >= 0 && x + 1 < n) { //y nach rechts gedreht
          genRows(new int[] {matrix[y][x], matrix[y + 1][x - 2], matrix[y + 1][x - 1], matrix[y + 1][x], matrix[y + 1][x + 1]});
        }
        if (y + 1 < 6 && x - 2 >= 0 && x + 1 < n) { //y nach rechts gedreht/gespiegelt
          genRows(new int[] {matrix[y][x - 2], matrix[y][x - 1], matrix[y][x], matrix[y][x + 1], matrix[y + 1][x]});
        }
        if (y + 1 < 6 && x - 1 >= 0 && x + 2 < n) { //y nach links gedreht
          genRows(new int[] {matrix[y][x - 1], matrix[y][x], matrix[y][x + 1], matrix[y][x + 2], matrix[y + 1][x]});
        }
        if (y + 1 < 6 && x - 1 >= 0 && x + 2 < n) { //y nach links gedreht/gespiegelt
          genRows(new int[] {matrix[y][x], matrix[y + 1][x - 1], matrix[y + 1][x], matrix[y + 1][x + 1], matrix[y + 1][x + 2]});
        }
        if (y + 3 < 6 && x + 1 < n) { //y auf Kopf
          genRows(new int[] {matrix[y][x], matrix[y + 1][x], matrix[y + 2][x], matrix[y + 2][x + 1], matrix[y + 3][x]});
        }
        if (y + 3 < 6 && x - 1 >= 0) { //y auf Kopf/gespiegelt
          genRows(new int[] {matrix[y][x], matrix[y + 1][x], matrix[y + 2][x], matrix[y + 2][x - 1], matrix[y + 3][x]});
        }

      }
    }
  }

  /**
   * X Pentomino generiren
   */
  private void genX() {
    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < n; x++) {

        if (y + 2 < 6 && x + 1 < n && x - 1 >= 0) {
          genRows(new int[] {matrix[y][x], matrix[y + 1][x - 1], matrix[y + 1][x], matrix[y + 1][x + 1], matrix[y + 2][x]});
        }

      }

    }
  }

  /**
   * Füllt die Matrix
   */
  private void fillMatrix() {
    int nmbr = 1;
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < n; j++) {
        matrix[i][j] = nmbr;
        nmbr++;
      }
    }
  }

  /**
   * Gibt die Matrix aus
   */
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
        } else {
          if (matrix[i][j] < 10) {
            System.out.print("   " + matrix[i][j] + " ");
          } else if (matrix[i][j] < 100 && matrix[i][j] >= 10) {
            System.out.print("  " + matrix[i][j] + " ");
          } else {
            System.out.print(" " + matrix[i][j] + " ");
          }

          System.out.print("|");
        }
      }
    }

  }

  /**
   * Generiert die Spalten Header
   */
  public void genHeader() {

    for (int i = 1; i <= 6 * n; i++) { //Alle Header generieren

      DLXNode nextHeader = new DLXNode();

      if ((h.R == h) && (h.L == h)) { //Erste Spalte erstellen

        h.R = nextHeader;
        h.L = nextHeader;

        nextHeader.L = h;
        nextHeader.R = h;
      } else { //Alle anderen Spalten

        DLXNode lastElement = h.L;

        //Verbinden
        h.L = nextHeader;
        nextHeader.R = h;

        nextHeader.L = lastElement;
        lastElement.R = nextHeader;
      }

      nextHeader.posY = 0;
      nextHeader.posX = i;
      columnHeader[i] = nextHeader; //Speichern in Array
    }
  }

  /**
   * Generiert eine Reihe für die Überlagungsmatrix aus einem int Array
   * @param x
   */
  public void genRows(int[] x) {

    //for (int[] x: positions) {

    DLXNode[] tempArray = new DLXNode[x.length];

    for (int i = 0; i < x.length; i++) {
      DLXNode temp = new DLXNode();
      temp.posX = x[i];
      temp.posY = linecount; //Y Position durch linecount festelegen
      temp.C = columnHeader[x[i]]; //Header Zuweisen
      tempArray[i] = temp;
    }

    //Array nach rechts verbinden
    for (int i = 0; i < tempArray.length; i++) {
      if (i == tempArray.length - 1) {
        tempArray[i].R = tempArray[0];
      } else {
        tempArray[i].R = tempArray[i + 1];
      }
    }

    //Array nach links verbinden
    for (int i = 0; i < tempArray.length; i++) {
      if (i == 0) {
        tempArray[i].L = tempArray[tempArray.length - 1];
      } else {
        tempArray[i].L = tempArray[i - 1];
      }
    }

    //Elemente nach oben verbinden
    for (int i = 0; i < tempArray.length; i++) {
      DLXNode head = getHeader(x[i]);
      DLXNode last = head.U;

      if (head.U == head) {
        tempArray[i].U = head;
        tempArray[i].D = head;
        head.U = tempArray[i];
        head.D = tempArray[i];
      } else {

        last.D = tempArray[i];
        tempArray[i].U = last;

        head.U = tempArray[i];
        tempArray[i].D = head;
      }
    }

    linecount++; //Eine Zeile erstellt
  }

  public DLXNode getHeader(int i) {
    return columnHeader[i];
  }


  //-- Methoden von Herr Professor Heinz --

  /**
   * Class DLXNode
   * represents a matrix element of the cover matrix with value 1
   * links go to up down left right neigbors, and column header
   * can also be used as colm header or root of column headers
   * matrix is sparsely coded
   * try to do all operations very efficiently
   * see:
   * http://en.wikipedia.org/wiki/Dancing_Links
   * http://arxiv.org/abs/cs/0011047
   */
  class DLXNode {       // represents 1 element or header
    DLXNode C;           // reference to column-header
    DLXNode L, R, U, D;  // left, right, up, down references

    DLXNode() {
      C = L = R = U = D = this;
    }

    int posX;
    int posY;

    @Override
    public String toString() {
      return "X: " + posX + ", Y: " + posY;
    }

    // supports circular lists
  }

  /**
   * search tries to find and count all complete coverings of the DLX matrix.
   * Is a recursive, depth-first, backtracking algorithm that finds
   * all solutions to the exact cover problem encoded in the DLX matrix.
   * each time all columns are covered, static long cnt is increased
   *
   * @param k: number of level
   */
  public void search(int k) { // finds & counts solutions
    if (h.R == h) {
      cnt++;
      return;
    }     // if empty: count & done
    DLXNode c = h.R;                   // choose next column c
    cover(c);                          // remove c from columns
    for (DLXNode r = c.D; r != c; r = r.D) {  // forall rows with 1 in c
      for (DLXNode j = r.R; j != r; j = j.R) // forall 1-elements in row
      {
        cover(j.C);                    // remove column
      }
      search(k + 1);                    // recursion
      for (DLXNode j = r.L; j != r; j = j.L) // forall 1-elements in row
      {
        uncover(j.C);                  // backtrack: un-remove
      }
    }
    uncover(c);                        // un-remove c to columns
  }

  /**
   * cover "covers" a column c of the DLX matrix
   * column c will no longer be found in the column list
   * rows i with 1 element in column c will no longer be found
   * in other column lists than c
   * so column c and rows i are invisible after execution of cover
   *
   * @param c: header element of column that has to be covered
   */
  public void cover(DLXNode c) { // remove column c
    c.R.L = c.L;                         // remove header
    c.L.R = c.R;                         // .. from row list
    for (DLXNode i = c.D; i != c; i = i.D)      // forall rows with 1
    {
      for (DLXNode j = i.R; i != j; j = j.R) {   // forall elem in row
        j.D.U = j.U;                     // remove row element
        j.U.D = j.D;
        // .. from column list
      }
    }
  }

  /**
   * uncover "uncovers" a column c of the DLX matrix
   * all operations of cover are undone
   * so column c and rows i are visible again after execution of uncover
   *
   * @param c: header element of column that has to be uncovered
   */
  public void uncover(DLXNode c) {//undo remove col c
    for (DLXNode i = c.U; i != c; i = i.U)      // forall rows with 1
    {
      for (DLXNode j = i.L; i != j; j = j.L) {   // forall elem in row
        j.D.U = j;                       // un-remove row elem
        j.U.D = j;
        // .. to column list
      }
    }
    c.R.L = c;                           // un-remove header
    c.L.R = c;                           // .. to row list
  }
}


