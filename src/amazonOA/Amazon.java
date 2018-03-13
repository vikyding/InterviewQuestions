package amazonOA;
import java.util.*;

public class Amazon {

    public List<String> getKDistinctSub(String s, int k) {
        List<String> ans = new ArrayList<String>();
        Set<String> set = new HashSet<>();
        if (s == null || s.length() < k || k == 0) {
            return ans;
        }
        int[] map = new int[128];
        int l = 0, r = 0;
        int count = 0;
        for (; r < s.length(); r++) {
            char ch = s.charAt(r);
            if (map[ch] == 0) {
                count++;
            }
            map[ch]++;
            while ((map[ch] > 1 || count > k) && l < r) {
                char c = s.charAt(l++);
                map[c]--;
                if (map[c] == 0) {
                    count--;
                }
            }
            if (count == k) {
                set.add(s.substring(l, r + 1));
            }
        }
        ans.addAll(set);
        return ans;
    }

    public int[] containsTags(String[] words, String[] tags) {
        int[] res = new int[2];
        if (words == null || words.length < tags.length || tags == null || tags.length == 0) {
            return res;
        }
        HashMap<String, Integer> map = new HashMap<>();
        for (String tag : tags) {
            int count = map.getOrDefault(tag, 0);
            map.put(tag, count + 1);
        }
        int l = 0, r = 0;
        int count = 0;
        int min = Integer.MAX_VALUE;
        for (; r < words.length; r++) {
            if (map.containsKey(words[r])) {
                if (map.get(words[r]) > 0) {
                    count++;
                }
                System.out.println(count);
                map.put(words[r], map.get(words[r]) - 1);
                if (count == tags.length) {

                    while (!map.containsKey(words[l]) || map.get(words[l]) < 0) {
                        if (map.containsKey(words[l])) {
                            map.put(words[l], map.get(words[l]) + 1);
                        }
                        l++;
                    }
                    int len = r - l + 1;
                    if (len < min) {
                        min = len;
                        res[0] = l;
                        res[1] = r;
                    }
                }

            }
        }
        if (min == Integer.MIN_VALUE) return new int[2];
        return res;
    }


    //a string and an integer K, return a list of substring with exactly k length and  k - 1 distinct characters.
    public List<String> getKDiswithOneRepeat(String s, int k){
           List<String> res = new ArrayList<>();
           if (s == null || s.length() < k || k == 0){return res;}
           Set<String> set = new HashSet<>();
           int l = 0, r = 0;
           int dis = 0;
           int repeat = -1;
           int[] map = new int[128];
           for (; r < s.length(); r++){
                char ch = s.charAt(r);
                if (map[ch] == 0){
                    dis++;
                } else if (map[ch] > 0){
                    repeat++;
                }
                map[ch]++;
                while ((repeat > 0 || dis > k - 1) && l < r){
                       char c = s.charAt(l++);
                       if (map[c] > 1){
                           repeat--;
                       }
                       map[c]--;
                       if (map[c] == 0){
                           dis--;
                       }
                }
                if (dis == k - 1 && repeat == 0){
                    String temp = s.substring(l, r + 1);
                    set.add(temp);
                }
           }
           res.addAll(set);
           return res;

    }

    public int[][] getCloseM(int[][] points, int m){
           int[][] res = new int[m][2];
           if (points == null || points.length == 0 || m == 0){
               return res;
           }
           PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a,b) ->
                                       ((a[0]*a[0] + a[1]*a[1]) - (b[0]*b[0] + b[1]*b[1])));
           for (int[] point : points){
                pq.offer(point);
           }
           int count = 0;
           while (count < m && !pq.isEmpty()){
                  int[] p = pq.remove();
                  res[count] = p;
                  count++;
           }
           return res;
    }



    public List<Movie> getNSimilarMovies(Movie movie, int n){
           List<Movie> res = new ArrayList<>();
           if (n == 0){return res;}
           PriorityQueue<Movie> heap = new PriorityQueue<Movie>(n, new Comparator<Movie>() {
               @Override
               public int compare(Movie o1, Movie o2) {
                   return o1.getRate() == o2.getRate() ? 0 : (o1.getRate() > o2.getRate() ? -1 : 1);
               }
           });
           Queue<Movie> queue = new LinkedList<>();
           Set<Movie> set = new HashSet<Movie>();
           set.add(movie);
           queue.offer(movie);
           while (!queue.isEmpty()){
                  Movie m = queue.poll();
                  for (Movie similar : m.getSimiliarMovies()) {
                      if (set.contains(similar)) {
                          continue;
                      }
                      heap.offer(similar);
                      queue.offer(similar);
                      set.add(similar);
                  }
           }

           while (n > 0 && !heap.isEmpty()){
                  res.add(heap.remove());
                  n--;
           }
           return res;
    }

    public int checkWinner(List<List<String>> fruits, List<String> shoppingcart){
           if (shoppingcart == null || shoppingcart.size() == 0){return 1;}
           if (fruits == null || fruits.size() == 0){return 1;}
           int index = 0;
           for (List<String> list : fruits){
                int start = 0;
                while (start < list.size() && list.get(start).equals("anything")){
                       start++;
                }
                if (start == list.size()){continue;}
                boolean flag = true;
                for (; index < shoppingcart.size(); index++){
                     if (shoppingcart.get(index).equals(list.get(start))){
                         int[] res = check(list,shoppingcart, start, index);
                         if (res[0] == 1){
                             index = res[1];
                             flag = false;
                             break;
                         }
                     }
                }
                if (flag){
                    return 0;
                }
           }
           return 1;
    }

    public int[] check(List<String> strs, List<String> cart, int s, int index){
           int[] res = new int[2];
           if (index == cart.size() && s < strs.size()){
               //System.out.println("stop : " + index);
                return res;
           }
           res[0] = 1;
           for(int i= s ; i < strs.size(); i++){
               if (index < cart.size() && (strs.get(i).equals(cart.get(index)) || strs.get(i).equals("anything"))){
                   index++;
               } else {
                   res[0] = 0;
                   break;
               }
           }
           res[1] = index;
           //System.out.println("end : " + index);
           return res;
    }

    class Point{
        int x;
        int y;
        int height;
        public Point(int x, int y, int h){
            this.x = x;
            this.y = y;
            this.height = h;
        }
    }
    public int flatFields(int numRows, int numColumns, List<List<Integer>> fields) {
        if (numRows == 0 || numColumns == 0){return 0;}
        int[][] starts = new int[][]{{0,0}, {0, numColumns - 1}, {numRows - 1, 0},{numRows - 1, numColumns - 1}};
        int minStep = Integer.MAX_VALUE;
        for (int[] ss : starts){
            Point s = new Point(ss[0],ss[1], fields.get(ss[0]).get(ss[1]));
            // List<List<Integer>> copy = new ArrayList<List<Integer>>(fields);
            int steps = findMin(fields, s, numRows, numColumns);
            if (steps != -1){
                //System.out.println(steps);
                minStep = Math.min(minStep, steps);
                System.out.println("min" + minStep);
            }
        }
        if (minStep == Integer.MAX_VALUE){
            return -1;
        }
        return minStep;
    }

    public int findMin(List<List<Integer>> fields, Point from,int numRows, int numColumns){
        PriorityQueue<Point> heap = new PriorityQueue<Point>((a, b)->(a.height - b.height));
        for (int i = 0; i < fields.size(); i++){
            for (int j = 0; j < fields.get(i).size(); j++){
                if (fields.get(i).get(j) > 1){
                    Point p = new Point(i, j, fields.get(i).get(j));
                    heap.offer(p);
                }
            }
        }
        int min = 0;
        while (!heap.isEmpty()){
            Point cur = heap.remove();
            System.out.println(cur.x + "->" + cur.y);
            int step = BFS(fields,from,cur, numRows, numColumns);
            if (step == -1){
                return -1;
            }
            min += step;
            // fields.get(cur.x).set(cur.y, 1);
            from = cur;
        }

        return min;
    }
    int[][] dir = new int[][]{{0,1},{1,0},{0, -1},{-1, 0}};
    public int BFS(List<List<Integer>> fields, Point from, Point to, int m, int n){
        Queue<Point> queue = new LinkedList<Point>();
        int[][] path = new int[m][n];
        queue.offer(from);
        while (!queue.isEmpty()){
            Point p = queue.remove();
            if (p.x == to.x && p.y == to.y){System.out.println("total " + path[p.x][p.y]);return path[p.x][p.y];}
            for (int i = 0; i < 4; i++){
                int x = p.x + dir[i][0];
                int y = p.y + dir[i][1];
                if (x >= 0 && x < m && y >= 0 && y < n){
                    if (fields.get(x).get(y) == 0
                            || fields.get(x).get(y) > fields.get(to.x).get(to.y)){continue;}
                    if (x == from.x && y == from.y){continue;}
                    if (path[x][y] == 0 || path[x][y] > path[p.x][p.y] + 1){
                        //System.out.println("update" + x + " " + y);

                        path[x][y] = path[p.x][p.y] + 1;
                        Point cur = new Point(x, y, fields.get(x).get(y));
                        queue.offer(cur);
                        //System.out.println(path[x][y]);
                    }
                }
            }
        }
        if (path[to.x][to.y] == 0){return -1;}
        return path[to.x][to.y];
    }

    public int golfEvent(List<List<Integer>> forest, int row, int col){
           if (row == 0 || col == 0){
               return 0;
           }
           PriorityQueue<Point> heap = new PriorityQueue<Point>((a,b)->(a.height - b.height));
           for (int i = 0; i < row; i++){
                for (int j = 0; j < col; j++){
                     int height = forest.get(i).get(j);
                     if (height > 1){
                         heap.offer(new Point(i, j, height));
                     }
                }
           }
           int steps = 0;
           Point start  = new Point(0, 0,1);
           while (!heap.isEmpty()){
                  Point cur = heap.remove();
                  int step = BFS_Height(forest, start, cur, row, col);
                  if (step == -1){
                      return -1;
                  }
                  steps += step;
                  steps += cur.height;
                  start = cur;
           }
           return steps;
    }

    public  int BFS_Height(List<List<Integer>> forest, Point from, Point to, int m, int n){
            int[][] path = new int[m][n];
            Queue<Point> queue = new LinkedList<Point>();
            queue.offer(from);
            while (!queue.isEmpty()){
                   Point cur = queue.poll();
                   if (cur.x == to.x && cur.y == to.y){return path[to.x][to.y];}
                   for (int i = 0; i < 4; i++){
                        int x = cur.x + dir[i][0];
                        int y = cur.y + dir[i][1];
                        if (x >= 0 && x < m && y >=0 && y < n && forest.get(x).get(y) > 0){
                            if (x == from.x && y == from.y){continue;}
                            if (path[x][y] == 0 || path[x][y] == path[cur.x][cur.y] + 1){
                                path[x][y] = path[cur.x][cur.y] + 1;
                                queue.offer(new Point(x,y,1));
                            }
                        }
                   }
            }
            if (path[to.x][to.y] == 0){return -1;}
            return path[to.x][to.y];
    }


    public int movieShot(char[] array){
        //List<Integer> ans = new ArrayList<Integer>();
        if (array == null || array.length == 0){
            return 0;
        }
        int[] map = new int[26];
        for (int i = 0; i < array.length; i++){
            map[array[i] - 'a'] = i;
        }
        int count = 0;
        int start = 0;
        int last = 0;
        for (int i= 0; i < array.length; i++){
            last = Math.max(last, map[array[i] - 'a']);
            if (last == i){
                //ans.add(last - start + 1);
                count++;
                start = last + 1;
            }
        }
        //return ans;
        return  count;
    }

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<Integer>();
        if (s == null || s.length() == 0 || p == null || p.length() == 0 || s.length() < p.length()){
            return res;
        }
        int[] cache = new int[26];
        for (char ch : p.toCharArray()){
            cache[ch - 'a']++;
        }
        for (int i = 0; i <= s.length() - p.length(); i++){
            char ch = s.charAt(i);
            if (cache[ch - 'a'] > 0){
                int[] map = new int[26];
                for (int j = 0; j < p.length(); j++){
                    map[s.charAt(i + j) - 'a']++;
                }
                if (compare(map,cache)){
                    res.add(i);
                }
            }
        }
        return res;
    }

    public boolean compare(int[] map, int[] cache){
        for (int i = 0; i < 26; i++){
            if (map[i] < cache[i]){
                return false;
            }
        }
        return true;
    }

    // silding window
    public List<Integer> findAnagrams_sliding(String s, String p) {
        List<Integer> res = new ArrayList<Integer>();
        if (s == null || s.length() == 0 || p == null || p.length() == 0 || s.length() < p.length()){
            return res;
        }
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : p.toCharArray()){
            int count = map.getOrDefault(ch, 0);
            map.put(ch, count + 1);
        }
        int l = 0;
        int e = 0;
        int counter = map.size();
        while (e < s.length()){
            char ch = s.charAt(e);
            if (map.containsKey(ch)){
                int c = map.get(ch);
                c--;
                if (c == 0){
                    counter--;
                }
                map.put(ch, c);
            }
            e++;
            while (counter == 0){
                char temp = s.charAt(l);
                if (map.containsKey(temp)){
                    map.put(temp, map.get(temp) + 1);
                    if (map.get(temp) > 0){counter++;}
                }
                if (e - l == p.length()){
                    res.add(l);
                }
                l++;
            }
        }


        return res;
    }

    class UnionFind{
          Map<String, String> father;
          int count;

          public UnionFind(Map<String, String> map){
                 father = map;
                 count = map.size();
          }

          public String find(String p){
                 System.out.println("find father of "+ p);
                 while (!father.get(p).equals(p)){
                         father.put(p, father.get(father.get(p)));
                         p = father.get(p);
                 }
                 System.out.println("is " + p);
                 return p;
          }

          public boolean isConnect(String p, String q){
                 String fap = find(p);
                 String faq = find(q);
                 if (!fap.equals(faq)){
                     return false;
                 }
                 return true;
          }

          public void union(String p, String q){
                 String fp = find(p);
                 String fq = find(q);
                 if (!fp.equals(fq)){
                      if (fp.compareTo(fq) < 0){
                          update(q, fp);
                      } else {
                          update(p, fq);
                      }
                      count--;
                 }
          }

          public void update(String p, String fa){
                 while (!father.get(p).equals(fa)){
                        String temp = father.get(p);
                        father.put(p, fa);
                        p = temp;
                 }
          }

    }

    public List<String> itemAssociation(String[][] associations){
           if (associations == null || associations.length == 0){
               return new ArrayList<>();
           }
           Map<String, String> map = new HashMap<>();
           for (String[] group : associations){
                map.putIfAbsent(group[0],group[0]);
                map.putIfAbsent(group[1],group[1]);
           }
           UnionFind uf = new UnionFind(map);
           for (String[] group : associations){
                uf.union(group[0], group[1]);
           }
           map = uf.father;
           Map<String, Integer> counts = new HashMap<>();
           Map<String, List<String>> store = new HashMap<>();
           for (Map.Entry<String, String> entry : map.entrySet()){
                String fa = uf.find(entry.getKey());
                System.out.println("find father of "+entry.getKey() + " is " + fa);
                if (counts.containsKey(fa)){
                    counts.put(fa, counts.get(fa) + 1);
                    store.get(fa).add(entry.getKey());
                } else {
                    counts.put(fa, 1);
                    List<String> list = new ArrayList<>();
                    list.add(fa);
                    store.put(fa,list);
                }
           }
           int max = 0;
           String smaller = "";
           for (Map.Entry<String, Integer> entry : counts.entrySet()){
                if (entry.getValue() > max){
                    max = entry.getValue();
                    smaller = entry.getKey();
                } else if (entry.getValue() == max){
                    smaller = smaller.compareTo(entry.getKey()) < 0? smaller : entry.getKey();
                }
           }
           return store.get(smaller);

    }

    public boolean isValid(String s) {
        if (s == null | s.length() == 0){return true;}
        if (s.length() %2 != 0){return false;}
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            if (ch == '{' || ch == '[' || ch == '('){
                stack.push(ch);
            } else {
                switch (ch) {
                    case '}' : if (stack.isEmpty() || stack.pop() != '{'){return false;}break;
                    case ')' : if (stack.isEmpty() || stack.pop() != '('){return false;}break;
                    case ']' : if (stack.isEmpty() || stack.pop() != '['){return false;}break;
                }
            }
        }
        return stack.isEmpty();
    }

    class TreeNode{
          int val;
          TreeNode left;
          TreeNode right;
          int count;
          public TreeNode(int v){
                 this.val = v;
                 count = 1;
          }
    }


    public int distanceBST(int[] array, int n, int p, int q){
           if (array == null || n == 0 || p == 0 || q == 0){return 0;}
           TreeNode root = new TreeNode(array[0]);
           for (int i = 1; i < array.length; i++){
                root = insert(root, array[i]);
           }
           TreeNode LCA = findCommonAncestor(root, p,q);
           int lcaheight = depth(root, LCA.val);
           int pheight = depth(root, p);
           int qheight = depth(root, q);
           if (pheight == -1 || qheight == -1){return -1;}
           return pheight + qheight - 2 * lcaheight;
    }

    public TreeNode insert(TreeNode root, int val){
           if (root == null) {return new TreeNode(val);}
           if (val < root.val){
               root.left = insert(root.left, val);
           } else if (val > root.val) {
               root.right = insert(root.right, val);
           }else {
               root.count += 1;
           }
           return root;
    }

    public TreeNode findCommonAncestor(TreeNode root, int p , int q){
           if (root == null){return root;}
           if (root.val > p && root.val > q){
               return findCommonAncestor(root.left, p, q);
           }
           if (root.val < p && root.val < q){
               return findCommonAncestor(root.right, p, q);
           }
           return root;
    }

    public int depth(TreeNode root, int p){
           if (root == null){return -1;}
           if (p == root.val){return 0;}
           int next = (p < root.val) ? depth(root.left, p) : depth(root.right, p);
           if (next != -1){
               return next + 1;
           }
           return -1;
    }

    public int calPoints(String[] ops) {
        if (ops == null || ops.length == 0){return 0;}
        Deque<Integer> deque = new LinkedList<Integer>();
        int total = 0;
        for (int i = 0; i < ops.length; i++){
            if (ops[i].equals("+")){
                int last = deque.isEmpty() ? 0 : deque.removeLast();
                int secLast = deque.isEmpty() ? 0 : deque.removeLast();
                int sum = last + secLast;
                total += sum;
                deque.addLast(secLast);
                deque.addLast(last);
                deque.addLast(sum);
            } else if (ops[i].equals("X")){
                int last = deque.isEmpty() ? 0 : deque.removeLast();
                int sum = last * 2;
                total += sum;
                deque.addLast(last);
                deque.addLast(sum);
            } else if (ops[i].equals("Z") ){
                int last = deque.isEmpty() ? 0 : deque.removeLast();
                total -= last;
            } else {
                int sum = Integer.parseInt(ops[i]);
                total += sum;
                deque.addLast(sum);
            }
        }
        return total;
    }

    public int strStr(String haystack, String needle) {
        if (haystack == null || haystack.length() == 0){
            return (needle == null || needle.length() == 0) ? 0 : -1;
        }
        if (needle == null || needle.length() == 0){
            return 0;
        }
        int len = needle.length();
        for (int i = 0; i <= haystack.length() - len; i++){
            if (haystack.charAt(i) == needle.charAt(0)){
                int j = 0;
                for (;  j < len; j++){
                    if (haystack.charAt(i + j) != needle.charAt(j)){
                        break;
                    }
                }
                if (j == len){return i;}
            }
        }
        return -1;
    }

}