# Windows Docker 环境启动脚本

不推荐，建议使用 [Windows Subsystem for Linux, WSL](https://docs.microsoft.com/zh-cn/windows/wsl/install-win10) 来进行开发。

## 提示

如果遇到禁止执行脚本的权限问题，请在 PowerShell 中执行如下命令：

```ps1
Set-ExecutionPolicy -Scope CurrentUser Unrestricted
```

以解除当前用户的 PowerShell 脚本执行限制。

## 启动

在命令行输入 `./start-all.ps1` 启动 Docker 环境。

或者双击该脚本。
