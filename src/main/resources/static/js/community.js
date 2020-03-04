function comment2Target(targetId, type, content) {
    if (!content) {
        alert("回复内容不能为空");
        return;
    }
    $.ajax({
        type: 'post',
        url: '/comment',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (result) {
            if (result.code === 200) {
                window.location.reload();
            } else if (result.code === 2003) {
                var isAccepted = confirm(result.message);
                if (isAccepted) {
                    window.open("https://github.com/login/oauth/authorize?client_id=1d08677f6d9b4d730ad7&redirect_uri=http://unclex.wicp.vip/callback&scope=user&state=1");
                    window.localStorage.setItem("closable", true);
                }
            } else {
                alert(result.message);
            }
        },
        dataType: "json",
        contentType: "application/json"
    })
}

/*
* 提交评论回复
* */
function comment(e) {
    var commentId = e.getAttribute("data");
    var content = $("#input-" + commentId).val();
    comment2Target(commentId, 2, content);
}

/*
* 提交问题回复*/
function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment-content").val();
    comment2Target(questionId, 1, commentContent);
}

/**
 *展开二级评论，并查询数据
 */
function collapseComments(e) {
    var id = e.getAttribute("data");
    var comment = $("#comment-" + id);

    comment.toggleClass("in");
    $(e).toggleClass("comment-toggle");

    if (comment.hasClass("in")) {
        getComment(id);
    }
}

function getComment(id) {
    $.getJSON("/comment/" + id, function (result) {
        $.each(result.data, function (index, comment) {
            var commentItem = $(
                "<div class='media'>\n" +
                "    <div class='media-left'>\n" +
                "        <a href='#'>\n" +
                "            <img class='media-object img-rounded' src=" + comment.user.avatarUrl + " alt=''>\n" +
                "        </a>\n" +
                "    </div>\n" +
                "    <div class='media-body'>\n" +
                "        <h5 class='media-heading'>\n" +
                "            <span>" + comment.user.name + "</span>\n" +
                "        </h5>\n" +
                "        <p>" + comment.content + "</p>\n" +
                "        <span class='pull-right menu'>" + new Date(comment.gmtCreate).format('yyyy-MM-dd hh:mm') + "</span>\n" +
                "    </div>\n" +
                "</div>");
            $("#comment-"+comment.parentId).children(":first").append(commentItem);
        });
    });
}

Date.prototype.format = function(format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};
