# Android 快速开发框架

## 一、 屏幕适配

    <p>
        参考今日头条适配方案，参考AndroidAutoSize框架。
        <a href="https://github.com/JessYanCoding/AndroidAutoSize">
    </p>

### 使用方法：

      <p>
         1. 在AndrodManifest配置设计图信息
         2. Application继承AFWApplication
      </p>

    ```
        // 设置设计图宽度
        <meta-data 
            android:name="design_width_in_dp"
            android:value="360"/>
        // 设置设计图高度
        <meta-data
            android:name="design_height_in_dp"
            android:value="640"/>
    ```

## 二、侵入式状态栏

    <P>
        继承AFWActivity，isFullScreen() 方法返回true，即可开启侵入式状态栏，返回false，关闭侵入式状态栏
        override fun isFullScreen(): Boolean {
             return true
        }
    </P>

## 三、AFWActivity、AFWFragment

    <p>
        基础的Activity和Fragment，封装了一些常用方法
    </p>

### 1. fragment的add和remove

       添加和移除fragment，暂时不支持fragment嵌套fragment。
        1. addFragment（）
        2. removeFragment（）

### 2. 权限申请

    封装了Android6.0以上，危险权限申请,请求成功失败的状态，在PermissionCallBack中回调

    ```
        fun checkPermissions(
            permissions: Array<String>,
            permissionCallBack: PermissionCallBack,
        )
    ```
    
    PermissionCallBack回调,有两个参数：
      1. granted:Boolean:权限申请成功失败的标志，true = 成功，false = 失败，申请的权限列表中，有一个被拒绝，granted = false
      2. permanentRejectionList 永久拒绝权限列表，返回被永久拒绝的权限列表，没有被永久拒绝，列表为空
    
    ```
        interface PermissionCallBack {
            fun onPermission(granted:Boolean,permanentRejectionList:ArrayList<String>)
        }
    ```
### MVP的封装
    AFWMVPActivity
    AFWMVPFragment

    <p>
        对MVP的封装
        Mode层：负责网络请求
        Presenter层：负责对Mode层的数据进行分析，并分发到View层
        View层：View的展示，通常由Activity或Fragment实现
    </p>

## 四、AFWRecyclerView
    <p>
        对RecyclerView的一些积累
        RecyclerView 通用的adapter，支持多布局
    </p>
### ARecyclerAdapter
     <p>
        RecyclerView 通用的adapter，单布局
    </p>
### ARecyclerMultipleAdapter
    <p>
        RecyclerView 通用的adapter，多布局
    </p>

## 五、网络请求框架
    <p>
       封装HTTP请求框架
        默认使用okhttp
        使用HttpRequest进行网络请求的发送
    </p>