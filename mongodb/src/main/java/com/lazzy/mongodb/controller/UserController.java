package com.lazzy.mongodb.controller;

import com.lazzy.base.sdk.LogExecute;
import com.lazzy.mongodb.controller.vo.Response;
import com.lazzy.mongodb.dao.UserDao;
import com.lazzy.mongodb.entity.Comment;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 评论接口信息
 */
@RestController
@RequestMapping("user")
public class UserController {
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping("comment")
    public Response<?> add(@RequestBody Comment comment) {
        comment.setLocalTime(getLocalTime());
        userDao.insertComment(comment);
        return Response.success();
    }

    @PutMapping("comment")
    public Response<?> update(@RequestBody Comment comment) {
        updateReply(comment);
        userDao.updateComment(comment);
        return Response.success();
    }

    @GetMapping("comment")
    @LogExecute
    public Response<List<Comment>> query() {
        return Response.success(userDao.findAll());
    }

    /**
     * 获取服务器时间
     * @return 时间字符串
     */
    private String getLocalTime() {
        /** java 8 时间api 获取当前时间：2024-05-05T14:30:15.123456789 */
        LocalDateTime dt = LocalDateTime.now();

        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化日期和时间
        return dt.format(formatter);
    }

    /**
     * 更新回复时间
     * @param comment 评论对象
     */
    private void updateReply(Comment comment) {
        comment.getReplies().forEach(r -> {
            Comment.Reply reply = userDao.findReplyByReplyId(r.getReplyId());
            if (!ObjectUtils.isEmpty(reply)) {
                r.setLocalTime(reply.getLocalTime());
            } else {
                r.setLocalTime(getLocalTime());
            }
        });

    }
}
