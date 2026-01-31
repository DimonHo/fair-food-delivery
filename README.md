# 🍔 FairFood - 开源外卖平台

> **让平台真正为人民服务，让天底下所有的劳动人民不再被算法剥削**

## 🌟 项目愿景

我们相信：
- **透明** - 所有算法代码公开可查
- **公平** - 抽佣仅用于维持平台运转，多余利润返还给劳动者
- **开源** - 任何人可以参与贡献，任何商家可以免费使用
- **去中心化** - 社区自治，不被资本控制

## 🎯 核心原则

1. **零剥削** - 平台抽佣仅覆盖云服务器成本（目标 <5%）
2. **算法透明** - 派单算法、价格算法、推荐算法全部开源
3. **数据主权** - 用户和商家数据归用户所有，可导出
4. **社区治理** - 重大决策由社区投票决定

## 🏗️ 系统架构

```
fair-food-delivery/
├── frontend/                 # 用户端（小程序/Web）
│   ├── consumer-app/        # 消费者端
│   ├── rider-app/           # 骑手端
│   └── merchant-app/        # 商家端
├── backend/                  # 服务端
│   ├── api-gateway/         # API 网关
│   ├── order-service/       # 订单服务
│   ├── matching-engine/     # 派单算法引擎
│   ├── pricing-engine/      # 价格算法
│   ├── payment-service/     # 支付服务
│   └── notification/        # 消息通知
├── algorithm/                # 核心算法（透明公开）
│   ├── dispatch/           # 智能派单算法
│   ├── pricing/            # 动态定价算法
│   └── route/              # 路线规划算法
├── docs/                     # 文档
│   ├── algorithm/          # 算法文档
│   ├── api/                # API 文档
│   └── deployment/         # 部署文档
└── infra/                    # 基础设施
    ├── kubernetes/         # K8s 部署配置
    └── monitoring/         # 监控告警
```

## 🚀 快速开始

```bash
# 克隆项目
git clone https://github.com/DimonHo/fair-food-delivery.git
cd fair-food-delivery

# 启动开发环境（使用 Docker Compose）
docker-compose up -d

# 访问
# - 前台: http://localhost:3000
# - API: http://localhost:8080
# - 文档: http://localhost:8000
```

## 🤝 如何贡献

我们欢迎所有有理想的开发者参与！

### 贡献方式

1. **报告 Bug** - 提交 Issue 描述问题
2. **修复 Bug** - 提交 PR 修复问题
3. **新功能** - 提出新功能想法，经社区讨论后实现
4. **改进文档** - 完善文档、翻译、示例
5. **分享传播** - 让更多人知道这个项目

### 贡献指南

请阅读 [CONTRIBUTING.md](CONTRIBUTING.md) 了解详情。

## 💰 资金与抽佣

- **平台抽佣**：仅用于云服务器费用，目标 <5%
- **盈余处理**：剩余资金用于：
  - 开发者激励计划
  - 社区活动
  - 公益捐赠
- **资金透明**：所有资金流向公开可查

## 📜 许可证

本项目采用 **GPLv3** 许可证，确保所有衍生项目也必须开源。

## 🗣️ 社区

- **GitHub Issues** - 功能建议和 Bug 报告
- **Discussions** - 交流讨论
- **Weekly Meeting** - 每周社区会议

## 📅 路线图

### Phase 1 - 基础框架
- [ ] 用户/商家/骑手注册登录
- [ ] 商家入驻和商品管理
- [ ] 下单和支付流程
- [ ] 基础派单算法

### Phase 2 - 核心算法
- [ ] 智能派单算法 v1（透明可解释）
- [ ] 动态定价算法（公平合理）
- [ ] 路线规划算法

### Phase 3 - 规模化
- [ ] 多城市支持
- [ ] 商家数据分析工具
- [ ] 骑手权益保障系统

---

## 🌈 一起改变世界

这个项目不仅仅是一个技术项目，更是一次社会实验。

**我们相信，技术应该服务于人民，而不是剥削人民。**

加入我们，一起创造一个更公平的外卖平台！

---

*Built with ❤️ by the community*
