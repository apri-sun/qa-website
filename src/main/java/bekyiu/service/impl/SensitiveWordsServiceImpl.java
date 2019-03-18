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
    public void a()
    {

    }


}
