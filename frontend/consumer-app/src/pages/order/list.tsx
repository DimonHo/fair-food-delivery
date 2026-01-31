import React, { useEffect, useState } from 'react'
import Taro from '@tarojs/taro'
import { View, Text, Image, Button, ScrollView } from '@tarojs/components'
import { relativeTime, formatPrice } from '../../../../shared/utils'
import './index.scss'

interface OrderItem {
  productId: number
  name: string
  price: number
  quantity: number
}

interface Order {
  id: number
  orderNo: string
  merchantName: string
  status: string
  statusText: string
  items: OrderItem[]
  totalPrice: number
  createdAt: string
}

const STATUS_MAP: Record<string, { text: string; color: string }> = {
  pending: { text: '待付款', color: '#ff9500' },
  paid: { text: '待接单', color: '#1890ff' },
  confirmed: { text: '已接单', color: '#1890ff' },
  cooking: { text: '制作中', color: '#722ed1' },
  ready: { text: '待取餐', color: '#13c2c2' },
  delivering: { text: '配送中', color: '#1890ff' },
  completed: { text: '已完成', color: '#52c41a' },
  cancelled: { text: '已取消', color: '#999' },
  refunded: { text: '已退款', color: '#999' }
}

export default function OrderList () {
  const [orders, setOrders] = useState<Order[]>([])
  const [loading, setLoading] = useState(true)
  const [activeTab, setActiveTab] = useState('all')

  const tabs = [
    { key: 'all', label: '全部' },
    { key: 'pending', label: '待付款' },
    { key: 'paid', label: '待接单' },
    { key: 'delivering', label: '配送中' },
    { key: 'completed', label: '已完成' }
  ]

  useEffect(() => {
    fetchOrders()
  }, [activeTab])

  const fetchOrders = async () => {
    setLoading(true)
    try {
      // TODO: 调用真实 API
      await new Promise(resolve => setTimeout(resolve, 500))
      
      // 模拟数据
      setOrders([
        {
          id: 1,
          orderNo: '20250131123456',
          merchantName: '招牌牛肉面',
          status: 'delivering',
          statusText: '配送中',
          items: [
            { productId: 1, name: '招牌牛肉面', price: 18, quantity: 2 },
            { productId: 2, name: '卤蛋', price: 3, quantity: 1 }
          ],
          totalPrice: 39,
          createdAt: new Date(Date.now() - 30 * 60 * 1000).toISOString()
        },
        {
          id: 2,
          orderNo: '20250130123456',
          merchantName: '川香小炒',
          status: 'completed',
          statusText: '已完成',
          items: [
            { productId: 3, name: '宫保鸡丁', price: 28, quantity: 1 }
          ],
          totalPrice: 28,
          createdAt: new Date(Date.now() - 24 * 60 * 60 * 1000).toISOString()
        }
      ])
    } finally {
      setLoading(false)
    }
  }

  const goToDetail = (orderId: number) => {
    Taro.navigateTo({
      url: `/pages/order/detail?id=${orderId}`
    })
  }

  const handlePay = (orderId: number) => {
    Taro.showToast({ title: '支付功能开发中', icon: 'none' })
  }

  const filteredOrders = activeTab === 'all' 
    ? orders 
    : orders.filter(o => o.status === activeTab || 
        (activeTab === 'delivering' && ['delivering', 'ready'].includes(o.status)))

  return (
    <View className='order-list-page'>
      {/* Tab 导航 */}
      <ScrollView className='tabs' scrollX>
        {tabs.map(tab => (
          <View 
            key={tab.key} 
            className={`tab ${activeTab === tab.key ? 'active' : ''}`}
            onClick={() => setActiveTab(tab.key)}
          >
            {tab.label}
          </View>
        ))}
      </ScrollView>

      {/* 订单列表 */}
      <ScrollView className='order-scroll' scrollY>
        {loading ? (
          <View className='loading'>加载中...</View>
        ) : filteredOrders.length === 0 ? (
          <View className='empty'>
            <Text className='icon'>📋</Text>
            <Text className='text'>暂无订单</Text>
          </View>
        ) : (
          filteredOrders.map(order => {
            const statusInfo = STATUS_MAP[order.status] || { text: order.statusText, color: '#666' }
            return (
              <View key={order.id} className='order-card' onClick={() => goToDetail(order.id)}>
                <View className='order-header'>
                  <View className='merchant'>
                    <Text className='name'>{order.merchantName}</Text>
                    <Text className='order-no'>{order.orderNo}</Text>
                  </View>
                  <Text className='status' style={{ color: statusInfo.color }}>
                    {statusInfo.text}
                  </Text>
                </View>

                <View className='order-items'>
                  {order.items.slice(0, 3).map((item, idx) => (
                    <Text key={idx} className='item'>
                      {item.name} ×{item.quantity}
                    </Text>
                  ))}
                  {order.items.length > 3 && (
                    <Text className='more'>等{order.items.length}件商品</Text>
                  )}
                </View>

                <View className='order-footer'>
                  <Text className='time'>{relativeTime(order.createdAt)}</Text>
                  <View className='right'>
                    <Text className='total-label'>合计</Text>
                    <Text className='total-price'>{formatPrice(order.totalPrice)}</Text>
                    {order.status === 'pending' && (
                      <Button 
                        className='pay-btn' 
                        size='mini'
                        onClick={(e) => { e.stopPropagation(); handlePay(order.id) }}
                      >
                        去支付
                      </Button>
                    )}
                  </View>
                </View>
              </View>
            )
          })
        )}
      </ScrollView>
    </View>
  )
}
