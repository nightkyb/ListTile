# ListTile [![](https://jitpack.io/v/nightkyb/ListTile.svg)](https://jitpack.io/#nightkyb/ListTile)

轻量级的列表项组件，包含必选的title文本，可选的leading图片，subtitle文本，extra文本/图片以及trailing图片。title、subtitle限制单行，extra允许多行。

## Demo

![](https://github.com/nightkyb/ListTile/blob/master/demo.png)

![](https://github.com/nightkyb/ListTile/blob/master/Screenshot_2019-08-29.png)

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
    implementation 'com.github.nightkyb:ListTile:1.0.1'
}
```

### Step 2

Add the ListTile to your layout:

```java
<com.nightkyb.listtile.ListTile
    style="@style/ListTileStyle"
    android:id="@+id/listTile"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:lt_extra_type="text"
    app:lt_extra="v1.0.0"
    app:lt_extra_color="#ff4400"
    app:lt_extra_left_margin="16dp"
    app:lt_extra_textAppearance="@style/TextAppearance.MaterialComponents.Body2"
    app:lt_leading="@drawable/ic_modify_password_24dp"
    app:lt_leading_color="#dd0000"
    app:lt_leading_right_margin="16dp"
    app:lt_leading_size="48dp"
    app:lt_subtitle="哈哈啊"
    app:lt_subtitle_color="#ff0000"
    app:lt_subtitle_textAppearance="@style/TextAppearance.MaterialComponents.Subtitle2"
    app:lt_subtitle_top_margin="4dp"
    app:lt_title="版本版本"
    app:lt_title_color="#ff00ff"
    app:lt_title_textAppearance="@style/TextAppearance.MaterialComponents.Subtitle1"
    app:lt_trailing="@drawable/ic_arrow_right_24dp"
    app:lt_trailing_color="@color/colorAccent"
    app:lt_trailing_left_margin="0dp"
    app:lt_trailing_size="24dp"/>
```

[Code example](https://github.com/nightkyb/ListTile/blob/master/app/src/main/res/layout/content_main.xml)
