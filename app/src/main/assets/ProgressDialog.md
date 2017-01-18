# 简单用法

```
ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("标题");
        dialog.setMessage("正在加载");
        dialog.show();
```

# 方法
* setTitle： 设置标题
* setMessage: 设置内容
* setCancelable: 设置是否可以点击取消
* setProgressStyle: 设置进度条样式
* setButton: 设置按钮，最多三个


# 简便用法
方式一
```
new ProgressDialog(this).show();  
```
方式二：使用静态方式创建并显示，这种进度条只能是圆形条,设置title和Message提示内容
```
ProgressDialog dialog2 = ProgressDialog.show(this, "提示", "正在登陆中");
```
方式三：使用静态方式创建并显示，这种进度条只能是圆形条,这里最后一个参数boolean indeterminate设置是否是不明确的状态
```
ProgressDialog dialog3 = ProgressDialog  
            .show(this, "提示", "正在登陆中", false);  
```

方式四 使用静态方式创建并显示，这种进度条只能是圆形条,这里最后一个参数boolean cancelable 设置是否进度条是可以取消的
```
ProgressDialog dialog4 = ProgressDialog.show(this, "提示", "正在登陆中",  
            false, true);  
```
方式五 使用静态方式创建并显示，这种进度条只能是圆形条,这里最后一个参数 DialogInterface.OnCancelListener cancelListener用于监听进度条被取消
```
ProgressDialog dialog5 = ProgressDialog.show(this, "提示", "正在登陆中", true,  
            true, cancelListener);  


private OnCancelListener cancelListener = new OnCancelListener() {  

    @Override  
    public void onCancel(DialogInterface dialog) {  
        // TODO Auto-generated method stub  
        Toast.makeText(MainActivity.this, "进度条被取消", Toast.LENGTH_LONG)  
                .show();  

    }  
};  
```

# 圆形进度条并设置按钮
要点是使用setProgressStyle方法，而设置按钮只有一个方法，但是如何设置Positive和Negative呢，就是通过第一个参数，如代码所示。
```
ProgressDialog dialog2 = new ProgressDialog(this);
  dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  dialog2.setTitle("标题");
  dialog2.setMessage("正在加载");
  dialog2.setButton(DialogInterface.BUTTON_POSITIVE, "好", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

      }
  });
  dialog2.setButton(DialogInterface.BUTTON_NEGATIVE, "不好", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

      }
  });
  dialog2.show();
```

# 进度条方式
要点是通过setProgressStyle设置进度条方式，并且通过setMax设置进度条总大小，而增长过程是通过incrementProgressBy来进行设置，该方法是指增长多少，而不是增长到，需要注意。

```
final ProgressDialog dialog3 = new ProgressDialog(this);
    dialog3.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    dialog3.setMax(100);
    dialog3.setTitle("标题");
    dialog3.setMessage("正在加载");
    dialog3.setButton(DialogInterface.BUTTON_POSITIVE, "好", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });
    dialog3.setButton(DialogInterface.BUTTON_NEGATIVE, "不好", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });
    dialog3.show();

    new Thread(new Runnable() {
        @Override
        public void run() {
            int i = 0;
            while(i<=100){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                dialog3.incrementProgressBy(1);
            }
        }
    }).start();
```
