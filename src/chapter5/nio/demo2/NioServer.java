package chapter5.nio.demo2;

import sun.jvm.hotspot.debugger.Address;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class NioServer {
    private Selector selector;

    private ExecutorService service = Executors.newCachedThreadPool();

    public  static Map<Socket,Long> time_stat = new HashMap<>();


    private  void startServer() throws Exception{
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false); //设置为非阻塞

        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
        //InetSocketAddress isa = new InetSocketAddress(8000);
        ssc.socket().bind(isa);

        for (;;) { // 等待并分发网络信息
            selector.select(); //阻塞  -- 当前没有任何数据准备好久一直等待
            // 获取准备好的SelectionKey
            Set<SelectionKey> readyKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = readyKeys.iterator();
            long e =0;
            while (iterator.hasNext()){
                SelectionKey sk = iterator.next();
                iterator.remove();//非常重要，避免重复处理相同的selectionkey
                //1. 判断当前SelectionKey所代表的Channel是否在Acceptable状态，若是就接收
                if (sk.isAcceptable()) {
                    doAccept(sk); //todo


                }
                //2. 判断通道是否可读
                else if (sk.isValid() && sk.isReadable()) {
                    if (!time_stat.containsKey(((SocketChannel)sk.channel()).socket())){
                        time_stat.put(((SocketChannel)sk.channel()).socket(),System.currentTimeMillis());
                        doRead(sk); //todo
                    }
                }
                //判断通道是否准备好进行写
                else if (sk.isValid() && sk.isWritable()) {
                    doWrite(sk);//todo
                    e = System.currentTimeMillis();
                    long b = time_stat.remove(((SocketChannel)sk.channel()).socket());
                    System.out.println("spend: " + (e - b) + " ms");




                }
            }

        }


    }

    private void doRead(SelectionKey sk) {
        SocketChannel channel = (SocketChannel)sk.channel();

        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;
        try {
            len = channel.read(bb);
            if (len < 0 ){
                disconnect(sk);
                return;
            }


        }catch (Exception e){
            System.out.println("Failed to read from client!");
            e.printStackTrace();
            disconnect(sk);
            return;
        }
        bb.flip();
        service.execute(new HandleMsg(sk,bb));

    }

    private void disconnect(SelectionKey sk) {
        sk.cancel();
    }

    private void doWrite(SelectionKey sk) {
        SocketChannel channel = (SocketChannel)sk.channel();
        EchoClient echoClient =  (EchoClient)sk.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutPutQueue();

        ByteBuffer bb = outq.getLast();

        try {
            int len = channel.write(bb);
            if (len == -1) {
                disconnect(sk);
                return;
            }
            if (bb.remaining() == 0){
                outq.removeLast();
            }

        } catch (Exception e) {
            System.out.println("Failed to write to client !");
            e.printStackTrace();
            disconnect(sk);
        }

        if (outq.size() == 0) {
            sk.interestOps(SelectionKey.OP_READ);
        }

    }


    private void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;

        try {
            clientChannel = server.accept(); // 和客户端通信的通道
            clientChannel.configureBlocking(false);


            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);

            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);


            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accept connection from :" + clientAddress.getHostAddress());

        } catch (Exception e){
            System.out.println("Failed to accept new client !");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

    }


    class HandleMsg implements Runnable {
        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk, ByteBuffer bb) {
            this.sk = sk;
            this.bb = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient =(EchoClient) sk.attachment();
            echoClient.enqueue(bb);
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            selector.wakeup();

        }
    }
}
