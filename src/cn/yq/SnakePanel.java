package cn.yq;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * @author 钦
 * @create 2019-08-30 15:30
 */
//JFrame(窗口)是最底层，JPanel(轻量级容器)是置于其面上，同一个界面只有一个JFrame，一个JFrame可以放多个JPanel。
public class SnakePanel extends JPanel implements KeyListener, ActionListener {

    //加载所有的图片
    ImageIcon up = new ImageIcon("Snake/up.png");
    ImageIcon down = new ImageIcon("Snake/down.png");
    ImageIcon left = new ImageIcon("Snake/left.png");
    ImageIcon right = new ImageIcon("Snake/right.png");
    ImageIcon title = new ImageIcon("Snake/title.jpg");
    ImageIcon food = new ImageIcon("Snake/food.png");
    ImageIcon body = new ImageIcon("Snake/body.png");

    //蛇的数据结构的设计
    int[] snakex = new int[750];
    int[] snakey = new int[750];
    int len=3;
    String direction = "R"; //R右 L左 U上 D下

    //食物的数据
    Random r = new Random();
    int foodx = r.nextInt(34)*25+25;
    int foody = r.nextInt(24)*25+75;


    //游戏是否开始
    boolean isStarted = false;
    //游戏是否失败
    boolean isFaild = false;

    Timer timer = new Timer(150, this);

    //统计分数
    int score =0;


    public SnakePanel(){
        //获取焦点
        this.setFocusable(true);
        initSnake();//放置静态蛇
        this.addKeyListener(this);//添加键盘监听接口
        timer.start();
    }


    //初始化蛇
    public void initSnake(){
        isStarted = false;
        isFaild=false;
        len=3;
        direction="R";
        snakex[0] = 100;
        snakey[0] = 100;
        snakex[1] = 75;
        snakey[1] = 100;
        snakex[2] = 50;
        snakey[2] = 100;
    }


    //设置布局
    public void paint(Graphics g){
        //设置画布(游戏区域)的背景颜色
        this.setBackground(Color.BLACK);
        g.fillRect(25, 75, 850, 600);
        //设置标题
        title.paintIcon(this, g, 25, 11);

        //画蛇头
        if(direction.equals("R")){
            right.paintIcon(this, g, snakex[0], snakey[0]);
        }else if(direction.equals("L")){
            left.paintIcon(this, g, snakex[0], snakey[0]);
        }else if(direction.equals("U")){
            up.paintIcon(this, g, snakex[0], snakey[0]);
        }else if(direction.equals("D")){
            down.paintIcon(this, g, snakex[0], snakey[0]);
        }
        //画蛇身  i从1开始,因为头已经占了一个位置
        for(int i=1;i<len;i++){
            body.paintIcon(this, g, snakex[i], snakey[i]);
        }

        //画开始提示语
        if(!isStarted){
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 30));
            g.drawString("Press Space to Start/Pause", 300, 300);
        }

        //画失败提示语
        if(isFaild){
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 30));
            g.drawString("Game Over, Press Space to Start", 300, 300);
        }
        //画食物
        food.paintIcon(this, g, foodx, foody);

        //分数和长度的统计
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial",Font.PLAIN,15));
        g.drawString("Score:"+score, 750, 30);
        g.drawString("Length:"+len, 750, 50);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        //代表键盘空格键的keyCode
        if(keyCode == KeyEvent.VK_SPACE){
            if(isFaild){
                initSnake();
            }else{
                isStarted = !isStarted;
            }
//			repaint();
            //代表键盘↑
        }else if(keyCode == KeyEvent.VK_UP && !direction.equals("D")){
            direction="U";
        }else if(keyCode == KeyEvent.VK_DOWN && !direction.equals("U")){
            direction="D";
        }else if(keyCode == KeyEvent.VK_LEFT && !direction.equals("R")){
            direction="L";
        }else if(keyCode == KeyEvent.VK_RIGHT && !direction.equals("L")){
            direction="R";
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /*
     * 1.重新定个闹钟
     * 2.蛇移动
     * 3.重画
     * */
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(isStarted && !isFaild){
            //移动身体
            for(int i=len;i>0;i--){
                snakex[i] = snakex[i-1];
                snakey[i] = snakey[i-1];
            }
            //头移动
            if(direction.equals("R")){
                //横坐标 +25
                snakex[0] = snakex[0]+25;
                if(snakex[0] >850){
                    snakex[0] = 25;
                }

            }else if(direction.equals("L")){
                //横坐标 -25
                snakex[0] = snakex[0]-25;
                if(snakex[0] <25){
                    snakex[0] = 850;
                }

            }else if(direction.equals("U")){
                //纵坐标 -25
                snakey[0] = snakey[0] -25;
                if(snakey[0] <75){
                    snakey[0] = 650;
                }

            }else if(direction.equals("D")){
                //纵坐标 +25
                snakey[0] = snakey[0] +25;
                if(snakey[0]>650){
                    snakey[0] = 75;
                }
            }
            //吃食物的逻辑
            if(snakex[0] == foodx && snakey[0] == foody){
                len++;
                score++;
                foodx = r.nextInt(34)*25+25;
                foody = r.nextInt(24)*25+75;
            }
            //判断游戏失败
            for(int i=1;i<len;i++){
                if(snakex[0] == snakex[i] && snakey[0] == snakey[i]){
                    isFaild = true;
                }
            }
        }
        //重画一下
        repaint();
    }
}
