# 简单用法
```
Toast.makeText(this,"简单演示",Toast.LENGTH_SHORT).show();
```
其中，第一个参数是context,第二个参数是显示内容，第三个参数是时长，时长可供两种:`Toast.LENGTH_SHORT`和`Toast.LENGTH_LONG`.


# 自定义位置

```
Toast toast = Toast.makeText(this,"简单演示",Toast.LENGTH_SHORT);
toast.setGravity(Gravity.CENTER,0,0);
toast.show();
```
主要代码为第二行的 `toast.setGravity(Gravity.CENTER,0,0)`,第一个参数为位置参数，其中可选的有`TOP,BOTTOM,LEFT,RIGHT`以`CENTER_VERTICAL`等等，并且该参数支持`|`操作，如`Gravity.TOP|Gravity.LEFT`.第二三个参数就是相对于当前位置进行的x,y方向的位移。


# 带图片效果
```
toast = Toast.makeText(getApplicationContext(),"带图片的Toast", Toast.LENGTH_LONG);
toast.setGravity(Gravity.CENTER, 0, 0);
LinearLayout toastView = (LinearLayout) toast.getView();
ImageView imageCodeProject = new ImageView(getApplicationContext());
imageCodeProject.setImageResource(R.drawable.icon);
toastView.addView(imageCodeProject, 0);
toast.show();
```

其实带图片的效果只是向toast自带的view中加了个图片，看其中的addView方法就知道了，其实这个效果也可以用自定义的效果实现，如下

# 自定义效果

```
LayoutInflater inflater = getLayoutInflater(); 
View customView = inflater.inflate(R.layout.custom,null); 
//省略对customView的操作
toast = new Toast(getApplicationContext()); 
toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40); 
toast.setDuration(Toast.LENGTH_LONG); 
toast.setView(customView); 
toast.show(); 
```
自定义View只是给Toast添加了一个layout，实现方式也比较简单，主要代码是setView创建一个layout就可以了。
要点是 Toast是使用new方法生成的，而不是使用makeText。

如果想要的自定义效果中的背景圆框效果，可使用drawable效果实现。

