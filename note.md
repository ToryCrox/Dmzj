## DMZJ Api字段

> 分类  http://v2.api.dmzj.com/0/category.json
> http://v2.api.dmzj.com/classify/filter.json
> http://v2.api.dmzj.com/classify/" + param + ".json

图片需要header字段
```
Referer", "http://images.dmzj.com/
```

源码位置
```java
AppBeanUtils.startCartoonBrowseActivity
ReadHelper.onReadChapter
ReadModelHelper
LocalImageManager.getImageWrappers

URLPathMaker
```

