import Taro from '@tarojs/taro'
import { useState, useEffect } from 'react'
import { View, Text, Button, ScrollView } from '@tarojs/components'
import './index.scss'
import { useAppStore } from '../../store'

interface TaskOrder {
  id: number
  orderNo: string
  merchantName: string
  merchantAddress: string
  deliveryAddress: string
  distance: number
  earning: number
  createdAt: string
}

export default function RiderHomePage() {
  const { token } = useAppStore()
  const [orders, setOrders] = useState<TaskOrder[]>([])
  const [activeTab, setActiveTab] = useState('grab')
  const [grabbingId, setGrabbingId] = useState<number | null>(null)

  useEffect(() => {
    if (token) {
      loadOrders()
    }
  }, [token])

  const loadOrders = () => {
    // 模拟订单数据
    setOrders([
      {
        id: 1,
        orderNo: 'FF20240131001',
        merchantName: '好滋味餐厅',
        merchantAddress: '朝阳区xxx路100号',
        deliveryAddress: '海淀区xxx小区1号楼101',
        distance: 2.5,
        earning: 8.5,
        createdAt: new Date().toISOString()
      },
      {
        id: 2,
        orderNo: 'FF20240131002',
        merchantName: '汉堡王',
        merchantAddress: '朝阳区xxx大厦1层',
        deliveryAddress: '海淀区xxx街道2号楼202',
        distance: 3.2,
        earning: 10.0,
        createdAt: new Date().toISOString()
      },
      {
        id: 3,
        orderNo: 'FF20240131003',
        merchantName: '奶茶店',
        merchantAddress: '朝阳区xxx广场B1层',
        deliveryAddress: '海淀区xxx大学宿舍楼',
        distance: 1.8,
        earning: 6.5,
        createdAt: new Date().toISOString()
      }
    ])
  }

  const grabOrder = (orderId: number) => {
    setGrabbingId(orderId)
    setTimeout(() => {
      setGrabbingId(null)
      Taro.showToast({ title: '抢单成功', icon: 'success' })
      setOrders(orders.filter(o => o.id !== orderId))
    }, 500)
  }

  if (!token) {
    return (
      <View className='rider-home-page'>
        <View className='login-tip'>请先登录</View>
      </View>
    )
  }

  return (
    <View className='rider-home-page'>
      {/* 顶部统计 */}
      <View className='stats-bar'>
        <View className='stat-item'>
          <Text className='stat-value'>¥{((orders.length || 0) * 8.5).toFixed(1)}</Text>
          <Text className='stat-label'>今日收入</Text>
        </View>
        <View className='stat-item'>
          <Text className='stat-value'>{orders.length}</Text>
          <Text className='stat-label'>可抢订单</Text>
        </View>
        <View className='stat-item'>
          <Text className='stat-value'>5</Text>
          <Text className='stat-label'>今日完成</Text>
        </View>
      </View>

      {/* Tab切换 */}
      <View className='tab-bar'>
        <View 
          className={`tab-item ${activeTab === 'grab' ? 'active' : ''}`}
          onClick={() => setActiveTab('grab')}
        >
          抢单
        </View>
        <View 
          className={`tab-item ${activeTab === 'tasks' ? 'active' : ''}`}
          onClick={() => setActiveTab('tasks')}
        >
          我的任务
        </View>
      </View>

      {/* 订单列表 */}
      <ScrollView className='order-list' scrollY>
        {activeTab === 'grab' ? (
          orders.map(order => (
            <View key={order.id} className='order-card'>
              <View className='order-header'>
                <Text className='order-no'>{order.orderNo}</Text>
                <Text className='earning'>¥{order.earning}</Text>
              </View>
              <View className='order-route'>
                <View className='route-item pickup'>
                  <Text className='dot'>●</Text>
                  <View className='route-info'>
                    <Text className='route-label'>取餐</Text>
                    <Text className='route-text'>{order.merchantName}</Text>
                    <Text className='route-addr'>{order.merchantAddress}</Text>
                  </View>
                </View>
                <View className='route-line'></View>
                <View className='route-item delivery'>
                  <Text className='dot'>●</Text>
                  <View className='route-info'>
                    <Text className='route-label'>送餐</Text>
                    <Text className='route-text'>{order.deliveryAddress}</Text>
                  </View>
                </View>
              </View>
              <View className='order-footer'>
                <Text className='distance'>距离 {order.distance}km</Text>
                <Button 
                  className='grab-btn'
                  loading={grabbingId === order.id}
                  onClick={() => grabOrder(order.id)}
                >
                  {grabbingId === order.id ? '抢单中...' : '立即抢单'}
                </Button>
              </View>
            </View>
          ))
        ) : (
          <View className='empty-tasks'>
            <Text>暂无进行中的订单</Text>
          </View>
        )}
      </ScrollView>
    </View>
  )
}
