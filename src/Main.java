import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;
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
class Node implements Comparable<Node>{
    char data;
    int freq;
    Node left, right;

    Node(){}
    Node(char data, int frequency){
        this.data = data;
        this.freq = frequency;
    }

    @Override
    public int compareTo(Node node) {
        return freq - node.freq;
    }
}


public class Main {

    private static Scanner scan = new Scanner(System.in);
    private static Map<Character, String> prefixCodeTable = new HashMap<>();
    public static int bitwight = 0;

    Main() throws IOException {
        String path = "/Users/sungjin.spencerkwon/Desktop/univ/2021 Winter Semester/" +
                "CS 301/Final project referrence/data/plaintext/palindrome.txt";
        File file = new File(path);

        BufferedReader br =new BufferedReader(new FileReader(file));
        String line;
        String encodeData = null;
        while((line=br.readLine())!=null) {
            encodeData = encoding(line);
        }
        System.out.println("****");
        System.out.println(bitwight);
    }

//    Main(){
//        while(scan.hasNext()){
//            String data = scan.nextLine();
//            System.out.println(data);
//            System.out.println("Original Input Data : " + data);
//        }
//
//        String encodeData = encoding(data);
//        System.out.println("Encoded Data : " + encodeData);
//
//    }

    public static Node buildTree(PriorityQueue<Node> priQue) {
        if(priQue.size() == 1) {
            return priQue.poll();
        }else {
            Node leftNode = priQue.poll();
            Node rightNode = priQue.poll();
            Node sumNode = new Node();

            if(leftNode !=null && rightNode!=null){
                sumNode.data = '-';
                sumNode.freq = leftNode.freq + rightNode.freq;
                sumNode.left = leftNode;
                sumNode.right = rightNode;
            }
            priQue.offer(sumNode);
            return buildTree(priQue);

        }
    }

    public static String encoding(String data) {
        Map<Character, Integer> charFreq = new HashMap<>();
        for(char c:data.toCharArray()) {
            if(!charFreq.containsKey(c)) {
                charFreq.put(c,1);
            }else {
                int no = charFreq.get(c);
                charFreq.put(c, ++no);
            }
        }
//        System.out.println("Frequency by Character : " + charFreq);

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        Set<Character> ketSet = charFreq.keySet();
        for(char c:ketSet) {
            Node node = new Node(c, charFreq.get(c));
            priorityQueue.offer(node);
        }
        Node rootNode = buildTree(priorityQueue);
        setPrefixCode(rootNode, "");

        StringBuilder sb = new StringBuilder();
        for(char c:data.toCharArray()) {
            sb.append(prefixCodeTable.get(c));
        }
        return sb.toString();
    }

    public static void setPrefixCode(Node n, String code) {
        if (n==null) return;

        if(n.data != '-' && n.left== null && n.right == null) {
            prefixCodeTable.put(n.data, code);
//            System.out.println("- "+n.data + "("+n.freq + ") = " + code);
            System.out.println(code + " "+ n.data + " "+ "Frequency : "+n.freq + " "+ code.length());

            int bit = code.length()*n.freq;
            bitwight = bitwight + bit;
        }else {
            setPrefixCode(n.left, code+'0');
            setPrefixCode(n.right, code+'1');
        }

    }

    public static <K, V> Stream<K> getKeysByValue(Map<K, V> map, V value){
        return map.
                entrySet().
                stream().
                filter(entry -> value.equals(entry.getValue())).
                map(Map.Entry::getKey);
    }

    public static void main(String[] args) throws Exception{
//        String filename = args[0];// input 파일 들고 오기 위핸서 넣은거야!!
//        new Main(filename);

        new Main();
    }

}
