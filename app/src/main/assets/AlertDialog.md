# 简单用法
通过AlertDialog.Builder来进行设置
```
new AlertDialog.Builder(this)
  .setTitle("简单演示标题")
  .setMessage("简单演示内容")
  .setPositiveButton("好", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
          Toast.makeText(AlertDialogTest.this,"点击了好",Toast.LENGTH_SHORT).show();
      }
  })
  .setNegativeButton("不好", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
          //无操作就会退出。
      }
  })
  .setNeutralButton("中间级别", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
          //无操作
      }
  })
  .show();
```


# 带图标的
主要方法是setIcon()
```
new AlertDialog.Builder(this)
  .setIcon(R.drawable.test)
  .setTitle("标题")
  .setMessage("消息")
  .setPositiveButton("Ok",null)
  .show();
```

# 列表项

主要方法是setItems(CharSequence[] items, final OnClickListener listener)
```
new AlertDialog.Builder(this)
  .setIcon(R.drawable.test)
  .setTitle("列表项")
  .setItems(new String[]{"1", "2", "3"}, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
          Toast.makeText(AlertDialogTest.this,"点击了第" + which + "个",Toast.LENGTH_SHORT).show();
      }
  })
  .setPositiveButton("Ok",null)
  .show();
```

# 单选

主要方法是setSingleChoiceItems(CharSequence[] items, int checkedItem, final OnClickListener listener)
```
new AlertDialog.Builder(this)
  .setIcon(R.drawable.test)
  .setTitle("单选")
  .setSingleChoiceItems(new String[]{"1", "2", "3"}, 0,new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
          Toast.makeText(AlertDialogTest.this,"点击了第" + which + "个",Toast.LENGTH_SHORT).show();
      }
  })
  .setPositiveButton("Ok",null)
  .show();
```

# 多选

主要方法setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems,final OnMultiChoiceClickListener listener)
```
new AlertDialog.Builder(this)
  .setIcon(R.drawable.test)
  .setTitle("多选")
  .setMultiChoiceItems(new String[]{"1", "2", "3"}, new boolean[]{true, false, true}, new DialogInterface.OnMultiChoiceClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which, boolean isChecked) {

      }
  })
  .setPositiveButton("Ok",null)
  .show();
```

# 自定义View

主要方法 setView(View view)
```
View cView = LayoutInflater.from(AlertDialogTest.this).inflate(R.layout.custom_toast,null);
new AlertDialog.Builder(this)
  .setTitle("自定义View")
  .setView(cView)
  .setPositiveButton("OK",null)
  .show();
```
