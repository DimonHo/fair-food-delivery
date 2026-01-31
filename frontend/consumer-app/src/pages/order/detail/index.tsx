import Taro from '@tarojs/taro'
import { useState, useEffect } from 'react'
import { View, Text, Image, Button } from '@tarojs/components'
import './index.scss'
import { Order } from '../../../../shared/types'

const statusMap = {
  pending: { text: '待付款', color: '#ff9800' },
  paid: { text: '已付款', color: '#2196f3' },
  confirmed: { text: '已接单', color: '#9c27b0' },
  cooking: { text: '制作中', color: '#ff9800' },
  ready: { text: '待取餐', color: '#00bcd4' },
  delivering: { text: '配送中', color: '#2196f3' },
  completed: { text: '已完成', color: '#4caf50' },
  cancelled: { text: '已取消', color: '#9e9e9e' },
  refunded: { text: '已退款', color: '#f44336' }
}

export default function OrderDetail() {
  const [order, setOrder] = useState<Order | null>(null)
  const id = Taro.getCurrentInstance().router?.params.id

  useEffect(() => {
    // 模拟获取订单详情
    setOrder({
      id: Number(id) || 1,
      orderNo: 'FF' + Date.now(),
      userId: 1,
      merchantId: 1,
      merchantName: '好滋味餐厅',
      status: 'delivering',
      items: [
        { productId: 1, name: '招牌红烧肉', price: 28, quantity: 1 },
        { productId: 2, name: '宫保鸡丁', price: 22, quantity: 1 },
        { productId: 5, name: '可乐', price: 5, quantity: 1 }
      ],
      totalPrice: 55,
      deliveryFee: 3,
      actualPay: 58,
      address: {
        id: 1,
        name: '张三',
        phone: '138****8888',
        province: '北京市',
        city: '朝阳区',
        district: 'xxx街道',
        detail: 'xxx小区1号楼101',
        isDefault: true
      },
      remark: '不要辣',
      createdAt: new Date().toISOString()
    })
  }, [id])

  const handlePay = () => {
    Taro.showLoading({ title: '支付中...' })
    setTimeout(() => {
      Taro.hideLoading()
      Taro.showToast({ title: '支付成功', icon: 'success' })
    }, 1500)
  }

  const handleCancel = () => {
    Taro.showModal({
      title: '取消订单',
      content: '确定要取消此订单吗？',
      success: (res) => {
        if (res.confirm) {
          Taro.showToast({ title: '订单已取消', icon: 'none' })
          setTimeout(() => Taro.navigateBack(), 1000)
        }
      }
    })
  }

  const handleRefund = () => {
    Taro.showModal({
      title: '申请退款',
      content: '确定要申请退款吗？',
      success: (res) => {
        if (res.confirm) {
          Taro.showToast({ title: '退款申请已提交', icon: 'success' })
        }
      }
    })
  }

  if (!order) return <View className='loading'>加载中...</View>

  const statusInfo = statusMap[order.status]

  return (
    <View className='order-detail-page'>
      {/* 状态头部 */}
      <View className='status-header'>
        <Text className='status-text' style={{ color: statusInfo.color }}>
          {statusInfo.text}
        </Text>
        {order.status === 'delivering' && (
          <Text className='status-desc'>骑手正在配送中</Text>
        )}
      </View>

      {/* 地址信息 */}
      <View className='section address-section'>
        <View className='address-icon'>📍</View>
        <View className='address-info'>
          <View className='user-info'>
            <Text className='name'>{order.address.name}</Text>
            <Text className='phone'>{order.address.phone}</Text>
          </View>
          <Text className='address-text'>
            {order.address.province}{order.address.city}{order.address.district}{order.address.detail}
          </Text>
        </View>
      </View>

      {/* 商家信息 */}
      <View className='section merchant-section'>
        <Text className='merchant-name'>{order.merchantName}</Text>
        {order.items.map((item, index) => (
          <View key={index} className='item-row'>
            <Text className='item-name'>{item.name}</Text>
            <Text className='item-qty'>x{item.quantity}</Text>
            <Text className='item-price'>¥{item.price}</Text>
          </View>
        ))}
        {order.remark && (
          <View className='remark-row'>
            <Text className='remark-label'>备注：</Text>
            <Text className='remark-text'>{order.remark}</Text>
          </View>
        )}
      </View>

      {/* 订单信息 */}
      <View className='section'>
        <View className='info-row'>
          <Text className='label'>订单号</Text>
          <Text className='value'>{order.orderNo}</Text>
        </View>
        <View className='info-row'>
          <Text className='label'>下单时间</Text>
          <Text className='value'>{new Date(order.createdAt).toLocaleString()}</Text>
        </View>
        <View className='info-row'>
          <Text className='label'>支付方式</Text>
          <Text className='value'>微信支付</Text>
        </View>
      </View>

      {/* 费用明细 */}
      <View className='section fee-section'>
        <View className='fee-row'>
          <Text className='label'>商品金额</Text>
          <Text className='value'>¥{order.totalPrice}</Text>
        </View>
        <View className='fee-row'>
          <Text className='label'>配送费</Text>
          <Text className='value'>¥{order.deliveryFee}</Text>
        </View>
        <View className='fee-row total'>
          <Text className='label'>实付金额</Text>
          <Text className='value price'>¥{order.actualPay}</Text>
        </View>
      </View>

      {/* 底部操作栏 */}
      <View className='action-bar'>
        {order.status === 'pending' && (
          <>
            <Button className='btn-cancel' onClick={handleCancel}>取消订单</Button>
            <Button className='btn-pay' onClick={handlePay}>去支付</Button>
          </>
        )}
        {order.status === 'paid' && (
          <Button className='btn-cancel' onClick={handleRefund}>申请退款</Button>
        )}
        {order.status === 'completed' && (
          <Button className='btn-primary'>再来一单</Button>
        )}
      </View>
    </View>
  )
}
