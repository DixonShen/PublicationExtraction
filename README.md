## PublicationExtraction论文实验代码

本部分代码包括IETS（IE by Text Segmentation）实验的部分代码和实验所需的训练数据、利用DBLP数据生成的KnowledgeBase，包括CRF实验的数据预处理部分、ONDUX论文的实验的几大部分（Blocking, Matching and PSM）。

### 实验进展

- [x] CRF数据预处理及实验
 - CRF数据预处理
 - CRF实验
 - 实验结果：Accuracy 87%
- [x] ONDUX
 - Knowledge Base生成
 - Blocking
 - Matching 
 - PSM
- [ ] 实验结果


### 实验代码说明

- src/main/java 代码目录
    - Block 分块阶段
        - Block 执行分块
        - MyBlock 表示单个block
    - DataPreprocess 预处理阶段
        - ExtractFeatures 抽取CRF中使用的特征，具体特征可查看代码
        - PrepareTrainingData 准备CRF的训练数据
    - KnowledgeBase 知识库相关
        - CreateKB 知识库工具类，创建知识库等操作
        - KnowledgeBase 知识库类
        - MySQL工具类
    - Matching 匹配阶段
        - Matching 执行match等操作
        - Vocabulary 存储match过程中需要的词典
    - Reinforcement 优化阶段
        - LabelReinforcement 进行重新标记
        - PSModel 存储match结果的概率矩阵
    - Test 模型运行、测试等
        - prepareONDUXTestData 准备ONDUX测试数据
        - testONDUX 运行测试ONDUX方法
- resources/data 实验用到的数据
    - crf_test_data.txt CRF测试数据
    - crf_training_data.txt,rf_training_data_2.txt CRF训练数据
    - knowledge_base_10000.txt 使用10000条record记录构建的knowledge base

