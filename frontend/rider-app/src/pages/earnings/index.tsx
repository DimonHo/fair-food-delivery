import { useState, useEffect } from 'react'
import { View, Text, ScrollView } from '@tarojs/components'
import './index.scss'

interface EarningsData {
  today: number
  week: number
  month: number
  orders: number
  avgDistance: number
  avgTime: number
  dailyData: Array<{ date: string; amount: number }>
}

export default function RiderEarningsPage() {
  const [data, setData] = useState<EarningsData | null>(null)

  useEffect(() => {
    // 模拟收入数据
    setData({
      today: 156.5,
      week: 1089.0,
      month: 4256.5,
      orders: 58,
      avgDistance: 3.2,
      avgTime: 28,
      dailyData: [
        { date: '01-25', amount: 145 },
        { date: '01-26', amount: 168 },
        { date: '01-27', amount: 132 },
        { date: '01-28', amount: 189 },
        { date: '01-29', amount: 156 },
        { date: '01-30', amount: 178 },
        { date: '01-31', amount: 121 }
      ]
    })
  }, [])

  if (!data) return <View className='loading'>加载中...</View>

  const maxAmount = Math.max(...data.dailyData.map(d => d.amount))

  return (
    <View className='rider-earnings-page'>
      {/* 总收入卡片 */}
      <View className='total-card'>
        <Text className='total-label'>本月收入</Text>
        <Text className='total-amount'>¥{data.month.toFixed(2)}</Text>
        <View className='total-stats'>
          <View className='stat-item'>
            <Text className='stat-value'>{data.orders}</Text>
            <Text className='stat-label'>订单数</Text>
          </View>
          <View className='stat-item'>
            <Text className='stat-value'>{data.avgDistance}km</Text>
            <Text className='stat-label'>均距</Text>
          </View>
          <View className='stat-item'>
            <Text className='stat-value'>{data.avgTime}min</Text>
            <Text className='stat-label'>均时</Text>
          </View>
        </View>
      </View>

      {/* 快捷统计 */}
      <View className='quick-stats'>
        <View className='quick-item'>
          <Text className='quick-value'>¥{data.today}</Text>
          <Text className='quick-label'>今日收入</Text>
        </View>
        <View className='quick-item'>
          <Text className='quick-value'>¥{data.week}</Text>
          <Text className='quick-label'>本周收入</Text>
        </View>
      </View>

      {/* 收入趋势图 */}
      <View className='chart-section'>
        <Text className='section-title'>近7日收入趋势</Text>
        <View className='chart-container'>
          {data.dailyData.map((item, index) => (
            <View key={index} className='chart-bar-wrapper'>
              <View 
                className='chart-bar'
                style={{ height: `${(item.amount / maxAmount) * 100}%` }}
              >
                <Text className='bar-value'>¥{item.amount}</Text>
              </View>
              <Text className='bar-date'>{item.date}</Text>
            </View>
          ))}
        </View>
      </View>

      {/* 提现按钮 */}
      <View className='withdraw-section'>
        <Text className='withdraw-text'>可提现余额 ¥{data.today.toFixed(2)}</Text>
        <View className='withdraw-btn'>立即提现</View>
      </View>
    </View>
  )
}
