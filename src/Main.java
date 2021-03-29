import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
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
    public static FileOutputStream fos;

    static {
        try {
            fos = new FileOutputStream("output.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    Main() throws IOException {
////        while(scan.hasNext()){
////            String data = scan.nextLine();
////            System.out.println(data);
////            System.out.println("Original Input Data : " + data);
////        }
//
//        String data = "Was it a rat I saw?\n";
//
//        String encodeData = encoding(data);
//        String division = "****"+"\n";
//        fos.write(division.getBytes());
//        String bits = String.valueOf(bitwight)+"\n";
//        fos.write(bits.getBytes());
//
//        for(int i=0; i<encodeData.length(); i+=8){
//            String singleByte = encodeData.substring(i,i+8);
//            int value = Integer.parseInt(singleByte,2);
//            fos.write(value);
//        }
//        fos.write(encodeData.getBytes());
//
//        fos.close();
//
//        System.out.println("");
//        System.out.println(bitwight);
//
//    }

    Main() throws IOException {
        Path path = Paths.get("/Users/sungjin.spencerkwon/IdeaProjects/Huffman Project/src/palindrome.txt");

        String data = Files.readString(path, StandardCharsets.ISO_8859_1);
        String code = encoding(data);
        String division = "****"+"\n";
        fos.write(division.getBytes());
        String bits = String.valueOf(bitwight)+"\n";
        fos.write(bits.getBytes());

//        for(int i=0; i<code.length(); i+=8){
//            String singleByte = code.substring(i,i+8);
//            int value = Integer.parseInt(singleByte,2);
//            fos.write(value);
//        }
        fos.write(code.getBytes());

        fos.close();

        System.out.println("");
        System.out.println(bitwight);


    }


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

    public static String encoding(String data) throws IOException {
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

    public static void setPrefixCode(Node n, String code) throws IOException {
        if (n==null) return;

        if(n.data != '-' && n.left== null && n.right == null) {
            prefixCodeTable.put(n.data, code);
//            System.out.println("- "+n.data + "("+n.freq + ") = " + code +" "+(code.length()* n.freq));
//            System.out.println(code + " "+ n.data);
//            String str = "- "+n.data + "("+n.freq + ") = " + code +" "+(code.length()* n.freq)+"\n";
            String str = code+" "+n.data+"\n";

            fos.write(str.getBytes());

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
