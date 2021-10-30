package BIO;



import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        //创建一个线程池
        //如果客户端连接 ，就创建一个一线程与之连接
        ExecutorService executorService= Executors.newCachedThreadPool();


        //创建serverSocket
        final ServerSocket serverSocket=new ServerSocket(6666);

        System.out.println("服务器启动了");

        while(true){
            //监听客户端连接
            final Socket s =serverSocket.accept();
            System.out.println("连接到一个客户端");

            //就创建一个线程，与之通讯
            executorService.execute(new Runnable() {
                public void run() {
                    //我们重写 可以和客户端通讯
                    handler(s);
                }
            });
        }



    }
    //编写一个handler方法
    public static void handler(Socket socket){

      try {
          System.out.println("线程id ="+Thread.currentThread().getId()+"名字=" +Thread.currentThread().getName());
          byte[] bytes=new byte[1024];

          //socket,获取输入流
          InputStream inputStream=socket.getInputStream();

          while(true){
              System.out.println("线程id ="+Thread.currentThread().getId()+"名字=" +Thread.currentThread().getName());

              int read=inputStream.read(bytes);
              if(read!=-1){
                  System.out.println(new String(bytes,0,read));
              }else {
                  break;
              }
          }
      }catch (Exception e){
          e.printStackTrace();
      }finally {
          try {
              socket.close();
          }catch (Exception e){
              e.printStackTrace();
          }

      }

    }
}
