# 简单用法
如果要使用RecylcerView控件，必须要添加相应的依赖：`compile 'com.android.support:recylcerview-v7:25.+'`,之后就可以正常使用，首先要在想要使用的Activity的布局文件中加入RecyclerView，并为其添加一个id.

```
<android.support.v7.widget.RecyclerView
        android:id="@+id/simple_rv"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

接着为该RecyclerView写适配器的类
```
public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> tmps;
    private Context mContext;

    public RVAdapter(Context context,ArrayList<String> tmps){
        mContext = context;
        this.tmps = tmps;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_simple_rv,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyHolder)holder).textView.setText(tmps.get(position));
    }

    @Override
    public int getItemCount() {
        return tmps.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tmp);
        }
    }
}

```

该类要集成RecyclerView.Adapter，并且重写其中的onCreateViewHolder，onBindVIewHolder,getItemCount方法。其中onCreateViewHolder方法是在创建适配器的时候调用该方法，为适配器创建ViewHolder，ViewHolder的作用是复用，onBindeViewHolder的作用是控制position位置的View的显示内容以布局等等。getItemCount就是View的数量。

最后使用findViewById获取到RecyclerView控件后,为该控件设置Adapter即适配器，适配器的作用是，控制这个RecyclerView显示的内容和布局。再然后还要为其设置布局控制器，不然内容是显示不出来的。一般是设置线性布局控制器。其控制器其实还有其他很多方式，如网状 GridLayoutManager等


```
simpleRv.setAdapter(new RVAdapter(this,lists));
simpleRv.setLayoutManager(new LinearLayoutManager(this));
```


# 横向滚动
其实就是设置布局控制器的时候，将该布局控制器的方向控制为横向

```
LinearLayoutManager layoutManager = new LinearLayoutManager(this);
layoutManager.setOrien
```

# 瀑布流

瀑布流其实就是网络布局的改进版，网络布局的每个子View都是对齐，而瀑布流的子VIew，如果每个子View的宽高不同，就可以不对齐排列。

使用方式依然是给Adapter设置布局方式

```
StaggeredGridLayoutManager sLayoutManager =
        new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

```
该参数中，第一个是网格几列，第二个参数是横向滚动还是垂直滚动。


# 点击事件

RecylcerView的点击事件可以有两种方式设置，一种是在onCreateViewHolder中设置，一种是在onBindViewHolder中设置。

1. onCreateViewHolder设置方法

```
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_simple_rv,parent,false);
final MyHolder holder =  new MyHolder(view);
holder.textView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(mContext,"点击了第" + holder.getAdapterPosition() + "个",Toast.LENGTH_SHORT).show();
    }
});

return holder;
}
```
2. onBindViewHolder中设置。

```
public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
((MyHolder)holder).textView.setText(tmps.get(position));
((MyHolder)holder).textView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        
    }
});
}
```

# 头尾布局
这里只说明头部布局是怎么弄的，至于尾部布局，那当然是跟头部布局一个套路啦，所以就不用讲了。

头部布局的设置方式，其实就是在Adapter中对当前位置进行判断，如果当前位置是0，那么就是列表中的第一个，那么就将这个第一个View设置为自己想设置的头部布局样式就好了。注意要重写其中的getItemViewType方法。

如代码：

```
public class HeaderRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

    private Context mContext;
    private ArrayList<String> lists;
    private boolean headerFLag = false;

    public HeaderRVAdapter(Context context, ArrayList<String> lists){
        mContext = context;
        this.lists = lists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == TYPE_HEADER){
	    //根据viewType的不同，返回不同的MyHolder，其中viewType的值就是由getItemViewType决定的，在调用该方法的时候，首先调用的是getItemViewType.
            headerFLag = true;
            view = LayoutInflater.from(mContext).inflate(R.layout.header_view,parent,false);
        }else{
            headerFLag = false;
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_simple_rv,parent,false);
        }
        return new MyHolder(view);
    }

    //该方法必须重写，否则在onCreateViewHolder中不会知道viewType的类型。
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
	//使用getItemViewType来判断类型，而不使用headerFLag的原因是，headerFlag并不是随时改变的，如果所有的子View都加载完后，headerFlag的值就可能不会改变，这样就会导致bug。	
        if(getItemViewType(position) == TYPE_HEADER)return;
        ((MyHolder)holder).textView.setText(lists.get(position-1));//注意这里的实际位置应该要-1的
    }

    @Override
    public int getItemCount() {
        return lists.size() + 1;  //因为headerView所以务必要加1
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            if(headerFLag)return;
            textView = (TextView) itemView.findViewById(R.id.tmp);
        }
    }
}

```


# 网格流中的headerView

在线性流布局中添加headerView其实就是给第一个子View添加了一个headerView，但是放在Grid布局中，这种方式就无法正确实现了。所以需要更改思路。


首先来看这个方法：

```
gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
    @Override
    public int getSpanSize(int position) {
        return getItemViewType(position) == TYPE_HEADER
                ? gridManager.getSpanCount() : 1;
    }
});
```
这个方法中的getSpanSize的返回值，决定了每个position所占用的单元格个数。所以通过该方法就可以为Grid布局设置头view.

那么对于Grid，重写Adapter中的下列方法即可：

```
@Override
public void onAttachedToRecyclerView(RecyclerView recyclerView) {
super.onAttachedToRecyclerView(recyclerView);
RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
if(manager instanceof GridLayoutManager) {
    final GridLayoutManager gridManager = ((GridLayoutManager) manager);
    gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            return getItemViewType(position) == TYPE_HEADER
                    ? gridManager.getSpanCount() : 1;
        }
    });
}
}
```
注意如果使用这种方式，那么setAdapter一定要在setLayoutManager之后，否则没有效果。

# 瀑布流中的headerView

重写Adapter中的另一个方法：

```
public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
    super.onViewAttachedToWindow(holder);
    ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
    if(lp != null
            && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
    }
}

```

# 设置分割线

## 简单用法
简单用法是RecyclerView自带的，代码如下：

```
simpleRv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
```

其中DividerItemDecoration中第二个参数是RecyclerView的方向，如果RecyclerView的方向是Vertical，那么就要传入VERTICAL.

## 复杂用法

复杂方法其实并不复杂，其实就是重写的RecyclerView.ItemDecoration的类而已，代码如下：

```
class MyDecoration extends RecyclerView.ItemDecoration{


	int height = 8;
	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
	    super.onDraw(c, parent, state);

	    int left = parent.getPaddingLeft();
	    int right = parent.getWidth() - parent.getPaddingRight();
	    final int childCount = parent.getChildCount();
	    for (int i = 0; i < childCount; i++) {
		View now = parent.getChildAt(i);

		//获得child的布局信息
		RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) now.getLayoutParams();
		int top = now.getBottom() + params.bottomMargin;
		int bottom = top + height; //假设是2dp

		//开始画线
		Paint mPaint = new Paint();
		mPaint.setColor(Color.parseColor("#cccccc"));
		c.drawRect(left,top,right,bottom,mPaint);

		//如果要画drawable,可以通过drawable的setBounds来设置所画的位置，再通过drawable的draw方法，将drawable画出来就行
	    }
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		//画横线，就是往下偏移一个分割线的高度，而如果是画竖线，就是将宽度传给第三个参数，即：outRect.set(0,0,width,0);
		outRect.set(0, 0, 0, height);

	}
}
```
解释一下，其中画线是在onDraw方法中，在onDraw方法中，要先找到固定的left,right的大小，如果是horizontal方向的，就要找到固定的top,bottom的大小。接着通过paraent.getChildCount来获取RecyclerView中项目个数，然后通过for方法，画出count-1个分割线，其中每个分割线对应的top和bottom，top就是每个view对应的bottom的值，而分割线的bottom对应的就是top + 分割线的高度。这些值找到后，就可以通过Canvas来进行画线，也可以通过drawable来划线。具体查看代码注释。

而对于getItemOffsets方法，该方法就是将每个view进行位移，因为画出来的分割线是有宽度或高度的，不位移的画，子View就会显示有问题。


