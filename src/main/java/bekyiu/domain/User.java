package bekyiu.domain;

import lombok.Data;

@Data
public class User
{
    private Long id;

    private String username;

    private String password;

    private String salt;

    private String headUrl;

}