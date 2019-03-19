package bekyiu.domain;

import lombok.Data;

@Data
public class User
{
    public static final Long ANONYMOUS_USER_ID = 1L;

    private Long id;

    private String username;

    private String password;

    private String salt;

    private String headUrl;

}