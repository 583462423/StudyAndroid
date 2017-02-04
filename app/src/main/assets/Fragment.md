# 简单用法
1. 首先要自定义Fragment，创建fragment类并继承android.support.v4.app.Fragment，如：

```
public class SimpleFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple,container,false);
        return view;
    }
}
```

2. 编写R.layout.fragment_simple布局，这里只是显示了一个TextView
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:background="@color/colorAccent"
    android:layout_height="match_parent">

    <TextView
        android:gravity="center"
        android:textStyle="bold"
        android:textSize="20dp"
        android:text="This is simple fragment"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

</LinearLayout>
```
3. 在Activity的布局文件中，使用fragment标签将该Fragment引入，注意fragment标签是小写，没有大写，name指定fragment的完整包名。

```
    <fragment
        android:name="com.qxg.study.studyandroid.fragments.SimpleFragment"
        android:id="@+id/frgment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

# 动态添加Fragment

使用方法是，首先在布局文件中加入FrameLayout布局，用于存放fragment，注意这个布局一定要给一个id，如：

```
    <FrameLayout
        android:id="@+id/framelayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

```

接着替换代码如下:

```
	FragmentManager fm = getSupportFragmentManager();
	FragmentTransaction ft = fm.beginTransaction();
	ft.replace(R.id.framelayout,fragment);
	ft.commit();
```
首先获取FragmentManager，使用FragmentManager开始一个事物，通过该事物替换布局中的fragment，之后提交即可。


# 模拟返回栈
只需要在事物提交前添加ft.addToBackStack(null)即可。

```
FragmentManager fm = getSupportFragmentManager();
FragmentTransaction ft = fm.beginTransaction();
ft.replace(R.id.framelayout,fragment);
ft.addToBackStack(null);
ft.commit();
```
# Fragment的生命周期

由于无法显示图片的原因，这里只是用文字列举出从头到尾的生命周期：

onAttach() -> onCreate() -> onCreateView -> onActivityCreated() -> onStart() -> onResume() -> 碎片激活 -> onPause() -> onStop() -> onDestroyView() -> onDestroy() -> 碎片消除

