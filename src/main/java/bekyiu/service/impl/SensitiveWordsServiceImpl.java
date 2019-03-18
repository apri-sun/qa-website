package bekyiu.service.impl;

import bekyiu.service.ISensitiveWordsService;
import bekyiu.util.Trie;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
//InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，
//凡是继承该接口的类，在初始化bean的时候会执行该方法
public class SensitiveWordsServiceImpl implements ISensitiveWordsService, InitializingBean
{
    private Trie trie = new Trie();

    //在初始化bean时构建trie
    @Override
    public void afterPropertiesSet() throws Exception
    {
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("sensitive_words.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String word;
        try
        {
            while ((word = bufferedReader.readLine()) != null)
            {
                System.out.println("word = " + word);
                trie.add(word);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (bufferedReader != null)
                {
                    bufferedReader.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    private static final String REPLACEMENT = "***";

    public String filter(String text)
    {
        if (text == null)
        {
            return "";
        }
        int begin = 0;
        int cur = 0; //用cur指向的字符去字典树中查
        Trie.Node node = trie.getRoot();
        StringBuilder result = new StringBuilder();
        while (begin < text.trim().length())
        {
            Character ch = text.charAt(cur);
            if (node.getNext().containsKey(ch))
            {
                cur++;
                node = node.getNext().get(ch);
                if(node.isWord())
                {
                    result.append(REPLACEMENT);
                    begin = cur;
                    node = trie.getRoot();
                    continue;
                }
            }
            else
            {
                //如果当前字符从字典树中查不出来要要分两种情况
                //1:前面已经匹配上了一部分, 只是当前字符没匹配上
                if(node != trie.getRoot()) //此时node一定是往下移动过得
                {
                    result.append(text, begin, cur + 1);
                    cur++;
                    begin = cur;
                    node = trie.getRoot();
                }
                else //2: 一直都没有匹配上
                {
                    //result.append(text.substring(begin, cur + 1));
                    result.append(text, begin, cur + 1);
                    begin++;
                    cur = begin;
                }

            }
        }
        return result.toString();
    }

}
