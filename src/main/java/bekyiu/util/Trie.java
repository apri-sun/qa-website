package bekyiu.util;

import lombok.Getter;

import java.util.TreeMap;

public class Trie
{
    public class Node
    {
        @Getter
        public TreeMap<Character, Node> next;
        @Getter
        public boolean isWord;

        public Node(boolean isWord)
        {
            this.isWord = isWord;
            next = new TreeMap<>();
        }
        public Node()
        {
            this(false);
        }
    }
    private Node root;
    private int size;//trie中存储的单词数量

    public Node getRoot()
    {
        return root;
    }
    public int getSize()
    {
        return size;
    }
    public Trie()
    {
        root = new Node();
        size = 0;
    }
    //向trie中添加一个新单词 word
    public void add(String word)
    {
        Node cur = root;
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
            {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        //有可能这个要添加的单词是已经存在的,所以要先判断isword,size才能++
        if(!cur.isWord)
        {
            cur.isWord = true;
            size++;
        }
    }

    public boolean contains(String word)
    {
        Node cur = root;
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
            {
                return false;
            }
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }
    public boolean isPrefix(String prefix)
    {
        Node cur = root;
        for(int i = 0; i < prefix.length(); i++)
        {
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null)
            {
                return false;
            }
            cur = cur.next.get(c);
        }
        return true;
    }
    public boolean delete(String word)
    {
        Node cur = root;

        char c = word.charAt(0);
        if(cur.next.get(c) == null)
        {
            return false;
        }
        cur = cur.next.get(c);
        delete(cur, word.substring(1));
        return true;
    }
    //删除以node 为根的树  这个返回值表示是否真的移除  只改变isWord不算移除
    private boolean delete(Node node, String word)
    {
        if(word.equals(""))
        {
            if(node.next.keySet().isEmpty())
            {
                return true;
            }
            else
            {
                node.isWord = false;
                return false;
            }
        }
        else
        {
            char c = word.charAt(0);
            if(node.next.containsKey(c))
            {
                if(delete(node.next.get(c), word.substring(1)))
                {
                    node.next.put(c, null);
                    if(node.next.keySet().isEmpty())
                    {
                        return true;
                    }
                    return false;
                }// else do nothing
            }
            return false;
        }
    }
}
