安卓中数据存储有三种，File,SharedPreferences和SQLite，在这个文档里面只介绍File和SharedPreferenced

# File

默认存储在/data/data/<packageName>/files文件夹下
直接查看代码：
写入：
```
String data = "some data";
FileOutputStream out = null;
BufferedWriter writer = null;
try {
    //通过openFileOutput来获取/data/data/<packageName>/files/tmp.txt文件流
    //第二个参数是模式，一般可取两个值Context.MODE_PRIVATE和Context.MODE_APPENED，其中第一个表示，所写内容会覆盖之前存在的文件内容，第二个则为追加内容。
    out = openFileOutput("tmp.txt", Context.MODE_PRIVATE);
    writer = new BufferedWriter(new OutputStreamWriter(out));
    //使用writer.write()方法来写入数据
    writer.write(data);
} catch (FileNotFoundException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}finally {
    try{
        if(writer != null){
            //关闭流
            writer.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

其实上述代码总结一下就是三行代码：
```
//取得输入流
Writer writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("tmp.txt",Context.MODE_PRIVATE)))；
//写入内容
writer.write(data);
//关闭流
writer.close();
```

读取：
```
StringBuilder outData = new StringBuilder();
FileInputStream in = null;
BufferedReader reader = null;
try{
    String line;
    //读取输入流
    in = openFileInput("tmp.txt");
    reader = new BufferedReader(new InputStreamReader(in));

    //读取文件内容
    while((line = reader.readLine())!=null){
        outData.append(line);
    }

    Toast.makeText(StorageTest.this,"读取出的数据是:" + outData.toString(),Toast.LENGTH_SHORT).show();
} catch (FileNotFoundException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}finally {
    try{
        if(reader != null){
            reader.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

总结：
```
reader = new BufferedReader(new InputStreamReader(openFileInput("tmp.txt")));
while((line = reader.readLine())!=null){
    outData.append(line);
}
reader.close();
```

# SharedPreferences
该存储方式是用键值对形式来存储的，如果要使用该存储方式，就要先获取该类型对象，默认存储的路径是/data/data/<packageName>/shared_prefs/目录下，获取对象有三种方式：

1. Context.getSharedPreferences(String name,int Mode)第一个参数是获取文件名，第二个为模式，目前只有MODE_PRIVATE可选。
2. Activity.getPreferences(int mode)，只需要传入模式，默认文件名是当前活动名
3. PreferenceManager.getDefaultSharedPreferences(int mode)，默认使用活动包名来命名文件。

而写入的话就简单了，需要获取一个SharedPreferences.Editor对象：

```
SharedPreferences sp = getPreferences(MODE_PRIVATE);
SharedPreferences.Editor editor = sp.edit();
editor.putString("tmp","123");
editor.apply();//或者使用editor.commit();两者区别在于apply没返回值，无法得知提交是否成功，不过apply效率比commit高。
```

读取则不需要借助Editor类，相对更简单：

```
SharedPreferences sp2 = getPreferences(MODE_PRIVATE);
String value = sp2.getString("tmp","默认值");
```
