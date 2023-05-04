## onBoarding

### TechStacks

* **Language**: Kotlin
* **Dependencies**: androidx.*, compose, dagger2 
* **Test dependencies**: Mokk, Junit, fragment-testing, core-testing, Espresso
* **Folder structure**    

```
├── di(only android)
│   └── components
│   └── module
├── data
│   └── repository
├── domain
│   └── featureName
│      └── model
│      └── extension
│      └── usecase
├──ui
│   └── featureName
│       └── screen
│       └── viewModel
│       └── viewState
├── foundation
│   └── ui
│   └── analytics
│   └── extension
│   └── ...

```    

* **di**
使用dagger2完成modules和components的构建和注入。
* **data**
repository为usecase提供方法，通过client完成提供远程服务的调用
* **usecases**
为用户提供单一，聚合的操作单元，完成对repository的调用并将服务端数据model转换为viewmodel需要的model
* **ui**
向用户提供界面，其中包含compose构建的screen，处理用户与UI交互的viewmodel，为screen展示提供数据的viewstate。viewmodel将使用usecase完成用户操作的响应。
* **foundation**
提供ui，analytics，navigator, extension等基础组件 


#### Story

As a user
I want the home screen to be displayed as expected 

AC 1：
**Given** 
the user open home view
**When** 
fetch data from PromptsService successfully 
data is not empty
**Then**
display the data as expected

AC 2：
**Given** 
the user open home view
**When** 
fetch data from PromptsService successfully 
data not empty
**Then**
display empty view as expected

AC 3：
**Given** 
the user open home view
**When** 
fetch data from PromptsService failed 
**Then**
display error view as expected


#### Git issue
初始puml
![ae51177e70b97ad0e170991636b65607.png](evernotecid://075974F6-E33A-498A-9FCB-88944E25008B/appyinxiangcom/28906394/ENResource/p64)
架构分层puml
![67eed3e54c4d27a0d93fc93d926f89ea.png](evernotecid://075974F6-E33A-498A-9FCB-88944E25008B/appyinxiangcom/28906394/ENResource/p65)
最终的puml
![ff64540194486d51b977821a66e89d11.png](evernotecid://075974F6-E33A-498A-9FCB-88944E25008B/appyinxiangcom/28906394/ENResource/p63)


我修改了uml中的方法与返回的对象,其中的对象class如下
```
data class Prompt(val prompt: String?)
data class PromptModel(val helloPrompt: String?)
data class HomeViewState(val content: String = "loading")
```

* repository
ME：测试工具是mockk，请为我生成PromptsRepository的测试代码和生成代码
GPT：......
ME: repository返回的类型是Flow<Result<Prompt>>，promptsService返回的类型也是Flow<Result<Prompt>>，这个story中Result的类型都是kotlin.Result, 请修改PromptsRepository与PromptsRepositoryTest
GPT: ......
promptsService 返回的类型是Flow<Result<Prompt>>，在PromptsRepository中我们不会对它进行解析和修改
GPT: ......

生产的代码：PromptsRepositoryTest，PromptsRepository

*usecase
ME：请为我生成PromptsUseCase与PromptsUseCaseTest，在usecase中Prompt会被转化为PromptModel，其中的逻辑是：
1.如果Prompt的data不为null, PromptModel的helloPrompt将是Prompt的data字段的内容的前面添加“ hello ”
2.如果Prompt的data为null, PromptModel的helloPrompt将是"empty"
它返回的类型是Flow<Result<PromptModel>>，PromptsRepository返回的类型是Flow<Result<Prompt>>

GPT: ......
生产的代码：PromptsUseCaseTest，PromptsUseCase

*viewModel
ME：
1. HomeViewModel会将usecase返回的Flow<Result<PromptModel>>，转化为HomeViewState，其中转化为HomeViewState的content将是PromptModel的helloPrompt
2. HomeViewState将会更新到私有变量_state = MutableStateFlow(HomeViewState())中，暴露为共有变量state提供给View使用。
3. HomeViewState的初始状态是Loading
4. 当usecase返回failure的时候，HomeViewState为Error
5. 返回PromptModel的helloPrompt值为"empty"的时候，HomeViewState是Empty，其余是Success，
GPT: ......
ME：请用result.fold方法来解析usecase的返回
GPT....
生产的代码：HomeViewModelTest，HomeViewModel

思考题：使用类似的story生成iOS的代码，或者Flutter的代码








