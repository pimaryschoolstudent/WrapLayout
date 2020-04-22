# WrapLayout
一款轻轻轻轻量级的自动换行布局，可以设置Gravity

##懒人使用方法

```
项目目录——>build.gradle

allprojects {

      repositories {
    
      ...
      maven { url 'https://jitpack.io' }
    }
}

app——>build.gradle
  
  	dependencies {
  
       		...
       
	     implementation 'com.github.pimaryschoolstudent:WrapLayout:1.0.0'
	     
	}
```
导入成功后像一般的布局使用即可
可以参考项目中的MainActivity

##扩展用法
复制WrapLayout.java到你的项目中
在res/values/attrs.xml
```
 <declare-styleable name="WrapLayout">
        <attr name="gravity">
            <enum name="center" value="1001"/>
            <enum name="left" value="1002"/>
            <enum name="right" value="1003"/>
        </attr>
</declare-styleable>
```
然后就可以自由扩展了
