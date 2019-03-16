package bekyiu.domain;

import lombok.Data;

import java.util.Date;

@Data
public class LoginTicket
{
    private Long id;

    private Long userId;

    private String ticket;

    private Date expired;

    private Integer status;

}