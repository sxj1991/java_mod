package com.lazzy.mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 指定要对应的文档名(表名）
 * comment 评论表
 * mongodb 映射实体类
 * json结构
 * {
 *     "code": 200,
 *     "data": [
 *         {
 *             "id": 1,
 *             "username": "li",
 *             "localTime": "2024-05-05 16:02:01",
 *             "content": "确实好笑的",
 *             "replies": null
 *         },
 *         {
 *             "id": 2,
 *             "username": "zhang",
 *             "localTime": "2024-05-05 16:26:36",
 *             "content": "视频拍的不错",
 *             "replies": [
 *                 {
 *                     "replyId": 1,
 *                     "username": "lisi",
 *                     "localTime": "2024-05-05 16:38:11",
 *                     "content": "确实还可以",
 *                     "sideReplyId": null
 *                 },
 *                 {
 *                     "replyId": 2,
 *                     "username": "wang",
 *                     "localTime": "2024-05-05 16:48:12",
 *                     "content": "感觉很一般啊",
 *                     "sideReplyId": 1
 *                 }
 *             ]
 *         }
 *     ],
 *     "msg": "success"
 * }
 */
@Data
@Document(collection = "comment")
public class Comment {

    /*** 自定义mongo主键 加此注解可自定义主键类型以及自定义自增规则
     *  若不加 插入数据数会默认生成 ObjectId 类型的_id 字段
     *  org.springframework.data.annotation.Id 包下
     *  mongo库主键字段还是为_id (本文实体类中字段为为id，意思查询字段为_id，但查询结果_id会映射到实体对象id字段中）
     */
    @Id
    private Long id;

    private String username;

    private String localTime;

    private String content;

    /** 回复子节点 */
    private List<Reply> replies;

    /**
     * 回复实体类
     * 用以记录评论中，回复信息
     */
    @Data
    public static class Reply {
        /** 回复信息主键 */
        private Long replyId;

        private String username;

        private String localTime;

        private String content;

        /**
         * 平行回复
         * 指向回复中对象信息
         */
        private Long sideReplyId;

    }

}
