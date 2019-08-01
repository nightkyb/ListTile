# ListTile [![](https://jitpack.io/v/nightkyb/ListTile.svg)](https://jitpack.io/#nightkyb/ListTile)

轻量级的列表项组件，包含必选的title文本，可选的heading图片，subtitle文本，extra文本以及trailing图片。title、subtitle限制单行，extra允许多行。

## Demo

![](https://github.com/nightkyb/ListTile/blob/master/Screenshot_1.png)

## Usage

### Step 1

#### Gradle

root build.gradle:

 ```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
``` 

app build.gradle:

```groovy
dependencies {
    implementation 'com.github.nightkyb:ListTile:1.0.0'
}
```

### Step 2

Add the ListTile to your layout:

```java
<com.nightkyb.listtile.ListTile
    android:id="@+id/listTile"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:lt_extra="v1.0.0"
    app:lt_leading="@drawable/ic_modify_password_24dp"
    app:lt_leading_size="48dp"
    app:lt_subtitle_size="14sp"
    app:lt_title="版本版本"
    app:lt_subtitle="哈哈啊"
    app:lt_trailing="@drawable/ic_arrow_right_24dp"
    app:lt_trailing_color="@color/colorAccent"/>
```

[Code example](https://github.com/nightkyb/ListTile/blob/master/app/src/main/res/layout/content_main.xml)
