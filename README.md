# Download

```css
implementation 'com.github.lienilibrary:EasyAdapter:1.0.2'
```

# Usage

### Sample 1: easy use

```
BaseEasyAdapter<String> adapter=new BaseEasyAdapter<String>(R.layout.item_test) {
            @Override
            public void onBind(@NonNull EasyHolder holder, int position) {
            }
        };
        RecyclerViewHelper.create(this,R.id.recyclerView,adapter).init();
adapter.notifyLoadingCompleted(null);//更新数据
```

### Sample 2:  refreshing

```
//adapter已全局定义
adapter=new BaseEasyAdapter<String>(R.layout.item_test) {
    @Override
    public void onBind(@NonNull EasyHolder holder, int position) { }
};
RecyclerViewHelper.create(this,R.id.recyclerView,adapter)
        .setOnFirstRefreshListener(new OnFirstRefreshListener() {
            @Override
            public void onFirstRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> strings=new ArrayList<>();
                        for (int i=0;i<15;i++) strings.add("string "+i);
                        adapter.notifyLoadingCompleted(strings);
                    }
                },2000);
            }
        })
        .init()
        .firstRefresh();
```

### Sample 3: refreshing and loading more

```
//adapter已全局定义
adapter=new BaseEasyAdapter<String>(R.layout.item_test) {
    @Override
    public void onBind(@NonNull EasyHolder holder, int position) {
        holder.getView(R.id.tvText,TextView.class).setText(getItem(position));
    }
};
RecyclerViewHelper.create(this,R.id.recyclerView,R.id.swipeRefreshLayout, adapter)
        .setOnFirstRefreshListener(new OnFirstRefreshListener() {
            @Override
            public void onFirstRefresh() {
            		//page 已全局定义
                page=1;
                getData();
            }
        })
        .setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData();
            }
        })
        .disableRefreshLayout()
        .init().firstRefresh();
```

```
private void getData(){
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            List<String> strings=new ArrayList<>();
            for (int i=0;i<15;i++) {
                strings.add("string "+i);
            }
            adapter.notifyLoadingCompleted(strings,page==1,page==3);
            page++;
        }
    },2000);
}
```

#### item click & long click

```
//click
adapter.setOnItemClickListener(new OnItemClickListener() {
    @Override
    public void onClick(View v, int position) {

    }
});
//long click
adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
    @Override
    public void onLongClick(View v, int position) {

    }
});
```

#### child item click & long click

```
//child click
adapter.addOnItemChildClickListener(R.id.tvText, new OnItemChildClickListener() {
    @Override
    public void onClick(View v, int position) {

    }
});
//child long click
adapter.addOnItemChildLongClickListener(R.id.tvText, new OnItemChildLongClickListener() {
    @Override
    public void onLongClick(View v, int position) {
        
    }
});
```

#### Globe Config

```
//可选 全局布局配置(空,加载中(中心，底部)，异常(中心，底部))
EasyAdapterSetting.initLayout();
//可选 全局图片配置(空,异常)
EasyAdapterSetting.initImage();
//可选 全局文字配置(空,底部没有更多)
EasyAdapterSetting.initText();
```

##### Specific

```
AdapterSetting setting=new AdapterSetting();
setting.setEmptyLayoutResId(R.layout.view_empty);

BaseEasyAdapter<String> adapter=new BaseEasyAdapter<String>(R.layout.item_test,setting) {
    @Override
    public void onBind(@NonNull EasyHolder holder, int position) { }
};
```

###### or

```
adapter.setEmptyImage();
adapter.setEmptyText();
```

