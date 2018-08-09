#######################################################查询25张表的记录

################################部署工作流相关的表
#ACT_GE_BYTEARRAY 通用的流程定义和流程资源：存储流程定义的资源文件
select * from fpqactiviti.act_ge_bytearray;
# ACT_RE_DEPLOYMENT（部署信息表）:用来存储部署时需要持久化保存下来的信息
select * from fpqactiviti.act_re_deployment;
#ACT_RE_PROCDEF（业务流程定义数据表：解析表）:解析成功了，在该表保存一条记录。
select * from fpqactiviti.act_re_procdef;

################################:启动流程实例相关表
#ACT_HI_ACTINST（历史节点表）：历史活动信息。这里记录流程流转过的所有节点，与HI_TASKINST不同的是，taskinst只记录usertask内容。
select * from fpqactiviti.act_hi_actinst;
#ACT_HI_IDENTITYLINK （历史流程人员表）：任务参与者数据表。主要存储历史节点参与者的信息。
select * from fpqactiviti.act_hi_identitylink;
#ACT_HI_PROCINST（历史流程实例信息）核心表：历史的流程实例
select * from fpqactiviti.act_hi_procinst;
#ACT_HI_TASKINST（历史任务流程实例信息）核心表：历史的任务实例
select * from fpqactiviti.act_hi_taskinst;
#ACT_RU_EXECUTION（运行时流程执行实例）：我的代办任务查询表
select * from fpqactiviti.act_ru_execution;
#ACT_RU_IDENTITYLINK（运行时用户关系信息，身份联系）：主要存储当前节点参与者的信息,任务参与者数据表。
select * from fpqactiviti.act_ru_identitylink;
#ACT_RU_TASK(运行时任务数据表)：（执行中实时任务）代办任务查询表，运行时任务
select * from fpqactiviti.act_ru_task;


################################:结束流程中某一个任务修改的相关表
#ACT_HI_ACTINST（历史节点表）：历史活动信息。这里记录流程流转过的所有节点，与HI_TASKINST不同的是，taskinst只记录usertask内容。
select * from fpqactiviti.act_hi_actinst;
#ACT_HI_IDENTITYLINK （历史流程人员表）：任务参与者数据表。主要存储历史节点参与者的信息。
select * from fpqactiviti.act_hi_identitylink;
#ACT_HI_TASKINST（历史任务流程实例信息）核心表：历史的任务实例
select * from fpqactiviti.act_hi_taskinst;
#ACT_RU_IDENTITYLINK（运行时用户关系信息，身份联系）：主要存储当前节点参与者的信息,任务参与者数据表。
select * from fpqactiviti.act_ru_identitylink;

################################:结束整个流程后修改的相关表
#ACT_HI_ACTINST（历史节点表）：历史活动信息。这里记录流程流转过的所有节点，与HI_TASKINST不同的是，taskinst只记录usertask内容。
select * from fpqactiviti.act_hi_actinst;
#ACT_RU_EXECUTION（运行时流程执行实例）：我的代办任务查询表
select * from fpqactiviti.act_ru_execution;
#ACT_RU_IDENTITYLINK（运行时用户关系信息，身份联系）：主要存储当前节点参与者的信息,任务参与者数据表。
select * from fpqactiviti.act_ru_identitylink;
#ACT_RU_TASK(运行时任务数据表)：（执行中实时任务）代办任务查询表，运行时任务
select * from fpqactiviti.act_ru_task;



#整个流程结束都为修改的表
select * from fpqactiviti.act_evt_log;
select * from fpqactiviti.act_ge_property;
select * from fpqactiviti.act_hi_attachment;
select * from fpqactiviti.act_hi_comment;
select * from fpqactiviti.act_hi_detail;
select * from fpqactiviti.act_hi_varinst;
select * from fpqactiviti.act_id_group;
select * from fpqactiviti.act_id_info;
select * from fpqactiviti.act_id_membership;
select * from fpqactiviti.act_id_user;
select * from fpqactiviti.act_procdef_info;
select * from fpqactiviti.act_re_model;
select * from fpqactiviti.act_ru_event_subscr;
select * from fpqactiviti.act_ru_job;
select * from fpqactiviti.act_ru_variable;
