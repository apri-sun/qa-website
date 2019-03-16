package bekyiu.mapper;

import bekyiu.domain.LoginTicket;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LoginTicketMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(LoginTicket ticket);

    LoginTicket selectByPrimaryKey(Long id);

    List<LoginTicket> selectAll();

    int updateByPrimaryKey(LoginTicket ticket);

    LoginTicket selectByTicket(String ticket);

    void updateStatusByTicket(@Param("ticket") String ticket, @Param("status") Integer status);
}
