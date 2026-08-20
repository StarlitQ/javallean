# 25 · Git + GitHub：代码的"存档 + 云备份"

> 目标：学会用 Git 给代码建存档点、用 GitHub 把代码备份到云端，并形成以后每个项目都用的习惯。
> 本文是完整参考，动手时跟着聊天里的节奏一步步来即可。

---

## 一、先建立直觉

**Git = 本地"时间机器"**。它给你代码文件夹建无数个"存档点（commit）"，任何时候能回退到任意一个存档、看每次改了啥。

**GitHub = 云端"网盘"**。把你本地的存档推（push）上去备份，还能多人协作、当作品集。

一句话：**Git 在你自己电脑上记，GitHub 把记录存到网上。** 两个是配合的，不是二选一。

---

## 二、三个核心概念（先混个脸熟）

| 概念 | 英文 | 白话解释 |
|------|------|----------|
| 仓库 | repository（repo） | 一个被 Git 管理的文件夹，比如你的 `java_enter` |
| 提交 | commit | 一次"存档"，记录那一刻所有文件的快照 + 一句说明 |
| 暂存区 | staging | "准备提交的清单"，先挑好要存哪些，再一起存（见下） |

**关键：commit 不是自动的，是"先选（add）→ 再存（commit）"两步。** 为什么分两步？因为有时你改了 5 个文件，只想存其中 3 个，先 add 这 3 个，再 commit。

```
工作区（改文件） --git add--> 暂存区（清单） --git commit--> 存档点（历史）
```

---

## 三、本地 Git 日常三步

```bash
git add .        # 把当前所有改动加入"待提交清单"
git commit -m "这次改了什么"   # 存一个档，-m 后面写一句说明
git status       # 随时看：现在改了啥、哪些已 add、哪些没
git log          # 看历史：所有存档点列表
```

> `git add .` 里的 `.` 表示"当前文件夹下所有文件"。

---

## 四、推到 GitHub 的流程（一次性打通）

```bash
git remote add origin https://github.com/zyi159672-ai/java-learning.git   # 关联远程仓库
git branch -M main           # 把本地主分支改名为 main（对齐 GitHub）
git push -u origin main      # 第一次推送，-u 记住，以后直接 git push
```

之后每天的循环就变成：

```
改代码 → git add . → git commit -m "说明" → git push
```

---

## 五、手把手第一次配置（跟着做）

### 第 1 步：配置身份（一次性）

```bash
git config --global user.name "zxt"      # 会显示在每次提交记录里
git config --global user.email "zyi159672@gmail.com"     # 建议填注册 GitHub 的邮箱
```

> 验证：`git config --global user.name` 能打印出你刚设的名字，就成功了。

### 第 2 步：建一个 .gitignore（过滤垃圾文件）

不是所有文件都该上传——编译产物、IDE 配置、依赖包这些"生成出来的"要排除掉。在 `java_enter/` 根目录建一个 `.gitignore` 文件，写上：

```gitignore
# IDE
.idea/
*.iml
.vscode/

# Java 编译产物
target/
*.class
out/

# 依赖 / 构建
node_modules/
dist/

# 系统文件
.DS_Store
Thumbs.db
```

### 第 3 步：初始化仓库 + 第一次提交

```bash
cd java_enter            # 进入你要管理的项目根目录
git init                 # 初始化，让这个文件夹变成"仓库"
git add .                # 加入所有文件
git commit -m "首次提交：完整学习项目"   # 存第一个档
```

### 第 4 步：注册 GitHub + 建仓库

1. 打开 https://github.com 注册账号（邮箱 + 密码）
2. 登录后右上角 `+` → `New repository`
3. 仓库名填个比如 `java-learning`，**不要**勾选 "Add a README"（因为你本地已有代码，勾了会冲突）

### 第 5 步：关联 + 推送

```bash
git remote add origin https://github.com/zyi159672-ai/java-learning.git
git branch -M main
git push -u origin main
```

**坑：** 现在 GitHub 推送**不能用密码**了，会弹出一个认证窗口（Windows 上通常是浏览器弹窗登录），登录 GitHub 账号授权即可；或提前准备 **Personal Access Token（个人访问令牌）** 当密码用。遇到认证问题在聊天里喊我。

---

## 六、以后每天的循环（形成肌肉记忆）

```bash
git status              # 看看改了啥（可选）
git add .
git commit -m "说明这次干了啥"
git push
```

**commit 说明怎么写**：一句话说清楚"这次改了什么"，比如 `"图书模块：完成分页搜索"`。好的说明让 3 个月后的自己一眼看懂。

---

## 七、救命命令（改坏了怎么办）

| 情况 | 命令 |
|------|------|
| 看改了什么 | `git status` / `git diff` |
| 撤销某个文件的改动（还没 add） | `git checkout -- 文件名` |
| 撤销 add（从清单拿出来，文件还在） | `git reset 文件名` |
| 回到上一个存档（慎用，会丢之后改动） | `git reset --hard HEAD~1` |
| 看某个存档长啥样 | `git log` 找到哈希值 → `git show 哈希值` |

> 只要 commit 过，代码就"死不了"。Git 的哲学：**勤存档，就不怕改坏。**

---

*下一步（doc 26）是 JWT 登录鉴权，等 Git/GitHub 走通后开始。*
