
import java.applet.Applet;  
import java.applet.AudioClip; 
import java.net.MalformedURLException;  
import java.net.URL;  
import java.io.*;
import javax.swing.text.*;
import javax.swing.text.StyleContext.NamedStyle;
import javax.swing.*;
import static javax.swing.JFrame.*; //引入JFrame的静态常量
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.util.*;
import java.util.Timer;


class audioplay{//播放音乐类
	   AudioClip adc;// 声音音频剪辑对象
	   URL url;
	   boolean adcFlag=false;
	   boolean playFlag=false;
	   void SetPlayAudioPath(String path){
	      try{  
	           url = new URL(path);  
	          // System.out.println(adc.toString());
	           if(adcFlag==true){adc.stop();playFlag=false;}
	           adc = Applet.newAudioClip(url);
	           adcFlag=true;
	         }
	      catch (MalformedURLException e1) {
	              e1.printStackTrace();  
	         }  
	   }
	   void play(){     //开始播放
	           adc.play();
	           playFlag=true;
	   }   
	   void stop(){     //停止播放
	           adc.stop();   
	           playFlag=false;
	   }
	   void loop() {
		   adc.loop();
	   }
	}

class music
{
	LinkedList<String> Lyricslist=new LinkedList<String>();//歌曲歌手和歌词信息
	LinkedList<String> Lyrics=new LinkedList<String>();//歌词信息
	LinkedList<Integer> Lyricstime=new LinkedList<Integer>();//歌词时间信息
	String name;//歌曲名称
	String songer;//歌手
	String time;//总时长
	String playFileDirectory;//文件路径
	music(String na,String ti)
	{
		this.name=na;
		this.time=ti;
	}
	void loadlyrics(String path,String name) {
		//参数分别为歌曲的路径和歌曲的名字
		//导入歌手和歌词
		 int n=0;
		 String Lyricspath=path+name+".txt";//寻找歌词路径
		 File filename = new File(Lyricspath);
		 InputStreamReader reader = null;
		 try {
			reader = new InputStreamReader(new FileInputStream(filename));
		 } catch (FileNotFoundException e1) {}
		 BufferedReader br = new BufferedReader(reader);
		 String line="";
		 String[] split= {"","",""};

		 try {
			while ((line = br.readLine()) != null ) {
	            // 一次读入一行数据
				if(n==0) {
					Lyricslist.add(line);//歌手名
		            n++;
				}
				else {
		            split=line.split(" ");
		            Lyricslist.add(split[0]);//每一行歌词开始时间
		            Lyricslist.add(split[1]);//每一行歌词结束时间
		            Lyricslist.add(split[2]);//每一行歌词
		            n++;
				}
			}
		 } catch (IOException e1) {}
		 
		 int x=0;
         for(x=0;x<(Lyricslist.size()-1)/3;x++) {
        	
        	 Lyricstime.add(Integer.parseInt(Lyricslist.get(x*3+1)));
        	 Lyricstime.add(Integer.parseInt(Lyricslist.get(x*3+2)));
        	 Lyrics.add(Lyricslist.get(x*3+3)+"\n");
        	
         }//每句歌词及它的开始和结束时间
	}
	int timetransform(String s){//将歌曲时间转化为秒数
		String []t= {"",""};
		t=s.split(":");
		return Integer.parseInt(t[0])+Integer.parseInt(t[1]);
	}
}


class MyExtendsJFrame extends JFrame implements ActionListener,MouseListener
{ //窗口类
	
	JLabel background;//背景控件
	JLabel playTime;//播放进度条控件
	JLabel gif;//动图
	JLabel volume;
	
	JButton buttonPlay;//播放 按钮
	JButton buttonStop;//停止播放
	JButton buttonNext;//下一首 按钮
	JButton buttonPrev;//上一首 按钮
	JButton buttonOpen;//打开 按钮
	JButton buttonloop;// 循环 按钮
	JScrollPane pane;//歌词滚动条
	JScrollBar pBar;//滚动条控制
	
	JTextPane textLyrics;//歌词控件
	JList listPlayFile;//播放列表控件
	Timer nTimer;//定时器对象
	
	JTextArea textarea;//歌名控件
	JTextArea runtime;//当前歌曲时间
	JTextArea lasttime;//歌曲总时长
	JTextArea textarea1;//歌手名
	
	Font fontlist=new Font("宋体",Font.PLAIN,15);
	Font fontL=new Font("宋体",Font.PLAIN,20);
	Font fontS=new Font("宋体",Font.PLAIN,15);
	Font fontLy=new Font("宋体",Font.PLAIN,15);
	
	audioplay audioPlay;
	LinkedList<music>mylist;//播放列表
	Vector<String> vt1=new Vector ();//创建范围Vector对象，用于点击播放列表的索引操作
 
	public MyExtendsJFrame()
	{		
		audioPlay=new audioplay();  //创建播放对象
		mylist=new LinkedList<music>();
		setTitle("播放器");//软件名
		setBounds(100,100,1280,850);	//设置窗口大小		
		setLayout(null);//空布局			
		init();   //添加控件的操作封装成一个函数         
		setVisible(true);//放在添加组件后面执行
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	void init()
	{//添加的控件
		//设置背景图片
		Icon img=new ImageIcon(".//g.jpg");     //创建图标对象			
		background = new JLabel(img);//设置背景图片
		background.setBounds(0,0,1280,850);//设置背景控件大小
	    getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));//背景图片控件置于最底层
		((JPanel)getContentPane()).setOpaque(false); //控件透明
		
		//设置动态图
		Icon img1=new ImageIcon(".//gif.gif");     //创建图标对象			
		gif = new JLabel(img1);//设置背景图片
		gif.setBounds(900,420,240,240);//设置背景控件大小
	    getLayeredPane().add(gif, new Integer(Integer.MIN_VALUE));//背景图片控件置于最底层
		add(gif);
		gif.setVisible(false);
		
	   
		 //当前歌曲播放时间	控件
	    runtime=new JTextArea("00:00");
	    runtime.setBounds(100,750,100,50);
	    runtime.setForeground(Color.white);
	    runtime.setOpaque(false);
	    add(runtime);
		 
	    //歌曲总时长
	    lasttime=new JTextArea("00:00");
	    lasttime.setBounds(1080,740,100,50);
	    lasttime.setForeground(Color.white);
	    lasttime.setOpaque(false);
	    add(lasttime);
	    
	    //播放	
		buttonPlay=new JButton();//添加     播放   按钮
	    buttonPlay.setBounds(620,720,50,50); //设置播放按钮大小
	    Icon icon=new ImageIcon(".//播放.png");//创建播放图标对象
	    buttonPlay.setIcon(icon);	      //设置播放图标
	    buttonPlay.setBorderPainted(false);
	    buttonPlay.addActionListener(this);//添加单机关联事件
	    buttonPlay.setContentAreaFilled(false);//背景透明
	    add(buttonPlay);//添加播放按钮至窗口中
	    
	    //停止		控件
	    buttonStop=new JButton();//添加     播放   按钮
	    buttonStop.setBounds(620,720,50,50); //设置播放按钮大小
	    Icon iconstop=new ImageIcon(".//停止副本.png");//创建播放图标对象
	    buttonStop.setIcon(iconstop);	      //设置播放图标
	    buttonStop.setBorderPainted(false);
	    buttonStop.setVisible(false);
	    buttonStop.addActionListener(this);//添加单机关联事件
	    buttonStop.setContentAreaFilled(false);//背景透明
	    add(buttonStop);//添加播放按钮至窗口中
	      
	    //下一首	控件
	    buttonNext=new JButton();//添加    下一首    按钮
	    buttonNext.setBounds(680,720,50,50); //设置播放按钮大小
	    Icon icon1=new ImageIcon(".//下一首副本.png");//创建播放图标对象
	    buttonNext.setIcon(icon1);	      //设置播放图标
	    buttonNext.setBorderPainted(false);
	    buttonNext.setContentAreaFilled(false);//背景透明
	    buttonNext.addActionListener(this);
	    add(buttonNext);//添加播放按钮至窗口中
	      
	      //上一首	控件
	    buttonPrev=new JButton();
	    buttonPrev.setBounds(560,720,50,50); 
	    Icon icon2=new ImageIcon(".//上一首副本.png");
	    buttonPrev.setIcon(icon2);	      
	    buttonPrev.setBorderPainted(false);
	    buttonPrev.addActionListener(this);
	    buttonPrev.setContentAreaFilled(false);//背景透明
	    add(buttonPrev);
	      
	     //打开文件  按钮
	    buttonOpen=new JButton();
	    buttonOpen.setBounds(880,720,50,50); //设置播放按钮大小
	    Icon icon3=new ImageIcon(".//打开.png");//创建播放图标对象
	    buttonOpen.setIcon(icon3);	      //设置播放图标
	    buttonOpen.setContentAreaFilled(false);//背景透明
	    buttonOpen.setBorderPainted(false);
	    buttonOpen.addActionListener(this);
	    add(buttonOpen);//添加播放按钮至窗口中
       

	    //循环按钮
	    buttonloop=new JButton();
	    buttonloop.setBounds(400,720,50,50); 
	    Icon iconloop=new ImageIcon(".//循环副本.png");
	    buttonloop.setIcon(iconloop);	
	    buttonloop.setContentAreaFilled(false);//背景透明
	    buttonloop.setBorderPainted(false);
	    buttonloop.addActionListener(this);
	    add(buttonloop);
	    
	      //歌曲名	控件  
	    textarea= new JTextArea("这是歌名");
	    textarea.setBounds(150,50,200,30);
	    textarea.setForeground(Color.white);//歌词控件字体颜色
	    textarea.setOpaque(false);
	    textarea.setFont(fontL);
	    add(textarea);
	    
	    //歌手名
	    textarea1=new JTextArea("这是歌手名");
	    textarea1.setBounds(150,80,100,30);
	    textarea1.setForeground(Color.white);//歌词控件字体颜色
	    textarea1.setOpaque(false);
	    textarea1.setFont(fontS);
	    add(textarea1);
	    getLayeredPane().add(textarea1, new Integer(Integer.MIN_VALUE+5));
	    
	      //播放列表		控件
	    listPlayFile=new JList();	  //创建播放列表 
	    listPlayFile.setBounds(880,50,250,100); //设置播放列表大小
	    listPlayFile.setOpaque(false);//设置播放列表透明
	    listPlayFile.setBackground(new Color(0,255,0,50));
	    listPlayFile.setForeground(Color.white);//设置播放列表字体颜色
	    listPlayFile.setFont(fontlist);
	    add(listPlayFile);       //添加播放列表至窗口
	    listPlayFile.addMouseListener(this);//添加播放列表的双击事件关联
	      
	      //歌词		控件
	    textLyrics=new JTextPane();   //创建歌词控件    
	    textLyrics.setBounds(130,120,250,500);//设置歌词控件大小
	    textLyrics.setForeground(Color.white);//歌词控件字体颜色
	    textLyrics.setOpaque(false);//歌词控件透明
	    textLyrics.setFont(fontLy);
	    add(textLyrics);    //添加歌词控件至窗口中
	    textLyrics.setText("这里是歌词区域");
	    getLayeredPane().add(textLyrics, new Integer(Integer.MIN_VALUE+5));
	    
	    //JScrollBar bar=Lyricspane.getVerticalScrollBar();
	    //滚动条 控件
//	    pane=new JScrollPane(textLyrics);//将歌词放入滚动条
//	    pane.setBounds(20,50,250,500);
//	    pane.setOpaque(false);
//	    pane.getViewport().setOpaque(false);//控件透明  
//	    pane.setBorder(null);//边框隐藏
//	    pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//	    pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//	    add(pane);
//	    getLayeredPane().add(pane, new Integer(Integer.MIN_VALUE+5));//滚动条控件置于最底层
//	    
//	    pBar=pane.getVerticalScrollBar();//得到滚动条控制

	      //进度条	控件
	    Icon img4=new ImageIcon(".//time.jpg");     //创建图标对象			
		playTime = new JLabel(img4);//设置背景图片
		playTime.setBounds(0,700,0,3);//设置背景控件大小
	    getLayeredPane().add(playTime, new Integer(Integer.MIN_VALUE+5));//背景图片控件置于最底层

	}
	//时间转换器
	public String timechange(int second)
	{
		int min=second/60;
		int sec=second-min*60;
		String rt1=String.format("%02d",min);
    	String rt2=String.format("%02d",sec);
    	String rt=rt1+":"+rt2;
    	return rt;
	}
	
	//定时器函数
	public  void timerFun(int nPlayTime,music M)
	{//定时器函数
		if(nTimer!=null){nTimer.cancel();}//已经有定时器则关闭
        nTimer = new Timer();//创建定时器
        nTimer.schedule(new TimerTask(){  //匿名类
        	int t;
        	int eachPlayTime=1280/nPlayTime;
        	int starttime=0;
            int second=0;
        	int length=M.Lyrics.size();//歌词的行数
            int value=0;
            
            public void run() { //定时器函数体
            	playTime.setBounds(0, 700, starttime+=eachPlayTime, 3);
            	String rt=timechange(second);
            	runtime.setText(rt);
            	second=second+1;
        	 //   value=value+500/();
        	  //  pBar.setValue(value);
        	    //
            	int n=0;
            	textLyrics.setText("");     //刷新歌词区

                print:for(n=0;n<length;n++){
                	int m=0;
                	SimpleAttributeSet set1 ,set2;
                	set1=new SimpleAttributeSet();
	        		set2=new SimpleAttributeSet();
	            	Document doc;
	            	doc=textLyrics.getDocument();
	            	StyleConstants.setForeground(set1, new Color(0,0,255));
	            	StyleConstants.setForeground(set2, Color.white);
	            	for(m=0;m<length;m++) {
                        if(t>=M.Lyricstime.get(m*2) && t<=M.Lyricstime.get(m*2+1) && n==m) {
                            try {
                                doc.insertString(doc.getLength(), M.Lyrics.get(m), set1);
                                continue print;
                                } catch (BadLocationException e) {}
                        }
                	}
                
                	try {
                        doc.insertString(doc.getLength(), M.Lyrics.get(n), set2);
                        } catch (BadLocationException e) {}
                }
                t++;
            }
        },0,1000);
    }
	public  void timerStop()
	{
		if(nTimer!=null){nTimer.cancel();}
		playTime.setBounds(0,439,0,3);
	}
	@SuppressWarnings("unchecked")//忽略警告
	public void actionPerformed(ActionEvent e)
	{
		
		//如果是播放按钮点击事件
		if(e.getSource()==buttonPlay){
			if(mylist.size()!=0)
			{
				buttonPlay.setVisible(false);
				buttonStop.setVisible(true);
				audioPlay.SetPlayAudioPath("file:"+mylist.get(0).name);
				audioPlay.play();
				music mc=mylist.get(0);
				File file=new File(mc.name+".wav"); 
				
	            int iMusicTime=(int)file.length()/1024/173;
	            audioPlay.SetPlayAudioPath("file:"+mylist.get(0).name+".wav");//设置播放文件
				audioPlay.play();//开始播放
				gif.setVisible(true);
				timerFun(iMusicTime,mc);//打开定时器，移动进度条
			}
			
		}
	
		//暂停 事件
		if(e.getSource()==buttonStop)
		{
			buttonPlay.setVisible(true);
			buttonStop.setVisible(false);
			audioPlay.stop();
			timerStop();
		}
	
		if(e.getSource()==buttonloop) {
			audioPlay.loop();
			buttonloop.setVisible(true);
		}
	
		//上一首
		if(e.getSource()==buttonPrev)
		{
			int Index=0;
			int end=mylist.size()-1;
			for(int i=0;i<mylist.size();i++)
			{
				if(mylist.get(i).name.equals(textarea.getText()))
				{
					Index=i;
					break;
				}
			}
			if(Index==0)
			{
				Index=end;
			}
			else
				Index=Index-1;
			audioPlay.SetPlayAudioPath("file:"+mylist.get(Index).name+".wav");//设置播放文件
			audioPlay.play();//开始播放
			gif.setVisible(true);
			music mc=mylist.get(Index);
			textarea.setText(mc.name);//歌曲名更新
			lasttime.setText(mc.time);//歌曲总时长更新
			textarea1.setText(mc.Lyricslist.get(0));//歌手更新
			File file=new File(mc.name+".wav"); 
            int iMusicTime=(int)file.length()/1024/173;
			timerFun(iMusicTime,mc);//更新计时器
			buttonPlay.setVisible(false);
		}
		//下一首
		if(e.getSource()==buttonNext)
		{
			int Index = 0;
			for(int i=0;i<mylist.size();i++)
			{
				if(mylist.get(i).name.equals(textarea.getText()))
				{
					Index=i;
					break;
				}
			}
			int end=mylist.size()-1;
			if(Index==end)
			{
				Index=0;
			}
			else
				Index=Index+1;
			audioPlay.SetPlayAudioPath("file:"+mylist.get(Index).name+".wav");//设置播放文件
			audioPlay.play();//开始播放
			gif.setVisible(true);
			music mc=mylist.get(Index);
			textarea.setText(mc.name);//歌曲名更新
			lasttime.setText(mc.time);//歌曲总时长更新
			textarea1.setText(mc.Lyricslist.get(0));//歌手更新
			File file=new File(mc.name+".wav"); 
            int iMusicTime=(int)file.length()/1024/173;
			timerFun(iMusicTime,mc);//更新计时器
			buttonPlay.setVisible(false);
		}
		//如果是打开文件按钮点击事件
		if(e.getSource()==buttonOpen){

			 FileDialog openFile=new FileDialog(this,"打开文件");//创建打开文件对话框			
			 openFile.setVisible(true);//对话框可见
			 String playFileName=openFile.getFile();//获取打开的文件名包括后缀
			 String playName=playFileName.substring(0, playFileName.lastIndexOf("."));//获得要播放的歌曲名
			 String playFileDirectory1=openFile.getDirectory();//获取打开的文件路径
			 String playFile=playFileDirectory1+playFileName;//完整的路径+文件名
			 
			 buttonPlay.setVisible(false);
			 buttonStop.setVisible(true);
			 audioPlay.SetPlayAudioPath("file:"+playFile);//设置播放文件		 			 
			 audioPlay.play();
			 gif.setVisible(true);
			 File file=new File(playFileName); 
	         int iMusicTime=(int)file.length()/1024/173;
			 music mc=new music(playName,timechange(iMusicTime));
	         buttonPlay.setVisible(false);
			 mc.playFileDirectory=playFileDirectory1;
			 mylist.addFirst(mc);
			 textarea.setText(playName);//歌曲名更新
			 mc.loadlyrics(mc.playFileDirectory, playName);
			 textarea1.setText(mc.Lyricslist.get(0));//歌手更新
			 int number0=mylist.size();
			 
			 lasttime.setText(mc.time);//歌曲总时长更新
		     timerFun(iMusicTime,mc);//更新计时器
			 
			 int number=mylist.size();
			 for(int i=0;i<number;i++)
			 {	 
				 music mc1=mylist.get(i);
				 if(playFileName.equals(mc1.name))
				 {
					 mylist.remove(i);
					 break;
				 }
			 }
			 
			 Vector  vt=new Vector ();	//创建Vector对象，通过add方法添加多行
			 Vector  vt0=new Vector ();	//创建Vector对象，用于鼠标点击后的歌曲播放
			 for(int k=0;k<number0;k++)
			 {
				 int j=k+1;
				 vt.add(j+"、"+mylist.get(k).name+" "+mylist.get(k).time);
				 vt0.add(mylist.get(k).name);
			 }
			 vt1=vt0;
			 listPlayFile.setListData(vt);
			 audioPlay.play();//开始播放
			 buttonPlay.setVisible(false);
		}
	}
	
	

		public void mousePressed(MouseEvent e){}//按下鼠标
		public void mouseReleased(MouseEvent e){}//释放鼠标
		public void mouseEntered(MouseEvent e){}//鼠标进入
		public void  mouseExited(MouseEvent e){}//鼠标离开
		public void  mouseClicked(MouseEvent e){//点击鼠标		
			 if (e.getClickCount() == 2) {//如果鼠标连续点击两次
				 if(e.getSource()==listPlayFile){//如果事件源是播放列表，即在播放列表控件中双击，则执行。
				 //添加双击播放列表中的代码，比如获取歌曲名，并且播放。
					 int index=listPlayFile.getSelectedIndex();
					 String str=vt1.get(index);
					 music mc=mylist.get(index);
					 buttonPlay.setVisible(false);
					 buttonStop.setVisible(true);
					 textarea.setText(str);//歌曲名更新
					 textarea1.setText(mc.Lyricslist.get(0));
					 audioPlay.SetPlayAudioPath("file:"+str+".wav");//设置播放文件
					 audioPlay.play();//开始播放
					 gif.setVisible(true);
					 lasttime.setText(mc.time);//歌曲总时长更新
					 int itime=mc.timetransform(mc.time);
					 timerFun(itime,mc);
				 } 
			 }	
	    }
		
}

//主函数
public class MusicPlay{
@SuppressWarnings("unchecked")//忽略警告
    	public static void main(String[] args) { 
        MyExtendsJFrame frame=new MyExtendsJFrame();//创建聊天程序窗口 
    }
}
