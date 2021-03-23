import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
/**
 * MESSAGE:
 * 성준아! 이건 내가 임의로 공부하려고 배껴온거야 ㅋㅋ 그래서 아직 적용인 안된게 많다ㅜㅜ
 * 일단 더 공부해보고 이번주까지 호프만 트리완성할게!
 *
 */



/**
 * Final Project
 * By.Sungjin Kwon, Sungjoon Ha
 *
 *
 */
// 호프만을 구현하기 위한 노드
class Node{
    int item;
    char c;
    Node left;
    Node right;
}

class Comparing implements Comparator<Node> {
    @Override
    public int compare(Node x, Node y) {
        return x.item - y.item;
    }
}

public class Main {

//    Main(String filename) throws FileNotFoundException {
//        File f = new File(filename);
//        Scanner scan = new Scanner(f);
//
//    }

    Main(){

        int n = 4;
        char[] arr = {'A', 'B', 'C', 'D', 'E'};
        int[] freq = {5, 1, 6, 3};

        PriorityQueue<Node> q = new PriorityQueue<Node>(n, new Comparing());
        for(int i=0; i<n; i++) {
            Node nd = new Node();
            nd.c = arr[i];
            nd.item = freq[i];

            nd.left = null;
            nd.right = null;
            q.add(nd);
        }

        Node root  = null;

        while(q.size()>1) {
            Node x = q.peek();
            q.poll();

            Node y = q.peek();
            q.poll();

            Node f = new Node();
            f.item = x.item + y.item;
            f.c='-';
            f.left = x;
            f.right = y;
            root = f;
            q.add(f);
        }
        System.out.println("Char    |    Code");
        System.out.println("-----------------");
        code(root, "");

    }

    public static void code(Node root, String str) {
        if(root.left ==null && root.right ==null && Character.isLetter(root.c)) {
            System.out.println(root.c + "   |   " + str);
            return;
        }
        code(root.left, str + "0");
        code(root.right, str + "1");
    }

    public static void main(String[] args) throws Exception{
//	    String filename = args[0]; // input 파일 들고 오기 위핸서 넣은거야!!
//        new Main(filename);

        new Main();

    }
}
