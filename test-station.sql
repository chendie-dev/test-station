-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
    `user_id`       bigint      NOT NULL AUTO_INCREMENT     COMMENT '用户id',
    `username`      varchar(64) NOT NULL DEFAULT ''         COMMENT '用户名',
    `real_name`     varchar(64) NOT NULL DEFAULT ''         COMMENT '真实名称',
    `password`      varchar(64) NOT NULL DEFAULT ''         COMMENT '密码',
    `lesson_id`     bigint      NOT NULL DEFAULT 0          COMMENT '班级id',
    `role_name`     varchar(64) NOT NULL DEFAULT 'student'  COMMENT '角色类型',
    `create_time`   bigint      NOT NULL DEFAULT 0          COMMENT '创建时间',
    `update_time`   bigint      NOT NULL DEFAULT 0          COMMENT '更新时间',
    PRIMARY KEY (`user_id`)
);

-- paper 试卷
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper`  (
  `paper_id`        bigint          NOT NULL AUTO_INCREMENT COMMENT '试卷id',
  `title`           varchar(128)    NOT NULL DEFAULT ''     COMMENT '试卷标题',
  `time`            bigint          NOT NULL DEFAULT 0      COMMENT '考试时间',
  `user_id`         bigint          NOT NULL DEFAULT 0      COMMENT '创建人id',
  `tag_id`          bigint          NOT NULL DEFAULT 0      COMMENT '标签id',
  `total_score`     int             NOT NULL DEFAULT 0      COMMENT '分值',
  `create_time`     bigint          NOT NULL DEFAULT 0      COMMENT '创建时间',
  `update_time`     bigint          NOT NULL DEFAULT 0      COMMENT '更新时间',
  PRIMARY KEY (`paper_id`)
);

-- 用户试题关系表
DROP TABLE IF EXISTS `user_paper`;
CREATE TABLE `user_paper`  (
    `user_paper_id`   bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`         bigint          NOT NULL DEFAULT 0      COMMENT '用户id',
    `paper_id`        bigint          NOT NULL DEFAULT 0      COMMENT '试卷id',
    `create_time`     bigint          NOT NULL DEFAULT 0      COMMENT '创建时间',
    PRIMARY KEY (`user_paper_id`)
);

-- 试题 question
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
    `question_id`    bigint          NOT NULL AUTO_INCREMENT COMMENT '试题id',
    `question_name`  varchar(128)    NOT NULL DEFAULT ''     COMMENT '试题标题',
    `options`        varchar(1024)   NOT NULL DEFAULT ''     COMMENT '选型',
    `answer`         varchar(1024)   NOT NULL DEFAULT ''     COMMENT '答案',
    `analysis`       varchar(1024)   NOT NULL DEFAULT ''     COMMENT '题目分析',
    `type`           tinyint(4)      NOT NULL DEFAULT 0      COMMENT '试题类型',
    `score`          int             NOT NULL DEFAULT 0      COMMENT '分值',
    `tag_id`         bigint          NOT NULL DEFAULT 0      COMMENT '标签id',
    `create_time`    bigint          NOT NULL DEFAULT 0      COMMENT '创建时间',
    `update_time`    bigint          NOT NULL DEFAULT 0      COMMENT '更新时间',
    PRIMARY KEY (`question_id`)
);

-- 试卷试题关系表
DROP TABLE IF EXISTS `paper_question`;
CREATE TABLE `paper_question`  (
    `paper_question_id`    bigint          NOT NULL AUTO_INCREMENT COMMENT '试题id',
    `paper_id`             bigint          NOT NULL COMMENT '试卷id',
    `question_id`             bigint          NOT NULL COMMENT '试卷id',
    `create_time`    bigint          NOT NULL DEFAULT 0      COMMENT '创建时间',
    PRIMARY KEY (`paper_question_id`)
);

-- lesson 班级
DROP TABLE IF EXISTS `lesson`;
CREATE TABLE `lesson`  (
  `lesson_id`       bigint          NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `lesson_name`     varchar(128)    NOT NULL DEFAULT ''     COMMENT '试卷标题',
  `create_time`     bigint          NOT NULL DEFAULT 0      COMMENT '创建时间',
  `update_time`     bigint          NOT NULL DEFAULT 0      COMMENT '更新时间',
  PRIMARY KEY (`lesson_id`)
);

-- exam_record 考试记录
DROP TABLE IF EXISTS `exam_record`;
CREATE TABLE `exam_record`  (
   `record_id`      bigint          NOT NULL AUTO_INCREMENT COMMENT '考试记录id',
   `user_id`        bigint          NOT NULL DEFAULT 0      COMMENT '用户id',
   `paper_id`       bigint          NOT NULL DEFAULT 0      COMMENT '试卷id',
   `grade`          int             NOT NULL DEFAULT 0      COMMENT '成绩',
   `use_time`       bigint          NOT NULL DEFAULT 0      COMMENT '使用时间',
   `create_time`    bigint          NOT NULL DEFAULT 0      COMMENT '创建时间',
   `update_time`    bigint          NOT NULL DEFAULT 0      COMMENT '更新时间',
   PRIMARY KEY (`record_id`)
);

-- 考试记录详细
DROP TABLE IF EXISTS `exam_record_detail`;
CREATE TABLE `exam_record_detail`  (
    `detail_id`      bigint          NOT NULL AUTO_INCREMENT COMMENT '考试记录id',
    `record_id`      bigint          NOT NULL DEFAULT 0      COMMENT '考试记录id',
    `question_id`    bigint          NOT NULL DEFAULT 0      COMMENT '试题id',
    `store`          int             NOT NULL DEFAULT 0      COMMENT '成绩',
    `reply`          varchar(1024)   NOT NULL DEFAULT ''     COMMENT '回答',
    `create_time`    bigint          NOT NULL DEFAULT 0      COMMENT '创建时间',
    `update_time`    bigint          NOT NULL DEFAULT 0      COMMENT '更新时间',
    PRIMARY KEY (`detail_id`)
);

-- 信息 information
CREATE TABLE `information`  (
    `information_id` bigint          NOT NULL AUTO_INCREMENT COMMENT '信息id',
    `user_id`        bigint          NOT NULL DEFAULT 0      COMMENT '用户id',
    `title`          varchar(128)    NOT NULL DEFAULT ''     COMMENT '信息标题',
    `content`        varchar(1024)   NOT NULL DEFAULT ''     COMMENT '内容',
    `create_time`    bigint          NOT NULL DEFAULT 0      COMMENT '创建时间',
    `update_time`    bigint          NOT NULL DEFAULT 0      COMMENT '更新时间',
    PRIMARY KEY (`information_id`)
);

-- 用户信息关系表
DROP TABLE IF EXISTS `user_information`;
CREATE TABLE `user_information`  (
    `user_information_id`   bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`               bigint          NOT NULL DEFAULT 0      COMMENT '用户id',
    `information_id`        bigint          NOT NULL DEFAULT 0      COMMENT '信息id',
    `create_time`           bigint          NOT NULL DEFAULT 0      COMMENT '创建时间',
    PRIMARY KEY (`user_information_id`)
);

-- 标签 tag
CREATE TABLE `tag`  (
    `tag_id`         bigint          NOT NULL AUTO_INCREMENT COMMENT '标签id',
    `user_id`        bigint          NOT NULL DEFAULT 0      COMMENT '用户id',
    `tag_name`       varchar(128)    NOT NULL DEFAULT ''     COMMENT '信息标题',
    `desc`           varchar(128)    NOT NULL DEFAULT ''     COMMENT '标签备注',
    `create_time`    bigint          NOT NULL DEFAULT 0      COMMENT '创建时间',
    `update_time`    bigint          NOT NULL DEFAULT 0      COMMENT '更新时间',
    PRIMARY KEY (`tag_id`)
);
