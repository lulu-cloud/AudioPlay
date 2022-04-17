
import java.applet.Applet;  
import java.applet.AudioClip; 
import java.net.MalformedURLException;  
import java.net.URL;  
import java.io.*;
import javax.swing.text.*;
import javax.swing.text.StyleContext.NamedStyle;
import javax.swing.*;
import static javax.swing.JFrame.*; //����JFrame�ľ�̬����
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.util.*;
import java.util.Timer;


class audioplay{//����������
	   AudioClip adc;// ������Ƶ��������
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
	   void play(){     //��ʼ����
	           adc.play();
	           playFlag=true;
	   }   
	   void stop(){     //ֹͣ����
	           adc.stop();   
	           playFlag=false;
	   }
	   void loop() {
		   adc.loop();
	   }
	}

class music
{
	LinkedList<String> Lyricslist=new LinkedList<String>();//�������ֺ͸����Ϣ
	LinkedList<String> Lyrics=new LinkedList<String>();//�����Ϣ
	LinkedList<Integer> Lyricstime=new LinkedList<Integer>();//���ʱ����Ϣ
	String name;//��������
	String songer;//����
	String time;//��ʱ��
	String playFileDirectory;//�ļ�·��
	music(String na,String ti)
	{
		this.name=na;
		this.time=ti;
	}
	void loadlyrics(String path,String name) {
		//�����ֱ�Ϊ������·���͸���������
		//������ֺ͸��
		 int n=0;
		 String Lyricspath=path+name+".txt";//Ѱ�Ҹ��·��
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
	            // һ�ζ���һ������
				if(n==0) {
					Lyricslist.add(line);//������
		            n++;
				}
				else {
		            split=line.split(" ");
		            Lyricslist.add(split[0]);//ÿһ�и�ʿ�ʼʱ��
		            Lyricslist.add(split[1]);//ÿһ�и�ʽ���ʱ��
		            Lyricslist.add(split[2]);//ÿһ�и��
		            n++;
				}
			}
		 } catch (IOException e1) {}
		 
		 int x=0;
         for(x=0;x<(Lyricslist.size()-1)/3;x++) {
        	
        	 Lyricstime.add(Integer.parseInt(Lyricslist.get(x*3+1)));
        	 Lyricstime.add(Integer.parseInt(Lyricslist.get(x*3+2)));
        	 Lyrics.add(Lyricslist.get(x*3+3)+"\n");
        	
         }//ÿ���ʼ����Ŀ�ʼ�ͽ���ʱ��
	}
	int timetransform(String s){//������ʱ��ת��Ϊ����
		String []t= {"",""};
		t=s.split(":");
		return Integer.parseInt(t[0])+Integer.parseInt(t[1]);
	}
}


class MyExtendsJFrame extends JFrame implements ActionListener,MouseListener
{ //������
	
	JLabel background;//�����ؼ�
	JLabel playTime;//���Ž������ؼ�
	JLabel gif;//��ͼ
	JLabel volume;
	
	JButton buttonPlay;//���� ��ť
	JButton buttonStop;//ֹͣ����
	JButton buttonNext;//��һ�� ��ť
	JButton buttonPrev;//��һ�� ��ť
	JButton buttonOpen;//�� ��ť
	JButton buttonloop;// ѭ�� ��ť
	JScrollPane pane;//��ʹ�����
	JScrollBar pBar;//����������
	
	JTextPane textLyrics;//��ʿؼ�
	JList listPlayFile;//�����б�ؼ�
	Timer nTimer;//��ʱ������
	
	JTextArea textarea;//�����ؼ�
	JTextArea runtime;//��ǰ����ʱ��
	JTextArea lasttime;//������ʱ��
	JTextArea textarea1;//������
	
	Font fontlist=new Font("����",Font.PLAIN,15);
	Font fontL=new Font("����",Font.PLAIN,20);
	Font fontS=new Font("����",Font.PLAIN,15);
	Font fontLy=new Font("����",Font.PLAIN,15);
	
	audioplay audioPlay;
	LinkedList<music>mylist;//�����б�
	Vector<String> vt1=new Vector ();//������ΧVector�������ڵ�������б����������
 
	public MyExtendsJFrame()
	{		
		audioPlay=new audioplay();  //�������Ŷ���
		mylist=new LinkedList<music>();
		setTitle("������");//�����
		setBounds(100,100,1280,850);	//���ô��ڴ�С		
		setLayout(null);//�ղ���			
		init();   //��ӿؼ��Ĳ�����װ��һ������         
		setVisible(true);//��������������ִ��
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	void init()
	{//��ӵĿؼ�
		//���ñ���ͼƬ
		Icon img=new ImageIcon(".//g.jpg");     //����ͼ�����			
		background = new JLabel(img);//���ñ���ͼƬ
		background.setBounds(0,0,1280,850);//���ñ����ؼ���С
	    getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));//����ͼƬ�ؼ�������ײ�
		((JPanel)getContentPane()).setOpaque(false); //�ؼ�͸��
		
		//���ö�̬ͼ
		Icon img1=new ImageIcon(".//gif.gif");     //����ͼ�����			
		gif = new JLabel(img1);//���ñ���ͼƬ
		gif.setBounds(900,420,240,240);//���ñ����ؼ���С
	    getLayeredPane().add(gif, new Integer(Integer.MIN_VALUE));//����ͼƬ�ؼ�������ײ�
		add(gif);
		gif.setVisible(false);
		
	   
		 //��ǰ��������ʱ��	�ؼ�
	    runtime=new JTextArea("00:00");
	    runtime.setBounds(100,750,100,50);
	    runtime.setForeground(Color.white);
	    runtime.setOpaque(false);
	    add(runtime);
		 
	    //������ʱ��
	    lasttime=new JTextArea("00:00");
	    lasttime.setBounds(1080,740,100,50);
	    lasttime.setForeground(Color.white);
	    lasttime.setOpaque(false);
	    add(lasttime);
	    
	    //����	
		buttonPlay=new JButton();//���     ����   ��ť
	    buttonPlay.setBounds(620,720,50,50); //���ò��Ű�ť��С
	    Icon icon=new ImageIcon(".//����.png");//��������ͼ�����
	    buttonPlay.setIcon(icon);	      //���ò���ͼ��
	    buttonPlay.setBorderPainted(false);
	    buttonPlay.addActionListener(this);//��ӵ��������¼�
	    buttonPlay.setContentAreaFilled(false);//����͸��
	    add(buttonPlay);//��Ӳ��Ű�ť��������
	    
	    //ֹͣ		�ؼ�
	    buttonStop=new JButton();//���     ����   ��ť
	    buttonStop.setBounds(620,720,50,50); //���ò��Ű�ť��С
	    Icon iconstop=new ImageIcon(".//ֹͣ����.png");//��������ͼ�����
	    buttonStop.setIcon(iconstop);	      //���ò���ͼ��
	    buttonStop.setBorderPainted(false);
	    buttonStop.setVisible(false);
	    buttonStop.addActionListener(this);//��ӵ��������¼�
	    buttonStop.setContentAreaFilled(false);//����͸��
	    add(buttonStop);//��Ӳ��Ű�ť��������
	      
	    //��һ��	�ؼ�
	    buttonNext=new JButton();//���    ��һ��    ��ť
	    buttonNext.setBounds(680,720,50,50); //���ò��Ű�ť��С
	    Icon icon1=new ImageIcon(".//��һ�׸���.png");//��������ͼ�����
	    buttonNext.setIcon(icon1);	      //���ò���ͼ��
	    buttonNext.setBorderPainted(false);
	    buttonNext.setContentAreaFilled(false);//����͸��
	    buttonNext.addActionListener(this);
	    add(buttonNext);//��Ӳ��Ű�ť��������
	      
	      //��һ��	�ؼ�
	    buttonPrev=new JButton();
	    buttonPrev.setBounds(560,720,50,50); 
	    Icon icon2=new ImageIcon(".//��һ�׸���.png");
	    buttonPrev.setIcon(icon2);	      
	    buttonPrev.setBorderPainted(false);
	    buttonPrev.addActionListener(this);
	    buttonPrev.setContentAreaFilled(false);//����͸��
	    add(buttonPrev);
	      
	     //���ļ�  ��ť
	    buttonOpen=new JButton();
	    buttonOpen.setBounds(880,720,50,50); //���ò��Ű�ť��С
	    Icon icon3=new ImageIcon(".//��.png");//��������ͼ�����
	    buttonOpen.setIcon(icon3);	      //���ò���ͼ��
	    buttonOpen.setContentAreaFilled(false);//����͸��
	    buttonOpen.setBorderPainted(false);
	    buttonOpen.addActionListener(this);
	    add(buttonOpen);//��Ӳ��Ű�ť��������
       

	    //ѭ����ť
	    buttonloop=new JButton();
	    buttonloop.setBounds(400,720,50,50); 
	    Icon iconloop=new ImageIcon(".//ѭ������.png");
	    buttonloop.setIcon(iconloop);	
	    buttonloop.setContentAreaFilled(false);//����͸��
	    buttonloop.setBorderPainted(false);
	    buttonloop.addActionListener(this);
	    add(buttonloop);
	    
	      //������	�ؼ�  
	    textarea= new JTextArea("���Ǹ���");
	    textarea.setBounds(150,50,200,30);
	    textarea.setForeground(Color.white);//��ʿؼ�������ɫ
	    textarea.setOpaque(false);
	    textarea.setFont(fontL);
	    add(textarea);
	    
	    //������
	    textarea1=new JTextArea("���Ǹ�����");
	    textarea1.setBounds(150,80,100,30);
	    textarea1.setForeground(Color.white);//��ʿؼ�������ɫ
	    textarea1.setOpaque(false);
	    textarea1.setFont(fontS);
	    add(textarea1);
	    getLayeredPane().add(textarea1, new Integer(Integer.MIN_VALUE+5));
	    
	      //�����б�		�ؼ�
	    listPlayFile=new JList();	  //���������б� 
	    listPlayFile.setBounds(880,50,250,100); //���ò����б��С
	    listPlayFile.setOpaque(false);//���ò����б�͸��
	    listPlayFile.setBackground(new Color(0,255,0,50));
	    listPlayFile.setForeground(Color.white);//���ò����б�������ɫ
	    listPlayFile.setFont(fontlist);
	    add(listPlayFile);       //��Ӳ����б�������
	    listPlayFile.addMouseListener(this);//��Ӳ����б��˫���¼�����
	      
	      //���		�ؼ�
	    textLyrics=new JTextPane();   //������ʿؼ�    
	    textLyrics.setBounds(130,120,250,500);//���ø�ʿؼ���С
	    textLyrics.setForeground(Color.white);//��ʿؼ�������ɫ
	    textLyrics.setOpaque(false);//��ʿؼ�͸��
	    textLyrics.setFont(fontLy);
	    add(textLyrics);    //��Ӹ�ʿؼ���������
	    textLyrics.setText("�����Ǹ������");
	    getLayeredPane().add(textLyrics, new Integer(Integer.MIN_VALUE+5));
	    
	    //JScrollBar bar=Lyricspane.getVerticalScrollBar();
	    //������ �ؼ�
//	    pane=new JScrollPane(textLyrics);//����ʷ��������
//	    pane.setBounds(20,50,250,500);
//	    pane.setOpaque(false);
//	    pane.getViewport().setOpaque(false);//�ؼ�͸��  
//	    pane.setBorder(null);//�߿�����
//	    pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//	    pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//	    add(pane);
//	    getLayeredPane().add(pane, new Integer(Integer.MIN_VALUE+5));//�������ؼ�������ײ�
//	    
//	    pBar=pane.getVerticalScrollBar();//�õ�����������

	      //������	�ؼ�
	    Icon img4=new ImageIcon(".//time.jpg");     //����ͼ�����			
		playTime = new JLabel(img4);//���ñ���ͼƬ
		playTime.setBounds(0,700,0,3);//���ñ����ؼ���С
	    getLayeredPane().add(playTime, new Integer(Integer.MIN_VALUE+5));//����ͼƬ�ؼ�������ײ�

	}
	//ʱ��ת����
	public String timechange(int second)
	{
		int min=second/60;
		int sec=second-min*60;
		String rt1=String.format("%02d",min);
    	String rt2=String.format("%02d",sec);
    	String rt=rt1+":"+rt2;
    	return rt;
	}
	
	//��ʱ������
	public  void timerFun(int nPlayTime,music M)
	{//��ʱ������
		if(nTimer!=null){nTimer.cancel();}//�Ѿ��ж�ʱ����ر�
        nTimer = new Timer();//������ʱ��
        nTimer.schedule(new TimerTask(){  //������
        	int t;
        	int eachPlayTime=1280/nPlayTime;
        	int starttime=0;
            int second=0;
        	int length=M.Lyrics.size();//��ʵ�����
            int value=0;
            
            public void run() { //��ʱ��������
            	playTime.setBounds(0, 700, starttime+=eachPlayTime, 3);
            	String rt=timechange(second);
            	runtime.setText(rt);
            	second=second+1;
        	 //   value=value+500/();
        	  //  pBar.setValue(value);
        	    //
            	int n=0;
            	textLyrics.setText("");     //ˢ�¸����

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
	@SuppressWarnings("unchecked")//���Ծ���
	public void actionPerformed(ActionEvent e)
	{
		
		//����ǲ��Ű�ť����¼�
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
	            audioPlay.SetPlayAudioPath("file:"+mylist.get(0).name+".wav");//���ò����ļ�
				audioPlay.play();//��ʼ����
				gif.setVisible(true);
				timerFun(iMusicTime,mc);//�򿪶�ʱ�����ƶ�������
			}
			
		}
	
		//��ͣ �¼�
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
	
		//��һ��
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
			audioPlay.SetPlayAudioPath("file:"+mylist.get(Index).name+".wav");//���ò����ļ�
			audioPlay.play();//��ʼ����
			gif.setVisible(true);
			music mc=mylist.get(Index);
			textarea.setText(mc.name);//����������
			lasttime.setText(mc.time);//������ʱ������
			textarea1.setText(mc.Lyricslist.get(0));//���ָ���
			File file=new File(mc.name+".wav"); 
            int iMusicTime=(int)file.length()/1024/173;
			timerFun(iMusicTime,mc);//���¼�ʱ��
			buttonPlay.setVisible(false);
		}
		//��һ��
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
			audioPlay.SetPlayAudioPath("file:"+mylist.get(Index).name+".wav");//���ò����ļ�
			audioPlay.play();//��ʼ����
			gif.setVisible(true);
			music mc=mylist.get(Index);
			textarea.setText(mc.name);//����������
			lasttime.setText(mc.time);//������ʱ������
			textarea1.setText(mc.Lyricslist.get(0));//���ָ���
			File file=new File(mc.name+".wav"); 
            int iMusicTime=(int)file.length()/1024/173;
			timerFun(iMusicTime,mc);//���¼�ʱ��
			buttonPlay.setVisible(false);
		}
		//����Ǵ��ļ���ť����¼�
		if(e.getSource()==buttonOpen){

			 FileDialog openFile=new FileDialog(this,"���ļ�");//�������ļ��Ի���			
			 openFile.setVisible(true);//�Ի���ɼ�
			 String playFileName=openFile.getFile();//��ȡ�򿪵��ļ���������׺
			 String playName=playFileName.substring(0, playFileName.lastIndexOf("."));//���Ҫ���ŵĸ�����
			 String playFileDirectory1=openFile.getDirectory();//��ȡ�򿪵��ļ�·��
			 String playFile=playFileDirectory1+playFileName;//������·��+�ļ���
			 
			 buttonPlay.setVisible(false);
			 buttonStop.setVisible(true);
			 audioPlay.SetPlayAudioPath("file:"+playFile);//���ò����ļ�		 			 
			 audioPlay.play();
			 gif.setVisible(true);
			 File file=new File(playFileName); 
	         int iMusicTime=(int)file.length()/1024/173;
			 music mc=new music(playName,timechange(iMusicTime));
	         buttonPlay.setVisible(false);
			 mc.playFileDirectory=playFileDirectory1;
			 mylist.addFirst(mc);
			 textarea.setText(playName);//����������
			 mc.loadlyrics(mc.playFileDirectory, playName);
			 textarea1.setText(mc.Lyricslist.get(0));//���ָ���
			 int number0=mylist.size();
			 
			 lasttime.setText(mc.time);//������ʱ������
		     timerFun(iMusicTime,mc);//���¼�ʱ��
			 
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
			 
			 Vector  vt=new Vector ();	//����Vector����ͨ��add������Ӷ���
			 Vector  vt0=new Vector ();	//����Vector���������������ĸ�������
			 for(int k=0;k<number0;k++)
			 {
				 int j=k+1;
				 vt.add(j+"��"+mylist.get(k).name+" "+mylist.get(k).time);
				 vt0.add(mylist.get(k).name);
			 }
			 vt1=vt0;
			 listPlayFile.setListData(vt);
			 audioPlay.play();//��ʼ����
			 buttonPlay.setVisible(false);
		}
	}
	
	

		public void mousePressed(MouseEvent e){}//�������
		public void mouseReleased(MouseEvent e){}//�ͷ����
		public void mouseEntered(MouseEvent e){}//������
		public void  mouseExited(MouseEvent e){}//����뿪
		public void  mouseClicked(MouseEvent e){//������		
			 if (e.getClickCount() == 2) {//�����������������
				 if(e.getSource()==listPlayFile){//����¼�Դ�ǲ����б����ڲ����б�ؼ���˫������ִ�С�
				 //���˫�������б��еĴ��룬�����ȡ�����������Ҳ��š�
					 int index=listPlayFile.getSelectedIndex();
					 String str=vt1.get(index);
					 music mc=mylist.get(index);
					 buttonPlay.setVisible(false);
					 buttonStop.setVisible(true);
					 textarea.setText(str);//����������
					 textarea1.setText(mc.Lyricslist.get(0));
					 audioPlay.SetPlayAudioPath("file:"+str+".wav");//���ò����ļ�
					 audioPlay.play();//��ʼ����
					 gif.setVisible(true);
					 lasttime.setText(mc.time);//������ʱ������
					 int itime=mc.timetransform(mc.time);
					 timerFun(itime,mc);
				 } 
			 }	
	    }
		
}

//������
public class MusicPlay{
@SuppressWarnings("unchecked")//���Ծ���
    	public static void main(String[] args) { 
        MyExtendsJFrame frame=new MyExtendsJFrame();//����������򴰿� 
    }
}
