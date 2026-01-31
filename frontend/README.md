# 前端项目结构

## 目录说明

```
frontend/
├── package.json              # 根目录配置（工作空间）
├── consumer-app/             # 用户端（Taro）
│   ├── src/
│   │   ├── app.config.ts    # 小程序全局配置
│   │   ├── app.tsx          # 应用入口
│   │   ├── pages/           # 页面
│   │   │   ├── index/       # 首页
│   │   │   ├── merchant/    # 商家详情
│   │   │   ├── cart/        # 购物车
│   │   │   ├── order/       # 订单
│   │   │   └── profile/     # 个人中心
│   │   ├── components/      # 组件
│   │   ├── store/           # 状态管理 (Zustand)
│   │   ├── api/             # 接口调用
│   │   ├── utils/           # 工具函数
│   │   ├── styles/          # 样式
│   │   └── types/           # 类型定义
│   ├── config/              # Taro 配置
│   └── package.json
├── rider-app/               # 骑手端（Taro）- 待开发
├── admin-web/               # 管理后台（React）- 待开发
└── shared/                  # 共享代码
    ├── types/               # 类型定义
    └── utils/               # 工具函数
```

## 快速开始

### 安装依赖

```bash
cd frontend
pnpm install
```

### 启动开发

```bash
# 微信小程序
cd consumer-app
pnpm dev:weapp

# H5
pnpm dev:h5

# React Native
pnpm dev:rn
```

### 构建

```bash
# 构建微信小程序
cd consumer-app
pnpm build:weapp

# 构建 H5
pnpm build:h5
```

## 技术栈

- **框架**: Taro 4.x + React 18
- **状态管理**: Zustand
- **样式**: SCSS
- **类型检查**: TypeScript
- **构建工具**: Webpack 5

## 编码规范

- 使用 TypeScript
- 组件使用 Functional Component + Hooks
- 样式使用 BEM 命名规范
- API 接口统一在 `api/` 目录管理
- 工具函数统一在 `utils/` 目录管理

## Git 规范

- 分支命名: `feature/xxx`, `fix/xxx`
- Commit message: `feat: xxx`, `fix: xxx`, `docs: xxx`
- PR 要求至少 1 人 review
