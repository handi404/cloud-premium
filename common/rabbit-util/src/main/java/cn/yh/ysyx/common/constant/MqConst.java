package cn.yh.ysyx.common.constant;

public class MqConst {
    /**
     * 消息补偿
     */
    public static final String MQ_KEY_PREFIX = "ysyx.mq:list";
    public static final int RETRY_COUNT = 3;

    /**
     * 商品上下架
     */
    public static final String EXCHANGE_GOODS_DIRECT = "ysyx.goods.direct";
    public static final String ROUTING_GOODS_UPPER = "ysyx.goods.upper";
    public static final String ROUTING_GOODS_LOWER = "ysyx.goods.lower";
    //队列
    public static final String QUEUE_GOODS_UPPER  = "ysyx.goods.upper";
    public static final String QUEUE_GOODS_LOWER  = "ysyx.goods.lower";

    /**
     * 团长上下线
     */
    public static final String EXCHANGE_LEADER_DIRECT = "ysyx.leader.direct";
    public static final String ROUTING_LEADER_UPPER = "ysyx.leader.upper";
    public static final String ROUTING_LEADER_LOWER = "ysyx.leader.lower";
    //队列
    public static final String QUEUE_LEADER_UPPER  = "ysyx.leader.upper";
    public static final String QUEUE_LEADER_LOWER  = "ysyx.leader.lower";

    //订单
    public static final String EXCHANGE_ORDER_DIRECT = "ysyx.order.direct";
    public static final String ROUTING_ROLLBACK_STOCK = "ysyx.rollback.stock";
    public static final String ROUTING_MINUS_STOCK = "ysyx.minus.stock";

    public static final String ROUTING_DELETE_CART = "ysyx.delete.cart";
    //解锁普通商品库存
    public static final String QUEUE_ROLLBACK_STOCK = "ysyx.rollback.stock";
    public static final String QUEUE_SECKILL_ROLLBACK_STOCK = "ysyx.seckill.rollback.stock";
    public static final String QUEUE_MINUS_STOCK = "ysyx.minus.stock";
    public static final String QUEUE_DELETE_CART = "ysyx.delete.cart";

    //支付
    public static final String EXCHANGE_PAY_DIRECT = "ysyx.pay.direct";
    public static final String ROUTING_PAY_SUCCESS = "ysyx.pay.success";
    public static final String QUEUE_ORDER_PAY  = "ysyx.order.pay";
    public static final String QUEUE_LEADER_BILL  = "ysyx.leader.bill";

    //取消订单
    public static final String EXCHANGE_CANCEL_ORDER_DIRECT = "ysyx.cancel.order.direct";
    public static final String ROUTING_CANCEL_ORDER = "ysyx.cancel.order";
    //延迟取消订单队列
    public static final String QUEUE_CANCEL_ORDER  = "ysyx.cancel.order";

    /**
     * 定时任务
     */
    public static final String EXCHANGE_DIRECT_TASK = "ysyx.exchange.direct.task";
    public static final String ROUTING_TASK_23 = "ysyx.task.23";
    //队列
    public static final String QUEUE_TASK_23  = "ysyx.queue.task.23";
}
