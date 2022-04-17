# 一个简单的播放器项目


注意：正常播放所有wav格式的歌曲，但是要显示歌词且在列表中显示必须有歌词文本文件，给出了《红昭愿》《我和我的祖国》《山外小楼也听雨》《贝贝》四首歌的文本。在未点击这特定四首歌而点击其他歌，列表不会显示，点击四首歌任一首即可显示列表。


**实验功能：
1、	歌词随时间高亮  
2、	播放停止功能正常  
3、	上下首切换  
4、	打开文件将歌曲置于列表  
5、	双击列表歌曲信息将点击歌曲播放  
6、	播放歌曲同时播放给出的gif动图

**未实现功能：
在歌词较长时，不会随着时间滚动。  
比如《红昭愿》这首歌曲，歌词较长，试图添加滚动条将其放入计时器中，但是因为设计的歌词高亮模块，每次刷新歌词区域会将JTextPane textLyrics
中的滚动条置0，导致每次滚动后回到初始值，又随定时器滚动，出现“闪烁”，没有解决这个问题，能力有限，所以决定不加滚动条功能。
