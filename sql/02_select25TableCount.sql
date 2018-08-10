######################查询25张表的记录
#act_evt_log(事件日志表):存储事件处理日志
select * from fpqactiviti.act_evt_log;

####ACT_GE_* : “GE”代表“General”（通用），用在各种情况下；
#ACT_GE_BYTEARRAY（通用的 资源文件表）：存储流程定义的资源文件
select * from fpqactiviti.act_ge_bytearray;
#act_ge_property（系统配置表）:dbid的取值会变化，记录指针
select * from fpqactiviti.act_ge_property;

####ACT_HI_* : “HI”代表“History”（历史），这些表中保存的都是历史数据，比如执行过的流程实例、变量、任务，等等。
#ACT_HI_ACTINST（活动节点历史表）：记录流程流转过的所有节点，与HI_TASKINST不同的是，act_hi_taskinst只记录usertask内容。
select * from fpqactiviti.act_hi_actinst;
#ACT_HI_IDENTITYLINK （历史身份联系表）：任务参与者数据表。主要存储历史节点参与者的信息。
select * from fpqactiviti.act_hi_identitylink;
#ACT_HI_PROCINST（历史流程实例表）核心表：历史的流程实例
select * from fpqactiviti.act_hi_procinst;
#ACT_HI_TASKINST（历史任务表）核心表：历史的任务实例
select * from fpqactiviti.act_hi_taskinst;
#act_hi_attachment(历史的流程附件):记录历史的流程附件数据
select * from fpqactiviti.act_hi_attachment;
#act_hi_comment（历史审批意见表）：
select * from fpqactiviti.act_hi_comment;
#act_hi_detail（历史详情表）：流程中产生的变量详细，包括控制流程流转的变量，业务表单中填写的流程需要用到的变量等
select * from fpqactiviti.act_hi_detail;
#act_hi_varinst（历史变量信息）：历史的流程运行中的变量信息
select * from fpqactiviti.act_hi_varinst;

#ACT_ID_*:“ID”代表“Identity”（身份），这些表中保存的都是身份信息，如用户和组以及两者之间的关系。如果Activiti被集成在某一系统当中的话，这些表可以不用，可以直接使用现有系统中的用户或组信息；
#act_id_group(身份信息-组信息):用来存储用户组信息
select * from fpqactiviti.act_id_group;
#act_id_info(身份信息-用户扩展信息表):
select * from fpqactiviti.act_id_info;
#act_id_membership(身份信息-用户和组关系的中间表):用户用户组关联表
select * from fpqactiviti.act_id_membership;
#act_id_user(身份信息-用户信息):
select * from fpqactiviti.act_id_user;

#act_procdef_info(流程定义信息表):
select * from fpqactiviti.act_procdef_info;

####ACT_RE_* : “RE”代表“Repository”（仓库），这些表中保存一些‘静态’信息，如流程定义和流程资源（如图片、规则等）；
# ACT_RE_DEPLOYMENT（部署信息表）:用来存储部署时需要持久化保存下来的信息
select * from fpqactiviti.act_re_deployment;
#ACT_RE_PROCDEF（业务流程定义数据表：解析表）:解析成功了，在该表保存一条记录。
select * from fpqactiviti.act_re_procdef;
#act_re_model(流程设计模型表):创建流程的设计模型时，保存在该数据表中。
select * from fpqactiviti.act_re_model;

####ACT_RU_*:“RU”代表“Runtime”（运行时），这些表中保存一些流程实例、用户任务、变量等的运行时数据。Activiti只保存流程实例在执行过程中的运行时数据，并且当流程结束后会立即移除这些数据，这是为了保证运行时表尽量的小并运行的足够快；
#ACT_RU_EXECUTION（流程实例运行时 执行实例表）：
select * from fpqactiviti.act_ru_execution;
#ACT_RU_IDENTITYLINK（流程实例运行时 身份联系表）：主要存储当前节点参与者的信息,任务参与者数据表。
select * from fpqactiviti.act_ru_identitylink;
#ACT_RU_TASK(流程实例运行时 用户任务表)：（执行中实时任务）代办任务查询表，运行时任务
select * from fpqactiviti.act_ru_task;
#act_ru_event_subscr（运行时事件表）：
select * from fpqactiviti.act_ru_event_subscr;
#act_ru_job（运行时定时任务表）：
select * from fpqactiviti.act_ru_job;
#act_ru_variable（运行时流程变量数据表）：
select * from fpqactiviti.act_ru_variable;



#################查询25张表的数据量，在information_schema数据库下执行
select tbs.TABLE_NAME,tbs.TABLE_ROWS from information_schema.`TABLES` tbs where tbs.TABLE_SCHEMA = 'fpqactiviti';