$(function ()
{
    $("#ask #release").click(function ()
    {
        var title = $("#title").val()
        var content = $("#content").val()
        console.log(title, content)
        $.post("/question/add",{"title":title, "content":content},function(backData){
            if(backData.code == 0)
            {
                window.location.reload()
            }
            else
            {
                window.location.href = "/loginToHtml"
            }
        }, "json");
    })
    $("#msg #send").click(function ()
    {
        var toName = $("#toName").val()
        var content = $("#message").val()
        console.log(toName)
        console.log(content)
        $.post("/msg/add",{"toName":toName, "content":content},function(backData){
            if(backData.code == 0)
            {
                window.location.reload()
            }
            else
            {
                window.location.href = "/loginToHtml"
            }
        }, "json");
    })

    $("#follow").click(function()
    {
        var userId = $("#thisUserId").val()
        //需要关注就发关注的ajax, 需要取关就发取关的ajax
        if($("#follow").hasClass('zg-btn-follow'))
        {
            $.post("/followUser", {"userId" : userId}, function (backData)
            {
                if(backData.code == 0)
                {
                    $("#follow").removeClass("zg-btn-follow").addClass("zg-btn-unfollow").text("取消关注")
                    $("#followers").text(backData.followerCount + ' 粉丝')
                }
                else
                {
                    alert("出现异常!")
                }
            }, "json")
        }
        else
        {
            $.post("/unfollowUser", {"userId" : userId}, function (backData)
            {
                if(backData.code == 0)
                {
                    $("#follow").removeClass("zg-btn-unfollow").addClass("zg-btn-follow").text("关注ta")
                    $("#followers").text(backData.followerCount + ' 粉丝')
                }
                else
                {
                    alert("出现异常!")
                }
            }, "json")
        }

    })
})
