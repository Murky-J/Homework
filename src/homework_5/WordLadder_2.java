package homework_5;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class WordLadder_2 {

    private static String[] words = getWords("words5.txt");

    private static HashMap<String , Integer> path = new HashMap<String, Integer>();

    private static void bfs(String start, String end) {
        BQueue queue = new BQueue();
        queue.enqueue(start);
        path.put(start, 0);
        String curr;

        while (!queue.empty()) {
            curr = (String) queue.dequeue();
            if (curr == end)
                continue;

            for (int i = 0; i < curr.length(); i++) {
                char[] currArray = curr.toCharArray();
                for (char ch = 'a'; ch <='z'; ch++) {
                    if (ch == currArray[i])
                        continue;
                    currArray[i] = ch;
                    String newWord = new String(currArray);
                    if (newWord.equals(end) || isContained(newWord)) {
                        if (path.get(newWord) == null) {
                            int depth = (int) path.get(curr);
                            path.put(newWord, depth + 1);
                            queue.enqueue(newWord);
                        }
                    }
                }
            }
        }
    }

    private static void dfs(String start, String end, ArrayList<String> pathArray, ArrayList<ArrayList<String>> result) {
        if (start.equals(end) == true) {
            pathArray .add(start);
            Collections.reverse(pathArray); //  将 end -> start 反转回 start -> end
            result.add(pathArray);
            return;
        }

        if (path.get(start) == null) {
//            System.out.println("No such Chain.");
            return;
        }
        pathArray.add(start);
        int nextDepth = (int) path.get(start) - 1;
        for (int i = 0; i < start.length(); i++) {
            char[] startArray = start.toCharArray();
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (ch == startArray[i]) {
                    continue;
                }
                startArray[i] = ch;
                String newWord = new String(startArray);
                if (path.get(newWord) != null && path.get(newWord) == nextDepth) {
                    ArrayList<String> newPathArray = new ArrayList<>(pathArray);
                    dfs(newWord, end, newPathArray, result);
                }
            }
        }
    }

    private static ArrayList<ArrayList<String>> wordLadder(String start, String end) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        ArrayList<String> pathArray = new ArrayList<String>();
        if (start == null || end == null) {
            return result;
        }
        bfs(start, end);
        dfs(end,start,pathArray, result);
        return result;
    }

    private static boolean isContained(String newWord) {
        for (int i = 0; i < words.length; i++)
            if (words[i].equals(newWord))
                return true;
        return false;
    }

    private static String[] getWords(String fileName) {

        String[] words = new String[2415];
        int count = 0;
        File file = new File(fileName);
        BufferedReader reader = null;
        String temp = new String();

        try {
            reader = new BufferedReader(new FileReader(file));

            while ((temp = reader.readLine()) != null) {
                words[count++] = temp;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return words;
    }

    public static void main(String[] args) {
//        ArrayList<ArrayList<String>> result = wordLadder("acorn", "blade");
//        for (ArrayList<String> arr : result)
//            System.out.println(arr);

        ArrayList<ArrayList<String>> tempResult = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                tempResult = wordLadder(words[i], words[j]);
                for (ArrayList<String> arr : tempResult) {
                    if (arr == null)
                        continue;
                    System.out.println(arr);
                }
            }
        }
    }
}
