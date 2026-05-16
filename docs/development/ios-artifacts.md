# iOS 导入产物

当前仓库为 `core` 下可复用的 Kotlin Multiplatform 模块提供 iOS 导入产物流水线，目标是产出可直接被 Xcode 导入的 `XCFramework`。

## 覆盖模块

当前会产出以下模块的 iOS 导入产物：

- `core:common`
- `core:data`
- `core:database`
- `core:datastore`
- `core:datastore-proto`
- `core:network`

以下模块不参与该流水线：

- `core:ui`
- `core:ffmpeg`

## 本地构建

构建并汇总产物：

```bash
./gradlew collectCoreIosArtifacts
```

汇总目录：

```text
core/build/ios-artifacts/
```

每个模块会生成一个独立的 `XCFramework` 目录：

- `CoreCommon.xcframework`
- `CoreData.xcframework`
- `CoreDatabase.xcframework`
- `CoreDatastore.xcframework`
- `CoreDatastoreProto.xcframework`
- `CoreNetwork.xcframework`

## CI 流水线

GitHub Actions 工作流文件：

```text
.github/workflows/build-core-ios-artifacts.yml
```

流水线运行在 `macos-latest`，执行：

```bash
./gradlew collectCoreIosArtifacts --stacktrace --info
```

随后上传目录 `core/build/ios-artifacts` 作为构建产物。

## 说明

- 当前流水线面向模块级导入产物，不会额外合并为单一总框架。
- `data`、`network`、`database` 等模块之间仍保持当前工程依赖关系；iOS 侧接入时应按需要导入对应模块。
- 若后续新增新的 KMP `core` 模块，需要同步更新根项目的 `assembleCoreIosArtifacts` / `collectCoreIosArtifacts` 任务和该文档。
