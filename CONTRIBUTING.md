# 贡献指南

感谢你考虑为 FairFood 做出贡献！以下是参与项目的详细指南。

## 🎯 我们的价值观

在贡献代码之前，请理解我们的核心理念：

1. **透明** - 代码即文档，算法可解释
2. **公平** - 每一个决策都要考虑对劳动者（骑手、商家）的影响
3. **开源** - 没有秘密，社区高于商业

## 🚀 开始贡献

### 1. Fork 项目

```bash
# 点击 GitHub 上的 Fork 按钮
# 然后克隆你的 fork
git clone https://github.com/YOUR-USERNAME/fair-food-delivery.git
cd fair-food-delivery
```

### 2. 创建功能分支

```bash
# 使用清晰的分支命名
git checkout -b feature/智能派单算法优化
# 或
git checkout -b fix/修复订单超时bug
```

### 3. 开发与测试

```bash
# 安装依赖
npm install  # 或 ./scripts/install.sh

# 运行测试
npm test

# 运行代码检查
npm run lint
```

### 4. 提交代码

遵循 [Conventional Commits](https://www.conventionalcommits.org/) 规范：

```
feat: 添加新的派单算法权重配置
fix: 修复高峰期订单超时问题
docs: 更新算法文档
refactor: 重构价格计算逻辑
```

### 5. 提交 PR

- 确保所有测试通过
- 更新相关文档
- 添加清晰的 PR 描述
- 链接相关 Issue

## 📐 代码规范

### Go 语言
- 遵循 [Effective Go](https://golang.org/doc/effective_go)
- 使用 gofmt 自动格式化
- 单元测试覆盖率 > 80%

### 前端
- React + TypeScript
- ESLint + Prettier
- 组件需有类型定义和文档

### 算法代码
- 必须有详细注释解释逻辑
- 附带时间复杂度/空间复杂度分析
- 提供单元测试和性能测试

## 🧪 测试要求

- 所有核心算法必须有单元测试
- 集成测试覆盖关键业务流程
- 性能测试确保算法效率

## 📖 文档标准

- API 接口必须有 OpenAPI 文档
- 算法实现必须有设计文档
- 复杂逻辑需添加行内注释

## 💬 沟通方式

- **Issue** - Bug 报告和功能建议
- **Discussions** - 一般讨论和问答
- **PR Review** - 代码审查讨论

## 🏆 贡献者激励

- 代码贡献者将出现在 README 的贡献者列表
- 核心贡献者可获得项目管理员权限
- 重大贡献者将获得特别认可

## ⚖️ 行为准则

请遵守我们的 [Code of Conduct](CODE_OF_CONDUCT.md)，尊重每一位贡献者。

---

**有问题？来 Discussions 提问！**
