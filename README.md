# TabLayout

use Gradle:

```
repositories {
  maven { url "https://jitpack.io" }
  mavenCentral()
  google()
}
dependencies {
  implementation 'com.github.czh235285:banner:1.0.0'
}
```

同法直接看 [banner](https://github.com/youth5201314/banner)

之前项目中遇到动态创建控件的情况下，圆点指示器不显示的问题，所以copy源码下来改了下，顺便增加了自定义指示器的方法。

```
        banner.setSelectedDrawable(selectedDrawable);
        banner.setUnSelectedDrawable(unSelectedDrawable);

        Drawable方便用资源图片或者动态创建Drawable都能用。
```