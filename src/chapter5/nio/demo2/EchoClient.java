package chapter5.nio.demo2;

import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * @function 封装一个队列。保存在需要返回给这个客户端的所有消息
 */
public class EchoClient {
    private LinkedList<ByteBuffer> outq;

    EchoClient () {
        outq = new LinkedList<>();
    }

    public LinkedList<ByteBuffer> getOutPutQueue() {
        return outq;
    }

    public void enqueue(ByteBuffer bb) {
        outq.addFirst(bb);
    }
}
