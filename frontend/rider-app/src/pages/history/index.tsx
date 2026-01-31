import { useState, useEffect } from 'react'
import { View, Text, ScrollView } from '@tarojs/components'
import './index.scss'

interface HistoryOrder {
  id: number
  orderNo: string
  merchantName: string
  deliveryAddress: string
  earning: number
  status: 'completed'
  completedAt: string
  distance: number
  duration: number
}

export default function RiderHistoryPage() {
  const [orders, setOrders] = useState<HistoryOrder[]>([])
  const [filter, setFilter] = useState<'all' | 'today' | 'week'>('all')

  useEffect(() => {
    // 模拟历史订单
    setOrders([
      {
        id: 101,
        orderNo: 'FF20240130015',
        merchantName: '好滋味餐厅',
        deliveryAddress: '海淀区xxx小区1号楼101',
        earning: 8.5,
        status: 'completed',
        completedAt: '2024-01-30 14:30',
        distance: 2.5,
        duration: 25
      },
      {
        id: 102,
        orderNo: 'FF20240130012',
        merchantName: '汉堡王',
        deliveryAddress: '海淀区xxx大厦502',
        earning: 10.0,
        status: 'completed',
        completedAt: '2024-01-30 12:15',
        distance: 3.2,
        duration: 32
      },
      {
        id: 103,
        orderNo: 'FF20240129008',
        merchantName: '奶茶店',
        deliveryAddress: '海淀区xxx大学宿舍楼',
        earning: 6.5,
        status: 'completed',
        completedAt: '2024-01-29 18:45',
        distance: 1.8,
        duration: 18
      },
      {
        id: 104,
        orderNo: 'FF20240129005',
        merchantName: '川菜馆',
        deliveryAddress: '朝阳区xxx路88号',
        earning: 9.0,
        status: 'completed',
        completedAt: '2024-01-29 12:30',
        distance: 4.1,
        duration: 38
      },
      {
        id: 105,
        orderNo: 'FF20240128022',
        merchantName: '披萨店',
        deliveryAddress: '海淀区xxx公寓A座',
        earning: 11.5,
        status: 'completed',
        completedAt: '2024-01-28 20:00',
        distance: 5.2,
        duration: 42
      }
    ])
  }, [])

  const totalEarnings = orders.reduce((sum, o) => sum + o.earning, 0)

  return (
    <View className='rider-history-page'>
      {/* 筛选栏 */}
      <View className='filter-bar'>
        <View 
          className={`filter-item ${filter === 'all' ? 'active' : ''}`}
          onClick={() => setFilter('all')}
        >
          全部
        </View>
        <View 
          className={`filter-item ${filter === 'today' ? 'active' : ''}`}
          onClick={() => setFilter('today')}
        >
          今日
        </View>
        <View 
          className={`filter-item ${filter === 'week' ? 'active' : ''}`}
          onClick={() => setFilter('week')}
        >
          本周
        </View>
      </View>

      {/* 统计信息 */}
      <View className='stats-card'>
        <View className='stat-item'>
          <Text className='stat-value'>{orders.length}</Text>
          <Text className='stat-label'>订单数</Text>
        </View>
        <View className='stat-item'>
          <Text className='stat-value'>¥{totalEarnings.toFixed(1)}</Text>
          <Text className='stat-label'>总收入</Text>
        </View>
        <View className='stat-item'>
          <Text className='stat-value'>{(orders.reduce((sum, o) => sum + o.distance, 0) / orders.length || 0).toFixed(1)}km</Text>
          <Text className='stat-label'>均距</Text>
        </View>
      </View>

      {/* 订单列表 */}
      <ScrollView className='order-list' scrollY>
        {orders.map(order => (
          <View key={order.id} className='order-card'>
            <View className='order-header'>
              <Text className='order-no'>{order.orderNo}</Text>
              <Text className='status-tag'>已完成</Text>
            </View>
            
            <View className='order-content'>
              <View className='merchant-row'>
                <Text className='label'>取餐</Text>
                <Text className='value'>{order.merchantName}</Text>
              </View>
              <View className='delivery-row'>
                <Text className='label'>送餐</Text>
                <Text className='value'>{order.deliveryAddress}</Text>
              </View>
            </View>

            <View className='order-footer'>
              <View className='order-meta'>
                <Text className='meta-text'>{order.completedAt}</Text>
                <Text className='meta-text'>{order.distance}km</Text>
                <Text className='meta-text'>{order.duration}分钟</Text>
              </View>
              <Text className='earning'>+¥{order.earning}</Text>
            </View>
          </View>
        ))}
      </ScrollView>
    </View>
  )
}
