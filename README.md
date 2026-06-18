# Java AI Agent 后端框架

基于 Java 21 与 Spring AI 的智能 Agent 开发底座。

## 技术栈

Java 21、Spring Boot 3、Spring AI、Redis、MySQL、RAG 向量检索

## 能力

- 多轮对话与上下文记忆
- RAG 知识库检索增强
- 可扩展 Skill 技能系统（Java / Python）
- 支持多消息通道接入

## 启动

```bash
mvn spring-boot:run
```

## 配置

在 `application.yml` 中配置模型 API Key、Redis、MySQL 等依赖。
